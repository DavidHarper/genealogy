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

public class DateRange extends Date {
	public static final int BEFORE = 1;
	public static final int AFTER = 2;
	public static final int BETWEEN = 3;
	
	protected int flags;
	protected Date date2 = null;
	
	public DateRange(Date date1, Date date2, int flags) {
		this.year = date1.year;
		this.month = date1.month;
		this.day = date1.day;
		this.JD = date1.JD;
		this.modifier = date1.modifier;
		this.oldStyle = date1.oldStyle;
		
		this.date2 = date2;
		
		this.flags = flags;
	}
	
	public DateRange(Date date1, int flags) {
		this(date1, null, flags);
	}
	
	public DateRange(Date date1, Date date2) {
		this(date1, date2, BETWEEN);
	}

	public int getFlags() {
		return flags;
	}

	public Date getDate2() {
		return date2;
	}
	
	public String toString() {
		switch (flags) {
		case BEFORE:
		return "BEFORE " + super.toString();
		
		case AFTER:
			return "AFTER " + super.toString();
			
		case BETWEEN:
			return "BETWEEN " + super.toString() + " AND " + date2.toString();
			
		default:
			return "DateRange[UNKNOWN " + super.toString() + "]";
		}
	}

}
