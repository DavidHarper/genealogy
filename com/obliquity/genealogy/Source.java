package com.obliquity.genealogy;

public class Source extends Core {
	protected String text;

	public Source(String text) {
		this.text = text;
	}
	
	public void add(Object o) throws PropertyException {
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
