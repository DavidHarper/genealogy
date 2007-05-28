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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
