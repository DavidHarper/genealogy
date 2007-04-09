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
	
	protected HashMap eventHash = new HashMap();
	protected HashMap attributeHash = new HashMap();
	
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
		
		GedcomObjectFactory factory = (GedcomObjectFactory)eventHash.get(tag);
		if (factory != null)
			return factory;
		
		factory = (GedcomObjectFactory)attributeHash.get(tag);
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
