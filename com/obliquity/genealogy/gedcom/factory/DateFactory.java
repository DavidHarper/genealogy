package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;

public class DateFactory extends GedcomObjectFactory {
	public DateFactory(GedcomObjectFactory parent) {
		super(parent);
	}
	
	public Core createRootObject(GedcomRecord record) {
		return new Date(record.getContent());
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean handleRecord(Core root, GedcomRecord record, GedcomReader reader) {
		return false;
	}

}
