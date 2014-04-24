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

package com.obliquity.genealogy.test.peopleviewer;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;
import com.obliquity.genealogy.gedcom.factory.FamilyHistoryFactory;
import com.obliquity.genealogy.test.FamilyPage;

public class PeopleViewer {
	protected JTabbedPane tabbedPane = new JTabbedPane();
	
	public static void main(String[] args) {
		PeopleViewer pv = new PeopleViewer();
		pv.execute(args);
	}
	
	public void execute(String[] args) {
		File file = null;

		if (args.length == 0) {
			JFileChooser chooser = new JFileChooser();

			FileFilter filter = new FileNameExtensionFilter("GEDCOM file", "ged");
			chooser.setFileFilter(filter);

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

			Set people = factory.getPeople();

			Runtime runtime = Runtime.getRuntime();

			System.gc();

			long totalmem = runtime.totalMemory() / 1024;
			long freemem = runtime.freeMemory() / 1024;
			long usedmem = totalmem - freemem;

			System.out.println("Memory: total=" + totalmem + "kb, free="
					+ freemem + "kb, used=" + usedmem + "kb");
			
			if (Boolean.getBoolean("nodisplay"))
				System.exit(0);

			Map<String,Vector<PersonListItem>> familynames = new HashMap<String,Vector<PersonListItem>>();
			
			for (Iterator iter = people.iterator(); iter.hasNext();) {
				Person person = (Person)iter.next();
				Name name = person.getName();
				String familyName = name.getFamilyName();
				
				Vector<PersonListItem> family = familynames.get(familyName);
				
				if (family == null) {	
					family = new Vector<PersonListItem>();
					familynames.put(familyName, family);
				}
				
				family.add(new PersonListItem(person));
			}
			
			Vector<FamilyListItem> allFamilies = new Vector<FamilyListItem>();
					
			for (Iterator iter = familynames.keySet().iterator(); iter.hasNext();) {
				String familyName = (String)iter.next();
				
				Vector<PersonListItem> family = familynames.get(familyName);
				
				Collections.sort(family);
				
				allFamilies.add(new FamilyListItem(familyName, family));
			}
				
			FamilyPage familyPage = new FamilyPage();
			
			FamilyLister fl = new FamilyLister(allFamilies, tabbedPane, familyPage);
			
			tabbedPane.add("People", fl);
			tabbedPane.add("Family", new JScrollPane(familyPage,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
			
			JFrame frame = new JFrame("PeopleViewer");
			frame.getContentPane().add(tabbedPane);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		} 
		catch (GedcomReaderException gre) {
			System.err.println("GedcomReaderException at line " + gre.getLineNumber() + " of input file");
			gre.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
