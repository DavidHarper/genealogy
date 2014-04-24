/*
 * genealogy - a package for reading genealogy data in GEDCOM format
 *
 * Copyright (C) 2008-2014 David Harper at obliquity.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 *
 * See the COPYING file located in the top-level-directory of
 * the archive of this library for complete text of license.
 */

package com.obliquity.genealogy.test.peopleviewer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.obliquity.genealogy.Family;
import com.obliquity.genealogy.Name;
import com.obliquity.genealogy.Person;
import com.obliquity.genealogy.test.FamilyPage;
import com.obliquity.genealogy.test.PersonPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.List;
import java.util.Collections;

class FamilyLister extends JPanel {
	protected Vector<FamilyListItem> allFamilies;
	protected final JTabbedPane parent;
	protected FamilyPage familyPage;

	protected JList memberNameList = new JList();

	protected JList familyNameList;
	
	protected JButton btnFamilyAsChild = new JButton("Show family as child");
	protected JButton btnFamilyAsSpouse = new JButton("Show family as spouse");
	
	protected PersonPanel personPanel = new PersonPanel();

	public FamilyLister(Vector<FamilyListItem> allFamilies, JTabbedPane parent, FamilyPage familyPage) {
		super(null);
		
		this.parent = parent;
		this.familyPage = familyPage;
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		this.allFamilies = allFamilies;
		Collections.sort(this.allFamilies);
		createUI();
	}

	protected void createUI() {
		familyNameList = new JList(allFamilies);
		JScrollPane sp1 = new JScrollPane(familyNameList);

		JScrollPane sp2 = new JScrollPane(memberNameList);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1,
				sp2);

		JPanel topPanel = new JPanel(new BorderLayout());

		topPanel.add(BorderLayout.CENTER, splitPane);

		familyNameList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				FamilyListItem fli = (FamilyListItem) familyNameList
						.getSelectedValue();
				Vector v = fli.getFamilyMembers();
				memberNameList.setListData(v);
				memberNameList.setSelectedIndex(0);
			}
		});

		familyNameList.setSelectedIndex(0);

		memberNameList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				PersonListItem pli = (PersonListItem) memberNameList
						.getSelectedValue();
				if (pli != null) {
					Person person = pli.getPerson();
					personPanel.setPerson(person);
					btnFamilyAsChild.setEnabled(person.hasFamilyAsChild());
					btnFamilyAsSpouse.setEnabled(person.hasFamilyAsSpouse());
				} else {
					personPanel.setPerson(null);
					btnFamilyAsChild.setEnabled(false);
					btnFamilyAsSpouse.setEnabled(false);
				}
			}
		});

		familyNameList.setVisibleRowCount(20);
		memberNameList.setVisibleRowCount(20);

		JPanel bottomPanel = new JPanel(new BorderLayout());

		bottomPanel.add(BorderLayout.CENTER, personPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton btnInfo = new JButton("Show individual page");
		buttonPanel.add(btnInfo);
		
		buttonPanel.add(btnFamilyAsChild);
		buttonPanel.add(btnFamilyAsSpouse);
		
		btnFamilyAsChild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFamilyAsChild();
			}			
		});
		
		btnFamilyAsSpouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFamilyAsSpouse();
			}			
		});
		
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		add(topPanel);
		add(bottomPanel);
	}
	
	protected void showFamilyAsChild() {
		Person person = personPanel.getPerson();

		List<Family> families = person.getFamiliesAsChild();
		
		Family family = null;
		
		if (families != null)
			family = families.get(0);
		
		familyPage.setFamily(family);
	}
	
	protected void showFamilyAsSpouse() {
		Person person = personPanel.getPerson();

		List<Family> families = person.getFamiliesAsSpouse();
		
		Family family = null;
		
		if (families.size() > 1) {
			FamilyAsSpouseProxy[] proxies = new FamilyAsSpouseProxy[families.size()];
			
			for (int i = 0; i < families.size(); i++)
				proxies[i] = new FamilyAsSpouseProxy(families.get(i), person);
		
			FamilyAsSpouseProxy proxy = (FamilyAsSpouseProxy)JOptionPane.showInputDialog(
                    this,
                    "Please choose the spouse",
                    "Multiple Marriages!",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    proxies,
                    proxies[0]);
			
			if (proxy != null)
				family = proxy.getFamily();
		} else
			family = families.get(0);
	
		if (family != null) {
			familyPage.setFamily(family);
			parent.setSelectedIndex(1);
		}
	}
	
	class FamilyAsSpouseProxy {
		protected Family family;
		protected Person subject;
		protected Person spouse;
		protected String text;
		
		public FamilyAsSpouseProxy(Family family, Person subject) {
			this.family = family;
			this.subject = subject;
			
			spouse = subject.equals(family.getHusband()) ? family.getWife() : family.getHusband();
			
			Name name = spouse.getName();
			
			text = name != null ? name.getFullName() : "(UNKNOWN)";
		}
		
		public String toString() {
			return text;
		}
		
		public Family getFamily() {
			return family;
		}
	}
}
