package com.obliquity.genealogy.test.peopleviewer;

import com.obliquity.genealogy.Person;

class PersonListItem implements Comparable {
	protected String givenName;
	protected Person person;
	protected String asString;
	
	public PersonListItem(Person person) {
		this.person = person;
		givenName = person.getGivenName();
		int bYear = person.getBirthYear();
		int dYear = person.getDeathYear();
		asString = givenName + " (" + ((bYear == 0) ? "?" : ""+bYear) + " - " +
			((dYear == 0) ? "?" : ""+dYear) + ")";
	}
	
	public Person getPerson() {
		return person;
	}
	
	public String toString() {
		return asString;
	}

	public int compareTo(Object arg0) {
		PersonListItem that = (PersonListItem)arg0;
		return this.givenName.compareTo(that.givenName);
	}
}
