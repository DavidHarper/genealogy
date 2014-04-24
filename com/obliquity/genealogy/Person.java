package com.obliquity.genealogy;
/*
 * genealogy - a package for reading genealogy data in GEDCOM format
 *
 * Copyright (C) 2008-2014 David Harper at obliquity.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 *
 * See the COPYING file located in the top-level-directory of
 * the archive of this library for complete text of license.
 */


import java.util.Vector;
import java.util.List;
import java.util.Collections;

public class Person extends Core {
	protected Name name;

	protected boolean male;

	protected Event birth;
	protected Event baptism;
	protected Event death;
	protected Event burial;

	protected List<Event> events = new Vector<Event>();

	protected List<Attribute> attributes = new Vector<Attribute>();
	
	protected List<Note> notes = new Vector<Note>();
	
	protected List<Source> sources = new Vector<Source>();

	protected List<Family> familyAsChild = new Vector<Family>();

	protected List<Family> familyAsSpouse = new Vector<Family>();

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
			if (burial == null)
				burial = event;
			break;
		}
	}
	
	public List<Event> getEvents() {
		return Collections.unmodifiableList(events);
	}
	
	public void addAttribute(Attribute attr) {
		attributes.add(attr);
	}
	
	public List<Attribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}
	
	public void addNote(Note note) {
		notes.add(note);
	}
	
	public List<Note> getNotes() {
		return Collections.unmodifiableList(notes);
	}
	
	public void addSource(Source source) {
		sources.add(source);
	}
	
	public List<Source> getSources() {
		return Collections.unmodifiableList(sources);
	}
	
	public void addFamilyAsChild(Family family) {
		familyAsChild.add(family);
	}
	
	public List<Family> getFamiliesAsChild() {
		return Collections.unmodifiableList(familyAsChild);
	}
	
	public boolean hasFamilyAsChild() {
		return !familyAsChild.isEmpty();
	}
	
	public void addFamilyAsSpouse(Family family) {
		familyAsSpouse.add(family);
	}
	
	public List<Family> getFamiliesAsSpouse() {
			return Collections.unmodifiableList(familyAsSpouse);
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
