package com.obliquity.genealogy.test;

import javax.swing.*;

import com.obliquity.genealogy.Family;
import com.obliquity.genealogy.Person;

import java.awt.*;

public class FamilyPage extends JPanel {
	protected PersonPanel ppFather = new PersonPanel();

	protected PersonPanel ppMother = new PersonPanel();

	protected JLabel lblCaption = new JLabel();

	protected JPanel pnlChildren = new JPanel(new StackLayout(5));

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
	}
}
