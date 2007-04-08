package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;

public class NoteFactory extends GedcomObjectFactory {
	protected SourceFactory sourceFactory;
	
	public NoteFactory(GedcomObjectFactory parent) {
		super(parent);
	}
	
	public Core createRootObject(GedcomRecord record) {
		return new Note(record.getContent());
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		if (tag.equalsIgnoreCase("SOUR"))
			return sourceFactory;
		else
			return null;
	}

	public boolean handleRecord(Core root, GedcomRecord record, GedcomReader reader) {
		String tag = record.getTag();
		
		if (tag.equalsIgnoreCase("CONC")) {
			((Note)root).concatenateText(record.getContent());
			return true;
		} else if (tag.equalsIgnoreCase("CONT")) {
			((Note)root).continueText(record.getContent());
			return true;
		} else
			return false;
	}
	
	public void setSourceFactory(SourceFactory sourceFactory) {
		this.sourceFactory = sourceFactory;
	}

}
