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

package com.obliquity.genealogy.test;

import javax.swing.*;

import com.obliquity.genealogy.Family;
import com.obliquity.genealogy.Person;

import java.awt.*;
import java.util.Vector;

public class FamilyPage extends JPanel {
	protected PersonPanel ppFather = new PersonPanel();

	protected PersonPanel ppMother = new PersonPanel();

	protected JLabel lblCaption = new JLabel();

	protected JPanel pnlChildren = new JPanel(new StackLayout(5));
	
	protected Vector<PersonPanel> ppChildren = new Vector<PersonPanel>();

	public FamilyPage() {
		super(new StackLayout(5));

		lblCaption.setForeground(Color.red);

		add(lblCaption);

		add(new JLabel("FATHER"));
		add(ppFather);

		add(new JLabel("MOTHER"));
		add(ppMother);

		add(new JLabel("CHILDREN"));

		add(pnlChildren);
	}

	public void setFamily(Family family) {
		Person husband = family.getHusband();
		Person wife = family.getWife();

		lblCaption.setText((husband == null || husband.getName() == null ? "?"
				: husband.getName().getFamilyName())
				+ "/"
				+ (wife == null || wife.getName() == null ? "?" : wife
						.getName().getFamilyName()));

		ppFather.setPerson(husband);
		ppMother.setPerson(wife);
		
		int nChildren = family.getChildCount();
		
		if (ppChildren.size() < nChildren)
			for (int i = ppChildren.size(); i <= nChildren; i++)
				ppChildren.add(new PersonPanel());
		
		pnlChildren.removeAll();
		
		for (int i = 0; i < nChildren; i++) {
			Person child = family.getChild(i+1);
			PersonPanel pp = ppChildren.elementAt(i);
			pp.setPerson(child);
			pnlChildren.add(pp);
		}
			
		validate();
	}
}
