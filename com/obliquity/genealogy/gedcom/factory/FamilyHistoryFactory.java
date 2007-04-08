package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.Core;
import com.obliquity.genealogy.gedcom.GedcomObjectFactory;
import com.obliquity.genealogy.gedcom.GedcomReader;
import com.obliquity.genealogy.gedcom.GedcomRecord;

public class FamilyHistoryFactory extends GedcomObjectFactory {
	PersonFactory personFactory = new PersonFactory(this);
	FamilyFactory familyFactory = new FamilyFactory(this);
	
	NoteFactory noteFactory = new NoteFactory(this);
	SourceFactory sourceFactory = new SourceFactory(this);
	
	EventFactory eventFactory = new EventFactory(this);
	
	AttributeFactory attributeFactory = new AttributeFactory(personFactory);
	
	public FamilyHistoryFactory() {
		super(null);
		
		personFactory.setNoteFactory(noteFactory);
		personFactory.setSourceFactory(sourceFactory);
		personFactory.setEventFactory(eventFactory);
		personFactory.setAttributeFactory(attributeFactory);
		
		familyFactory.setNoteFactory(noteFactory);
		familyFactory.setSourceFactory(sourceFactory);
		familyFactory.setEventFactory(eventFactory);
		
		noteFactory.setSourceFactory(sourceFactory);
	}
	
	public Core createRootObject(GedcomRecord record) {
		return null;
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		return null;
	}

	public boolean handleRecord(Core root, GedcomRecord record, GedcomReader reader) {
		return false;
	}

}
