package com.obliquity.genealogy.gedcom;

public class GedcomException extends Exception {
	protected int linenumber;
	
	public GedcomException(String message, int linenumber) {
		super(message);
		this.linenumber = linenumber;
	}
	
	public int getLineNumber() {
		return linenumber;
	}
}
