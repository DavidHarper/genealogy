package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import javax.swing.JFileChooser;

public class FamilyHistoryFactory extends GedcomObjectFactory {
	protected PersonFactory personFactory = new PersonFactory(this);
	protected FamilyFactory familyFactory = new FamilyFactory(this);
	
	protected NoteFactory noteFactory = new NoteFactory(this);
	protected SourceFactory sourceFactory = new SourceFactory(this);
	
	protected EventFactory eventFactory = new EventFactory(this);
	
	protected AttributeFactory attributeFactory = new AttributeFactory(personFactory);
	
	protected Set people;
	protected Set families;
	protected Set notes;
	protected Set sources;
	
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
			else if (tag.equalsIgnoreCase("TRLR")) {
				makeSets();
				return;
			} else
				factory = null;
			
			if (factory != null)
				factory.processGedcomRecord(record, reader);
			else
				ignoreRecord(record, reader);
		}
	}
	
	protected void makeSets() {
		people = new HashSet();
		families = new HashSet();
		notes = new HashSet();
		sources = new HashSet();
		
		for (Iterator iter = xrefTable.values().iterator(); iter.hasNext();) {
			Core o = (Core)iter.next();
			
			if (o instanceof Person)
				people.add(o);
			else if (o instanceof Family)
				families.add(o);
			else if (o instanceof Note)
				notes.add(o);
			else if (o instanceof Source)
				sources.add(o);
		}
	}
	
	public Set getPeople() {
		return people;
	}
	
	public Set getFamilies() {
		return families;
	}
	
	public Set getNotes() {
		return notes;
	}
	
	public Set getSources() {
		return sources;
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
			
			boolean debugging = Boolean.getBoolean("debug");
			
			factory.setDebugging(debugging);
			
			factory.processGedcomFile(reader);
			
			Set families = factory.getFamilies();
			
			Runtime runtime = Runtime.getRuntime();
			
			System.gc();
			
			long totalmem = runtime.totalMemory()/1024;
			long freemem  = runtime.freeMemory()/1024;
			long usedmem  = totalmem - freemem;
			
			System.out.println("Memory: total=" + totalmem + "kb, free=" + freemem +
					"kb, used=" + usedmem + "kb");
			
			if (Boolean.getBoolean("nolist"))
				System.exit(0);
			
			for (Iterator iter = families.iterator(); iter.hasNext();) {
				System.out.println("\n--- FAMILY ---");

				Family family = (Family)iter.next();
				
				showPerson(family.getHusband(), "Husband");
				showPerson(family.getWife(), "Wife");
				
				Date marriage = family.getMarriageDate();
				if (marriage != null)
					System.out.println("\nMarried: " + marriage.asString());
				
				for (int i = 1; i <= family.getChildCount(); i++)
					showPerson(family.getChild(i), "Child #" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showPerson(Person person, String caption) {
		System.out.print("\n" + caption + ": ");
		
		if (person == null)
			System.out.println(" not known");
		else {
			System.out.println(person.getName().getFullName());
			Date birth = person.getBirthDate();
			if (birth != null)
				System.out.println("\tBorn: " + birth.asString());
			Date death = person.getDeathDate();
			if (death != null)
				System.out.println("\tDied: " + death.asString());		
		}
	}
}
