package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.Core;
import com.obliquity.genealogy.Attribute;
import com.obliquity.genealogy.gedcom.*;

public class AttributeFactory extends GedcomObjectFactory {
	private DateFactory dateFactory = new DateFactory(this);	
	private PlaceFactory placeFactory = new PlaceFactory(this);

	public AttributeFactory(GedcomObjectFactory parent) {
		super(parent);
	}
	
	public Core createRootObject(GedcomRecord record) {
		String tag = record.getTag();
		String value = record.getContent();
		
		GedcomAttribute gedcomAttribute = GedcomAttribute.findAttribute(tag);
		
		if (gedcomAttribute == null)
			return new Attribute();
		else
			return new Attribute(gedcomAttribute.getType(), value);
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
		return false;
	}
}
