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

package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;

import java.util.HashMap;

public class FamilyFactory extends GedcomObjectFactory {
	protected EventFactory eventFactory;
	protected NoteFactory noteFactory;
	protected SourceFactory sourceFactory;
	
	protected HashMap<String, EventFactory> eventHash = new HashMap<String, EventFactory>();
	
	protected String[] eventTags = {
			"ANUL", "CENS", "DIV",  "DIVF", "ENGA", "MARR",
			"MARB", "MARC", "MARL", "MARS", "SLGS", "EVEN"
	};

	public FamilyFactory(GedcomObjectFactory parent) {
		super(parent);
	}
	
	public void setEventFactory(EventFactory eventFactory) {
		this.eventFactory = eventFactory;
		registerEvents();
	}
	
	protected void registerEvents() {
		for (int i = 0; i < eventTags.length; i++)
			eventHash.put(eventTags[i], eventFactory);
	}
	
	public void setNoteFactory(NoteFactory noteFactory) {
		this.noteFactory = noteFactory;
	}
	
	public void setSourceFactory(SourceFactory sourceFactory) {
		this.sourceFactory = sourceFactory;
	}
	
	public Core createRootObject(GedcomRecord record) {
		String xref = record.getXref();
		
		Family family = (Family)getObjectByXref(xref);
		
		if (family == null) {
			family = new Family();
			putObjectByXref(xref, family);
		}
		
		return family;
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		if (tag.equalsIgnoreCase("SOUR"))
			return sourceFactory;
		else if (tag.equalsIgnoreCase("NOTE"))
			return noteFactory;
		
		GedcomObjectFactory factory = (GedcomObjectFactory)eventHash.get(tag);
		if (factory != null)
			return factory;
		
		return null;
	}

	public boolean handleRecord(Core root, GedcomRecord record, GedcomReader reader) {
		String tag = record.getTag();
		Family family = (Family)root;
		
		if (tag.equalsIgnoreCase("NCHI")) {
			family.setChildCount(Integer.parseInt(record.getContent()));
			return true;
		} else if (tag.equalsIgnoreCase("HUSB")) {
			family.addPersonAsHusband(findPerson(record.getContent()));
			return false; // force parent to ignore subsidiary records
		} else if (tag.equalsIgnoreCase("WIFE")) {
			family.addPersonAsWife(findPerson(record.getContent()));
			return false; // force parent to ignore subsidiary records
		} else if (tag.equalsIgnoreCase("CHIL")) {
			family.addPersonAsChild(findPerson(record.getContent()));
			return true;
		}
		
		return false;
	}

	protected Person findPerson(String xref) {
		xref = xref.substring(1, xref.length() - 1);
		
		if (isDebugging())
			System.out.print(">>> Looking for person " + xref + " ... ");
	
		Person person = (Person)getObjectByXref(xref);
		
		if (person != null && isDebugging())
			System.out.println("found " + person);
	
		if (person == null) {
			if (isDebugging())
				System.out.println("not found, creating new person");
			person = new Person();
			putObjectByXref(xref, person);
		}
		
		return person;
	}
}
