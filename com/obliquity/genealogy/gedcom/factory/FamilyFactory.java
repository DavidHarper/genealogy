package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.Core;
import com.obliquity.genealogy.gedcom.*;

public class FamilyFactory extends GedcomObjectFactory {
	protected EventFactory eventFactory;
	protected NoteFactory noteFactory;
	protected SourceFactory sourceFactory;
	
	public FamilyFactory(GedcomObjectFactory parent) {
		super(parent);
	}
	
	public void setEventFactory(EventFactory eventFactory) {
		this.eventFactory = eventFactory;
	}
	
	public void setNoteFactory(NoteFactory noteFactory) {
		this.noteFactory = noteFactory;
	}
	
	public void setSourceFactory(SourceFactory sourceFactory) {
		this.sourceFactory = sourceFactory;
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
