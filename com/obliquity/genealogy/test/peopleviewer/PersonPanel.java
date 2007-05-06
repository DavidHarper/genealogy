package com.obliquity.genealogy.test.peopleviewer;

import javax.swing.*;
import java.awt.*;

import com.obliquity.genealogy.*;

class PersonPanel extends JPanel implements LayoutManager {
	protected Person person;

	protected JLabel[] labels;

	protected JLabel[] values;

	protected int vgap = 5;

	protected int hgap = 5;

	protected static final int MINIMUM_SIZE = 0;

	protected static final int PREFERRED_SIZE = 1;

	protected Insets insets = new Insets(10, 10, 10, 10);

	protected JLabel lblName = new JLabel();

	protected JLabel lblBirth = new JLabel();

	protected JLabel lblBaptism = new JLabel();

	protected JLabel lblDeath = new JLabel();

	protected JLabel lblBurial = new JLabel();

	public PersonPanel() {
		super(null);
		setLayout(this);

		int nrows = 5;

		labels = new JLabel[nrows];
		values = new JLabel[nrows];

		labels[0] = new JLabel("NAME:");
		values[0] = lblName;

		labels[1] = new JLabel("Birth:");
		values[1] = lblBirth;

		labels[2] = new JLabel("Chr.:");
		values[2] = lblBaptism;

		labels[3] = new JLabel("Death:");
		values[3] = lblDeath;

		labels[4] = new JLabel("Burial:");
		values[4] = lblBurial;

		Font fntBold20 = new Font("sansserif", Font.BOLD, 20);
		Font fntPlain20 = new Font("sensserif", Font.PLAIN, 20);

		Font fntBold14 = new Font("sansserif", Font.BOLD, 14);
		Font fntPlain14 = new Font("sansserif", Font.PLAIN, 14);

		labels[0].setFont(fntBold20);

		Dimension size = labels[0].getPreferredSize();
		size.width = 800;

		values[0].setFont(fntPlain20);
		values[0].setPreferredSize(size);

		for (int i = 1; i < labels.length; i++) {
			labels[i].setFont(fntBold14);
			size = labels[i].getPreferredSize();
			size.width = 800;
			values[i].setFont(fntPlain14);
			values[i].setPreferredSize(size);
		}
		
		for (int i = 0; i < labels.length; i++) {
			add(labels[i]);
			add(values[i]);
		}
	}

	public void setPerson(Person person) {
		this.person = person;
		refresh();
	}

	protected void refresh() {
		lblName.setText(person == null ? "" : person.getName().toString());

		lblBirth.setText(person == null || person.getBirth() == null ? ""
				: person.getBirth().getDateAndPlace());

		lblBaptism.setText(person == null || person.getBaptism() == null ? ""
				: person.getBaptism().getDateAndPlace());

		lblDeath.setText(person == null || person.getDeath() == null ? ""
				: person.getDeath().getDateAndPlace());

		lblBurial.setText(person == null || person.getBurial() == null ? ""
				: person.getBurial().getDateAndPlace());
	}

	public void addLayoutComponent(String name, Component comp) {
		// Does nothing
	}

	public void layoutContainer(Container parent) {
		checkComponent(parent);

		synchronized (parent.getTreeLock()) {
			int wl = 0;

			for (int i = 0; i < labels.length; i++) {
				Dimension d = labels[i].getMinimumSize();
				if (wl < d.width)
					wl = d.width;
			}

			int xl = insets.left;
			int xt = xl + wl + hgap;

			Dimension d = getSize();

			int wt = d.width - insets.right - xt;

			int y = insets.top;

			for (int i = 0; i < labels.length; i++) {
				Dimension dl = labels[i].getMinimumSize();
				labels[i].setBounds(xl, y, wl, dl.height);

				Dimension dt = values[i].getMinimumSize();
				values[i].setBounds(xt, y, wt, dt.height);

				int dy = vgap
						+ ((dl.height > dt.height) ? dl.height : dt.height);

				y += dy;
			}
		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		checkComponent(parent);

		return layoutSize(parent, MINIMUM_SIZE);
	}

	public Dimension preferredLayoutSize(Container parent) {
		checkComponent(parent);

		return layoutSize(parent, PREFERRED_SIZE);
	}

	protected Dimension layoutSize(Container parent, int mode) {
		int wl = 0;
		int wt = 0;
		int h = 0;

		synchronized (parent.getTreeLock()) {
			for (int i = 0; i < labels.length; i++) {
				Dimension dl = (mode == MINIMUM_SIZE) ? labels[i]
						.getMinimumSize() : labels[i].getPreferredSize();

				if (wl < dl.width)
					wl = dl.width;

				Dimension dt = (mode == MINIMUM_SIZE) ? values[i]
						.getMinimumSize() : values[i].getPreferredSize();

				if (wt < dt.width)
					wt = dt.width;

				h += (dl.height > dt.height) ? dl.height : dt.height;
			}

			h += vgap * (labels.length - 1);
		}

		return new Dimension(insets.left + wl + hgap + wt + insets.right,
				insets.top + h + insets.bottom);
	}

	private void checkComponent(Container parent) {
		if (this != parent)
			throw new AWTError("Cannot layout anyone but myself");
	}

	public void removeLayoutComponent(Component comp) {
		// Does nothing
	}
}
