package com.obliquity.genealogy.test.peopleviewer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.obliquity.genealogy.test.PersonPanel;

import java.awt.BorderLayout;
import java.util.Vector;
import java.util.Collections;

class FamilyLister extends JPanel {
	protected Vector<FamilyListItem> allFamilies;
	JList memberNameList = new JList();
	JList familyNameList;
	PersonPanel personPanel = new PersonPanel();
	
	public FamilyLister(Vector<FamilyListItem> allFamilies) {
		super(null);
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
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp2);
		
		JPanel topPanel = new JPanel(new BorderLayout());
		
		topPanel.add(BorderLayout.CENTER, splitPane);
		
		familyNameList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				FamilyListItem fli = (FamilyListItem)familyNameList.getSelectedValue();
				Vector v = fli.getFamilyMembers();
				memberNameList.setListData(v);				
				memberNameList.setSelectedIndex(0);
			}		
		});
		
		familyNameList.setSelectedIndex(0);
		
		memberNameList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				PersonListItem pli = (PersonListItem)memberNameList.getSelectedValue();
				if (pli != null) {
					personPanel.setPerson(pli.getPerson());
				} else
					personPanel.setPerson(null);
			}		
		});
		
		familyNameList.setVisibleRowCount(20);
		memberNameList.setVisibleRowCount(20);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		
		bottomPanel.add(BorderLayout.CENTER, personPanel);
		
		add(topPanel);
		add(bottomPanel);
	}
}
