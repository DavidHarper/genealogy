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
	DateFactory dateFactory = new DateFactory(eventFactory);	
	PlaceFactory placeFactory = new PlaceFactory(eventFactory);
	
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
	}
	
	public Core createRootObject(GedcomRecord record) {
		// TODO Auto-generated method stub
		return null;
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	public void handleRecord(Core root, GedcomRecord record, GedcomReader reader) {
		// TODO Auto-generated method stub
		
	}

}
