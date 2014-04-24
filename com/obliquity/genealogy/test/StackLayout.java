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
