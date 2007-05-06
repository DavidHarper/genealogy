package com.obliquity.genealogy.test.peopleviewer;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;
import com.obliquity.genealogy.gedcom.factory.FamilyHistoryFactory;

public class PeopleViewer {
	public static void main(String[] args) {
		PeopleViewer pv = new PeopleViewer();
		pv.execute(args);
	}
	
	public void execute(String[] args) {
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

			Set people = factory.getPeople();

			Runtime runtime = Runtime.getRuntime();

			System.gc();

			long totalmem = runtime.totalMemory() / 1024;
			long freemem = runtime.freeMemory() / 1024;
			long usedmem = totalmem - freemem;

			System.out.println("Memory: total=" + totalmem + "kb, free="
					+ freemem + "kb, used=" + usedmem + "kb");

			Map familynames = new HashMap();
			
			for (Iterator iter = people.iterator(); iter.hasNext();) {
				Person person = (Person)iter.next();
				Name name = person.getName();
				String familyName = name.getFamilyName();
				
				Vector family = (Vector)familynames.get(familyName);
				
				if (family == null) {	
					family = new Vector();
					familynames.put(familyName, family);
				}
				
				family.add(new PersonListItem(person));
			}
			
			Vector allFamilies = new Vector();
					
			for (Iterator iter = familynames.keySet().iterator(); iter.hasNext();) {
				String familyName = (String)iter.next();
				
				Vector family = (Vector)familynames.get(familyName);
				
				Collections.sort(family);
				
				allFamilies.add(new FamilyListItem(familyName, family));
			}
				
			FamilyLister fl = new FamilyLister(allFamilies);
			
			JFrame frame = new JFrame("PeopleViewer");
			frame.getContentPane().add(fl);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
