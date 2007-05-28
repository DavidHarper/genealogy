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
