package com.obliquity.genealogy.test;

import java.awt.*;

public class StackLayout implements LayoutManager {
	protected int vgap;

	public StackLayout(int vgap) {
		this.vgap = vgap;
	}
	
	public int getVgap() {
		return vgap;
	}

	public void setVgap(int vgap) {
		this.vgap = vgap;
	}

	public void addLayoutComponent(String name, Component comp) {
	}

	public void removeLayoutComponent(Component comp) {
	}

	public Dimension preferredLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();

			int w = 0;
			int h = 0;
			
			for (int i = 0; i < ncomponents; i++) {
				Component comp = parent.getComponent(i);
				Dimension d = comp.getPreferredSize();
				if (w < d.width) {
					w = d.width;
				}
				
				h += d.height;
			}
			
			return new Dimension(insets.left + insets.right + w, 
					insets.top + insets.bottom + h + vgap * (ncomponents - 1));
		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();

			int w = 0;
			int h = 0;
			
			for (int i = 0; i < ncomponents; i++) {
				Component comp = parent.getComponent(i);
				Dimension d = comp.getMinimumSize();
				if (w < d.width) {
					w = d.width;
				}
				
				h += d.height;
			}
			return new Dimension(insets.left + insets.right +  w,
					insets.top + insets.bottom + h + vgap * (ncomponents - 1));
		}
	}

	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Dimension d = parent.getSize();
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();
			
			int w = d.width - (insets.left + insets.right);
			int x = insets.left;
			int y = insets.top;

			for (int i = 0; i < ncomponents; i++) {
				Component comp = parent.getComponent(i);
				d = comp.getMinimumSize();
				
				int h = d.height;

				comp.setBounds(x, y, w, h);
				
				y += h + vgap;
			}
		}
	}

	/**
	 * Returns the string representation of this grid layout's values.
	 * 
	 * @return a string representation of this grid layout
	 */
	public String toString() {
		return getClass().getName() + "[vgap=" + vgap + "]";
	}
}
