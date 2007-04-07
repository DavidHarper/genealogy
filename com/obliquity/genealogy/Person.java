package com.obliquity.genealogy;

public class Person extends Core {
	protected Name name;
	
	protected boolean male;
	
	protected Event birth;
	protected Event death;
	
	protected Event[] events;
	
	protected Attribute[] attributes;
	
	protected Family[] familyAsChild;
	protected Family[] familyAsSpouse;
	
	public void add(Object o) {
	}

}
