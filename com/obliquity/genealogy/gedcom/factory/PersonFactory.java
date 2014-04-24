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

public class PersonFactory extends GedcomObjectFactory {
	protected EventFactory eventFactory;
	protected NoteFactory noteFactory;
	protected SourceFactory sourceFactory;
	protected AttributeFactory attributeFactory;
	protected NameFactory nameFactory = new NameFactory(this);
	
	protected HashMap<String, EventFactory> eventHash = new HashMap<String, EventFactory>();
	protected HashMap<String, AttributeFactory> attributeHash = new HashMap<String, AttributeFactory>();
	
	protected String[] eventTags = {
			"BIRT", "CHR",  "DEAT", "BURI", "CREM", "ADOP",
			"BAPM", "BARM", "BASM", "BLES", "CHRA", "CONF",
			"FCOM", "ORDN", "NATU", "EMIG", "IMMI", "CENS",
			"PROB", "WILL", "GRAD", "RETI", "BAPL", "CONL",
			"ENDL", "SLGC", "EVEN"
	};
	
	protected String[] attributeTags = {
			"CAST", "DSCR", "EDUC", "IDNO", "NATI", "NCHI",
			"NMR",  "OCCU", "PROP", "RELI", "RESI", "SSN",
			"TITL"
	};
	
	public PersonFactory(GedcomObjectFactory parent) {
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

	public void setAttributeFactory(AttributeFactory attributeFactory) {
		this.attributeFactory = attributeFactory;
		registerAttributes();
	}
	
	protected void registerAttributes() {
		for (int i = 0; i < attributeTags.length; i++)
			attributeHash.put(attributeTags[i], attributeFactory);
	}
	
	public Core createRootObject(GedcomRecord record) {
		String xref = record.getXref();
		
		Person person = (Person)getObjectByXref(xref);
		
		if (person == null) {
			person = new Person();
			putObjectByXref(xref, person);
		}
		
		return person;
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		if (tag.equalsIgnoreCase("SOUR"))
			return sourceFactory;
		else if (tag.equalsIgnoreCase("NOTE"))
			return noteFactory;
		else if (tag.equalsIgnoreCase("NAME"))
			return nameFactory;
		
		GedcomObjectFactory factory = eventHash.get(tag);
		if (factory != null)
			return factory;
		
		factory = attributeHash.get(tag);
		if (factory != null)
			return factory;
		
		return null;
	}

	public boolean handleRecord(Core root, GedcomRecord record, GedcomReader reader) {
		String tag = record.getTag();
		Person person = (Person)root;
		
		if (tag.equalsIgnoreCase("SEX")) {
			person.setMale(record.getContent().equalsIgnoreCase("M"));
			return true;
		} else if (tag.equalsIgnoreCase("FAMS")) {
			person.addFamilyAsSpouse(findFamily(record.getContent()));
			return false; // force parent to ignore subsidiary records
		} else if (tag.equalsIgnoreCase("FAMC")) {
			person.addFamilyAsChild(findFamily(record.getContent()));
			return false; // force parent to ignore subsidiary records
		}
		
		return false;
	}

	protected Family findFamily(String xref) {
		xref = xref.substring(1, xref.length() - 1);
		
		if (isDebugging())
			System.out.print(">>> Looking for family " + xref + " ... ");
		
		Family family = (Family)getObjectByXref(xref);
		
		if (family != null && isDebugging())
			System.out.println("found " + family);
		
		if (family == null) {
			if (isDebugging())
				System.out.println("not found, creating new family");
			family = new Family();
			putObjectByXref(xref, family);
		}
		
		return family;
	}
}
