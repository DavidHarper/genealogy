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

package com.obliquity.genealogy.gedcom;

public class GedcomRecord {
	protected int level;

	protected String tag, xref, content;

	public GedcomRecord() {		
	}
	
	public GedcomRecord(int level, String tag, String xref, String content) {
		setProperties(level, tag, xref, content);
	}

	public void setProperties(int level, String tag, String xref, String content) {
		this.level = level;
		this.tag = tag;
		this.xref = xref;
		this.content = content;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setXref(String xref) {
		this.xref = xref;
	}

	public String getXref() {
		return xref;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String toString() {
		return "GedcomRecord[level=" + level + ", tag=" + tag + ((xref != null) ? ", xref=\"" + xref + "\"" : "") +
			((content != null) ? ", content=\"" + content + "\"" : "") + "]";
	}
}
