package com.obliquity.genealogy.test;

import java.awt.BorderLayout;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
				String givenName = name.getGivenName();
				
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
	
	class FamilyLister extends JPanel {
		protected Vector allFamilies;
		JList memberNameList = new JList();
		JList familyNameList;
		
		public FamilyLister(Vector allFamilies) {
			this.allFamilies = allFamilies;
			Collections.sort(this.allFamilies);
			createUI();
		}
		
		protected void createUI() {
			familyNameList = new JList(allFamilies);
			JScrollPane sp1 = new JScrollPane(familyNameList);
						
			JScrollPane sp2 = new JScrollPane(memberNameList);
			
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp2);
			
			add(BorderLayout.CENTER, splitPane);
			
			familyNameList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					FamilyListItem fli = (FamilyListItem)familyNameList.getSelectedValue();
					Vector v = fli.getFamilyMember();
					memberNameList.setListData(v);
				}		
			});
			
			familyNameList.setSelectedIndex(0);
			
			familyNameList.setVisibleRowCount(20);
			memberNameList.setVisibleRowCount(20);
		}
	}
	
	class FamilyListItem implements Comparable {
		protected String familyName;
		protected Vector familyMembers;
		
		public FamilyListItem(String familyName, Vector familyMembers) {
			this.familyName = familyName;
			this.familyMembers = familyMembers;
		}
		
		public String getFamilyName() {
			return familyName;
		}
		
		public Vector getFamilyMember() {
			return familyMembers;
		}
		
		public String toString() {
			return familyName;
		}

		public int compareTo(Object arg0) {
			FamilyListItem that = (FamilyListItem)arg0;
			return familyName.compareTo(that.familyName);
		}
	}
	
	class PersonListItem implements Comparable {
		protected String givenName;
		protected Person person;
		protected String asString;
		
		public PersonListItem(Person person) {
			this.person = person;
			givenName = person.getGivenName();
			int bYear = person.getBirthYear();
			int dYear = person.getDeathYear();
			asString = givenName + " (" + ((bYear == 0) ? "?" : ""+bYear) + " - " +
				((dYear == 0) ? "?" : ""+dYear) + ")";
		}
		
		public Person getPerson() {
			return person;
		}
		
		public String toString() {
			return asString;
		}

		public int compareTo(Object arg0) {
			PersonListItem that = (PersonListItem)arg0;
			return this.givenName.compareTo(that.givenName);
		}
	}
}
