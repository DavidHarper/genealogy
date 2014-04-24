/*
 * genealogy - a package for reading genealogy data in GEDCOM format
 *
 * Copyright (C) 2008-2014 David Harper at obliquity.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 *
 * See the COPYING file located in the top-level-directory of
 * the archive of this library for complete text of license.
 */

package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FamilyHistoryFactory extends GedcomObjectFactory {
	protected PersonFactory personFactory = new PersonFactory(this);

	protected FamilyFactory familyFactory = new FamilyFactory(this);

	protected NoteFactory noteFactory = new NoteFactory(this);

	protected SourceFactory sourceFactory = new SourceFactory(this);

	protected EventFactory eventFactory = new EventFactory(this);

	protected AttributeFactory attributeFactory = new AttributeFactory(
			personFactory);

	protected Set<Person> people;

	protected Set<Family> families;

	protected Set<Note> notes;

	protected Set<Source> sources;

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

	public boolean handleRecord(Core root, GedcomRecord record,
			GedcomReader reader) {
		return false;
	}

	public void processGedcomFile(GedcomReader reader) throws IOException,
			GedcomReaderException, PropertyException {
		for (GedcomRecord record = reader.nextRecord(); record != null; record = reader
				.nextRecord()) {
			if (record.getLevel() != 0)
				throw new GedcomReaderException("Level is not zero in " + record,
						reader.getLineNumber());

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
		people = new HashSet<Person>();
		families = new HashSet<Family>();
		notes = new HashSet<Note>();
		sources = new HashSet<Source>();

		for (Iterator iter = xrefTable.values().iterator(); iter.hasNext();) {
			Core o = (Core) iter.next();

			if (o instanceof Person)
				people.add((Person)o);
			else if (o instanceof Family)
				families.add((Family)o);
			else if (o instanceof Note)
				notes.add((Note)o);
			else if (o instanceof Source)
				sources.add((Source)o);
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

			chooser.setFileFilter(new FileFilter() {
				public boolean accept(File f) {
					if (f.isDirectory())
						return true;

					String filename = f.getName();
					return filename.toLowerCase().endsWith(".ged");
				}

				public String getDescription() {
					return "GEDCOM files";
				}
			});

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
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

			System.out.println("The GEDCOM file contained:");
			System.out.println("\t" + factory.getFamilies().size()
					+ " families");
			System.out.println("\t" + factory.getPeople().size()
					+ " individuals");
			System.out.println("\t" + factory.getNotes().size() + " notes");
			System.out.println("\t" + factory.getSources().size() + " sources");

			Set families = factory.getFamilies();

			Runtime runtime = Runtime.getRuntime();

			System.gc();

			long totalmem = runtime.totalMemory() / 1024;
			long freemem = runtime.freeMemory() / 1024;
			long usedmem = totalmem - freemem;

			System.out.println("Memory: total=" + totalmem + "kb, free="
					+ freemem + "kb, used=" + usedmem + "kb");

			if (!Boolean.getBoolean("list"))
				System.exit(0);

			for (Iterator iter = families.iterator(); iter.hasNext();) {
				System.out.println("\n--- FAMILY ---");

				Family family = (Family) iter.next();

				showPerson(family.getHusband(), "Husband");
				showPerson(family.getWife(), "Wife");

				Date marriage = family.getMarriageDate();
				if (marriage != null)
					System.out.println("\nMarried: " + marriage);

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
				System.out.println("\tBorn: " + birth);
			Date death = person.getDeathDate();
			if (death != null)
				System.out.println("\tDied: " + death);
		}
	}
}
