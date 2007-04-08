package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;

public class EventFactory extends GedcomObjectFactory {
	DateFactory dateFactory = new DateFactory(this);	
	PlaceFactory placeFactory = new PlaceFactory(this);
	

	public EventFactory(GedcomObjectFactory parent) {
		super(parent);
	}
	
	public Core createRootObject(GedcomRecord record) {
		String tag = record.getTag();
		
		GedcomEvent gedcomEvent = GedcomEvent.findEvent(tag);
		
		if (gedcomEvent == null)
			return new Event();
		else
			return new Event(gedcomEvent.getType());
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		if (tag.equalsIgnoreCase("DATE"))
			return dateFactory;
		else if (tag.equalsIgnoreCase("PLAC"))
			return placeFactory;
		else
			return null;
	}

	public boolean handleRecord(Core root, GedcomRecord record, GedcomReader reader) {
		if (record.getTag().equalsIgnoreCase("TYPE"))
			((Event)root).setTypeName(record.getContent());
		
		return false;
	}

}
