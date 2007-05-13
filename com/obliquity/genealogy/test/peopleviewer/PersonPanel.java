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

	protected static final int MINIMUM_SIZE = 0;

	protected static final int PREFERRED_SIZE = 1;

	protected Insets insets = new Insets(15, 10, 10, 10);

	protected PersonTextField txtName = new PersonTextField();

	protected PersonTextField txtBirth = new PersonTextField();

	protected PersonTextField txtBaptism = new PersonTextField();

	protected PersonTextField txtDeath = new PersonTextField();

	protected PersonTextField txtBurial = new PersonTextField();

	public PersonPanel() {
		super(null);
		setLayout(this);

		int nrows = 5;

		labels = new JLabel[nrows];
		values = new PersonTextField[nrows];

		labels[0] = new JLabel("NAME:");
		values[0] = txtName;

		labels[1] = new JLabel("Birth:");
		values[1] = txtBirth;

		labels[2] = new JLabel("Baptism:");
		values[2] = txtBaptism;

		labels[3] = new JLabel("Death:");
		values[3] = txtDeath;

		labels[4] = new JLabel("Burial:");
		values[4] = txtBurial;

		Font fntBold14 = new Font("sansserif", Font.BOLD, 14);
		Font fntPlain14 = new Font("sansserif", Font.PLAIN, 14);

		labels[0].setFont(fntBold14);

		Dimension size = labels[0].getPreferredSize();
		size.width = 800;

		values[0].setFont(fntBold14);
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
		
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}

	public void setPerson(Person person) {
		this.person = person;
		refresh();
	}

	protected void refresh() {
		txtName.setText(person == null ? "" : person.getName().toString(), false);

		txtBirth.setText(person == null || person.getBirth() == null ? ""
				: person.getBirth().getDateAndPlace(), false);

		txtBaptism.setText(person == null || person.getBaptism() == null ? ""
				: person.getBaptism().getDateAndPlace(), false);
		
		txtDeath.setText(person == null || person.getDeath() == null ? ""
				: person.getDeath().getDateAndPlace(), false);
		
		txtBurial.setText(person == null || person.getBurial() == null ? ""
				: person.getBurial().getDateAndPlace(), false);
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
						getCaret().setVisible(true);					}
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
