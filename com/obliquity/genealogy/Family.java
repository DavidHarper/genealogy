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

package com.obliquity.genealogy;

import java.util.Vector;
import java.util.List;

public class Family extends Core {
	protected Person husband;
	protected Person wife;
	
	protected List<Person> children = new Vector<Person>();
	
	protected int nChildren = -1;
	
	protected Event marriage;
	
	protected List<Event> events = new Vector<Event>();
	
	protected List<Note> notes = new Vector<Note>();

	public void add(Object o) throws PropertyException {
		if (o instanceof Event)
			addEvent((Event)o);
		else if (o instanceof Note)
			addNote((Note)o);
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
	
	public void addNote(Note note) {
		notes.add(note);
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
		else return children.get(i-1);
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
