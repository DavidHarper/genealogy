package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.Core;
import com.obliquity.genealogy.gedcom.*;

public class SourceFactory extends GedcomObjectFactory {
	public SourceFactory(GedcomObjectFactory parent) {
		super(parent);
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
