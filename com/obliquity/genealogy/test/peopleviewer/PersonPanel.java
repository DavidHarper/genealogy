package com.obliquity.genealogy.test.peopleviewer;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.obliquity.genealogy.*;

class PersonPanel extends JPanel implements LayoutManager {
	protected Person person;

	protected JLabel[] labels;

	protected PersonTextField[] values;

	protected int vgap = 5;

	protected int hgap = 5;
	
	protected Insets myInsets = new Insets(10, 10, 10, 10);

	protected static final int MINIMUM_SIZE = 0;

	protected static final int PREFERRED_SIZE = 1;

	protected JLabel lblName = new JLabel("NAME");

	protected PersonTextField txtBirth = new PersonTextField();

	protected PersonTextField txtBaptism = new PersonTextField();

	protected PersonTextField txtDeath = new PersonTextField();

	protected PersonTextField txtBurial = new PersonTextField();

	public PersonPanel() {
		super(null);
		setLayout(this);

		add(lblName);

		int nrows = 4;

		labels = new JLabel[nrows];
		values = new PersonTextField[nrows];

		labels[0] = new JLabel("Birth:");
		values[0] = txtBirth;

		labels[1] = new JLabel("Baptism:");
		values[1] = txtBaptism;

		labels[2] = new JLabel("Death:");
		values[2] = txtDeath;

		labels[3] = new JLabel("Burial:");
		values[3] = txtBurial;

		Font fntBold16 = new Font("sansserif", Font.BOLD, 16);

		Font fntBold14 = new Font("sansserif", Font.BOLD, 14);
		Font fntPlain14 = new Font("sansserif", Font.PLAIN, 14);

		lblName.setFont(fntBold16);

		for (int i = 0; i < labels.length; i++) {
			labels[i].setFont(fntBold14);
			add(labels[i]);

			values[i].setFont(fntPlain14);
			add(values[i]);
		}

		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}

	public void setPerson(Person person) {
		this.person = person;
		refresh();
	}

	protected void refresh() {
		lblName.setText(person == null ? "" : person.getName().toString());

		txtBirth.setText(person == null || person.getBirth() == null ? ""
				: person.getBirth().getDateAndPlace(), false);

		txtBaptism.setText(person == null || person.getBaptism() == null ? ""
				: person.getBaptism().getDateAndPlace(), false);

		txtDeath.setText(person == null || person.getDeath() == null ? ""
				: person.getDeath().getDateAndPlace(), false);

		txtBurial.setText(person == null || person.getBurial() == null ? ""
				: person.getBurial().getDateAndPlace(), false);

		revalidate();
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

			Insets insets = getInsets();

			int xl = insets.left + myInsets.left;
			int xt = xl + wl + hgap;

			Dimension d = getSize();

			int wt = d.width - (insets.right + myInsets.right) - xt;

			int y = insets.top + myInsets.top;

			Dimension db = lblName.getMinimumSize();

			lblName.setBounds(xl, y, db.width, db.height);

			y += db.height + vgap;

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
		int wt = 600;
		int h = 0;

		synchronized (parent.getTreeLock()) {
			Dimension db = (mode == MINIMUM_SIZE) ? lblName.getMinimumSize()
					: lblName.getPreferredSize();

			h += db.height + vgap;

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

		Insets insets = getInsets();

		int width = insets.left + myInsets.left + wl + hgap + wt + myInsets.right + insets.right;
		int height = insets.top + myInsets.top + h + myInsets.bottom + insets.bottom;

		return new Dimension(width, height);
	}

	private void checkComponent(Container parent) {
		if (this != parent)
			throw new AWTError("Cannot layout anyone but myself");
	}

	public void removeLayoutComponent(Component comp) {
		// Does nothing
	}

	class PersonTextField extends JTextField {
		public PersonTextField() {
			super();
			init();
		}

		public PersonTextField(int columns) {
			super(columns);
			init();
		}

		public PersonTextField(String text) {
			super(text);
			init();
		}

		protected void init() {
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						setEditable(true);
						getCaret().setVisible(true);
					}
				}
			});

			addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					setEditable(false);
				}
			});

			setToolTipText("Click to edit");
		}

		public void setText(String text, boolean editable) {
			super.setText(text);
			setEditable(editable);
		}
	}
}
