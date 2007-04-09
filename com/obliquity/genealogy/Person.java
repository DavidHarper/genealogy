package com.obliquity.genealogy;

import java.util.Vector;

public class Person extends Core {
	protected Name name;

	protected boolean male;

	protected Event birth;
	protected Event baptism;
	protected Event death;
	protected Event burial;

	protected Vector events = new Vector();

	protected Vector attributes = new Vector();
	
	protected Vector notes = new Vector();
	
	protected Vector sources = new Vector();

	protected Vector familyAsChild = new Vector();

	protected Vector familyAsSpouse = new Vector();

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
	
	public void addAttribute(Attribute attr) {
		attributes.add(attr);
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public void addSource(Source source) {
		sources.add(source);
	}
	
	public void addFamilyAsChild(Family family) {
		familyAsChild.add(family);
	}
	
	public void addFamilyAsSpouse(Family family) {
		familyAsSpouse.add(family);
	}
	
	public Event getBirth() {
		return birth;
	}
	
	public Date getBirthDate() {
		return (birth == null) ? null : birth.getDate();
	}
	
	public Date getDeathDate() {
		return (death == null) ? null : death.getDate();
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
