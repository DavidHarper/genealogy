package com.obliquity.genealogy;

import java.util.Vector;

public class Family extends Core {
	protected Person husband;
	protected Person wife;
	
	protected Vector children = new Vector();
	
	protected int nChildren = -1;
	
	protected Event marriage;
	
	protected Vector events = new Vector();
	
	public void add(Object o) throws PropertyException {
		if (o instanceof Event)
			addEvent((Event)o);
		else
			throw new PropertyException("Unable to add object of type "
					+ o.getClass().getName() + " to a Family");
	}

	public void addEvent(Event event) {
		events.add(event);
		
		switch (event.getType()) {
		case Event.MARRIAGE:
			if (marriage == null)
				marriage = event;
			break;
		}
	}
	
	public void addPersonAsHusband(Person person) {
		husband = person;
	}
	
	public Person getHusband() {
		return husband;
	}
	
	public void addPersonAsWife(Person person) {
		wife = person;
	}
	
	public Person getWife() {
		return wife;
	}
	
	public void addPersonAsChild(Person person) {
		children.add(person);
	}
	
	public int getChildCount() {
		return nChildren < 0 ? children.size() : nChildren;
	}

	public void setChildCount(int i) {
		nChildren = i;
	}
	
	public Person getChild(int i) {
		if (i < 1 || i > children.size())
			return null;
		else return (Person)children.elementAt(i-1);
	}
	
	public Event getMarriage() {
		return marriage;
	}
	
	public Date getMarriageDate() {
		return (marriage == null) ? null : marriage.getDate();
	}
	
	public String toString() {
		return "Family[husband=" + ((husband == null) ? "null" : husband.getName().toString()) +
			", wife=" + ((wife == null) ? "null" : wife.getName().toString()) +
			", marriage=" + ((marriage == null) ? "null" : marriage.toString()) +
			", children=" + getChildCount() + "]";
	}
}
