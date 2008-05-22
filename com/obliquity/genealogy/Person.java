package com.obliquity.genealogy;

import java.util.Iterator;
import java.util.Vector;

public class Person extends Core {
	protected Name name;

	protected boolean male;

	protected Event birth;
	protected Event baptism;
	protected Event death;
	protected Event burial;

	protected Vector<Event> events = new Vector<Event>();

	protected Vector<Attribute> attributes = new Vector<Attribute>();
	
	protected Vector<Note> notes = new Vector<Note>();
	
	protected Vector<Source> sources = new Vector<Source>();

	protected Vector<Family> familyAsChild = new Vector<Family>();

	protected Vector<Family> familyAsSpouse = new Vector<Family>();

	public void add(Object o) throws PropertyException {
		if (o instanceof Event)
			addEvent((Event) o);
		else if (o instanceof Attribute)
			addAttribute((Attribute) o);
		else if (o instanceof Note)
			addNote((Note)o);
		else if (o instanceof Source)
			addSource((Source)o);
		else if (o instanceof Name)
			setName((Name)o);
		else
			throw new PropertyException("Unable to add object of type "
					+ o.getClass().getName() + " to a Person");
	}

	public void setName(Name name) {
		this.name = name;
	}
	
	public Name getName() {
		return name;
	}
	
	public String getFamilyName() {
		return name == null ? null : name.getFamilyName();
	}
	
	public String getGivenName() {
		return name == null ? null : name.getGivenName();
	}
	
	public void setMale(boolean male) {
		this.male = male;
	}
	
	public boolean isMale() {
		return male;
	}
	
	public void addEvent(Event event) {
		events.add(event);
		
		switch (event.getType()) {
		case Event.BIRTH:
			if (birth == null)
				birth = event;
			break;
			
		case Event.BAPTISM:
			if (baptism == null)
				baptism = event;
			break;
			
		case Event.DEATH:
			if (death == null)
				death = event;
			break;
			
		case Event.BURIAL:
			if (burial == null);
		}
	}
	
	public Iterator getEventIterator() {
		return events.iterator();
	}
	
	public void addAttribute(Attribute attr) {
		attributes.add(attr);
	}
	
	public Iterator getAttributeIterator() {
		return attributes.iterator();
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public Iterator getNoteIterator() {
		return notes.iterator();
	}
	
	public void addSource(Source source) {
		sources.add(source);
	}
	
	public Iterator getSourceIterator() {
		return sources.iterator();
	}
	
	public void addFamilyAsChild(Family family) {
		familyAsChild.add(family);
	}
	
	public Iterator getFamilyAsChildIterator() {
		return familyAsChild.iterator();
	}
	
	public Family[] getFamiliesAsChild() {
		return (Family[])familyAsChild.toArray(new Family[0]);
	}
	
	public boolean hasFamilyAsChild() {
		return !familyAsChild.isEmpty();
	}
	
	public void addFamilyAsSpouse(Family family) {
		familyAsSpouse.add(family);
	}
	
	public Iterator getFamilyAsSpouseIterator() {
		return familyAsSpouse.iterator();
	}
	
	public Family[] getFamiliesAsSpouse() {
			return (Family[])familyAsSpouse.toArray(new Family[0]);
	}
	
	public boolean hasFamilyAsSpouse() {
		return !familyAsSpouse.isEmpty();
	}
	
	public Event getBirth() {
		return birth;
	}
	
	public Date getBirthDate() {
		return (birth == null) ? null : birth.getDate();
	}
	
	public int getBirthYear() {
		return (birth == null  || birth.getDate() == null) ? 0 : birth.getDate().getYear();
	}
	
	public Date getDeathDate() {
		return (death == null) ? null : death.getDate();
	}
	
	public int getDeathYear() {
		return (death == null || death.getDate() == null) ? 0 : death.getDate().getYear();
	}
	
	public Event getBaptism() {
		return baptism;
	}
	
	public Event getDeath() {
		return death;
	}
	
	public Event getBurial() {
		return burial;
	}
	
	public String toString() {
		return "Person[name=" + ((name == null) ? "null" : name.toString()) +
			", birth=" + ((birth == null) ? "null" : birth.toString()) + "]";
	}
}
