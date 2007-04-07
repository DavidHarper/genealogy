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
