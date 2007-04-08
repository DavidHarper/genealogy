package com.obliquity.genealogy;

public class Note extends Core {
	protected String text;
	protected Source source;
	
	public void add(Object o) throws PropertyException {
		if (o instanceof Source)
			source = (Source)o;
	}
}
