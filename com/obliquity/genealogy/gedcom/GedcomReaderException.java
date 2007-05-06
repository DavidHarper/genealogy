package com.obliquity.genealogy.gedcom;

public class GedcomReaderException extends Exception {
	public static final int NO_LINE_NUMBER = -1;
	
	protected int linenumber;
	
	public GedcomReaderException(String message, int linenumber) {
		super(message);
		this.linenumber = linenumber;
	}
	
	public int getLineNumber() {
		return linenumber;
	}

}
