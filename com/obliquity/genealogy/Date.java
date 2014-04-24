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

package com.obliquity.genealogy;

import java.text.DecimalFormat;

public class Date extends Core {
	public static final int EXACT = 0;

	public static final int ABOUT = 1;

	public static final int ESTIMATED = 2;

	public static final int CALCULATED = 3;
	
	public static final int WFT_ESTIMATED = 4;

	protected static final DecimalFormat fmtI4 = new DecimalFormat("####");

	protected static final DecimalFormat fmtI2 = new DecimalFormat("00");

	protected int year = 0;

	protected int month = 0;

	protected int day = 0;

	protected int modifier = EXACT;

	protected int JD = -1;

	protected boolean oldStyle = false;
	
	protected String[] gregorianMonthNames = {null,
			"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"
	};

	public Date() {
		// No-argument constructor for the benefit of sub-classes.
	}

	public Date(int year, int month, int day, int modifier, boolean oldStyle) {
		this.year = year;
		this.month = month;
		this.day = day;

		this.modifier = modifier;
		this.oldStyle = oldStyle;

		calculateJD();
	}

	public Date(int year, int month, int day, int modifier) {
		this(year, month, day, modifier, false);
	}

	public Date(int year, int month, int day, boolean oldStyle) {
		this(year, month, day, EXACT, oldStyle);
	}

	public Date(int year, int month, int day) {
		this(year, month, day, EXACT, false);
	}

	public Date(int year, int month, boolean oldStyle) {
		this(year, month, 0, EXACT, oldStyle);
	}

	public Date(int year, int month) {
		this(year, month, 0, EXACT, false);
	}

	public Date(int year) {
		this(year, 0, 0, EXACT, false);
	}

	protected void calculateJD() {

	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getModifier() {
		return modifier;
	}

	public boolean isExact() {
		return modifier == EXACT;
	}

	public boolean isAbout() {
		return modifier == ABOUT;
	}

	public boolean isEstimated() {
		return modifier == ESTIMATED;
	}

	public boolean isCalculated() {
		return modifier == CALCULATED;
	}

	public boolean isOldStyle() {
		return oldStyle;
	}

	public void add(Object o) throws PropertyException {
		throw new PropertyException("Cannot add an attribute to a "
				+ getClass().getName());
	}

	public String toString() {
		String modstr = "";
		
		switch (modifier) {
		case ABOUT:
			modstr = "[ABOUT]";
			break;
		case ESTIMATED:
			modstr = "[ESTIMATED]";
			break;
		case CALCULATED:
			modstr = "[CALCULATED]";
			break;
			
		case WFT_ESTIMATED:
			modstr = "[WFT-ESTIMATED]";
			break;
		}
		
		return (day == 0 ? "" : "" + day + " ") +
			(month == 0 ? "" : gregorianMonthNames[month] + " ") +
			fmtI4.format(year) + (oldStyle ? "/"+fmtI2.format((year+1)%100) : "") + modstr;

	}
}
