package com.obliquity.genealogy;

public class Note extends Core {
	protected String text;
	protected Source source;
	
	public Note(String text) {
		this.text = text;
	}
	
	public void add(Object o) throws PropertyException {
		if (o instanceof Source)
			source = (Source)o;
		else
			throw new PropertyException("Unable to add object of type "
					+ o.getClass().getName() + " to a Note");
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void concatenateText(String newText) {
		text = text + newText;
	}
	
	public void continueText(String newText) {
		text = text + "\n" + newText;
	}
}
