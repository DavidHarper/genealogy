package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;

import java.io.*;

import javax.swing.JFileChooser;

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

	public void processGedcomFile(GedcomReader reader)
		throws IOException, GedcomException, PropertyException {
		for (GedcomRecord record = reader.nextRecord();
			 record != null; record = reader.nextRecord()) {
			if (record.getLevel() != 0)
				throw new GedcomException("Level is not zero in " + record, reader.getLineNumber());
			
			String tag = record.getTag();
			
			GedcomObjectFactory factory = null;
			
			if (tag.equalsIgnoreCase("INDI"))
				factory = personFactory;
			else if (tag.equalsIgnoreCase("FAM"))
				factory = familyFactory;
			else if (tag.equalsIgnoreCase("NOTE"))
				factory = noteFactory;
			else if (tag.equalsIgnoreCase("SOUR"))
				factory = sourceFactory;
			else if (tag.equalsIgnoreCase("TRLR"))
				return;
			else
				factory = null;
			
			if (factory != null)
				factory.processGedcomRecord(record, reader);
			else
				ignoreRecord(record, reader);
		}
	}
	
	public static void main(String[] args) {
		File file = null;
		
		if (args.length == 0) {
			JFileChooser chooser = new JFileChooser();

			if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		       file = chooser.getSelectedFile();
		} else {
			file = new File(args[0]);
		}
		
		if (file == null) {
			System.err.println("No file specified");
			System.exit(1);
		}
		
		try {
			GedcomReader reader = new GedcomReader(file);
			
			FamilyHistoryFactory factory = new FamilyHistoryFactory();
			
			factory.processGedcomFile(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
