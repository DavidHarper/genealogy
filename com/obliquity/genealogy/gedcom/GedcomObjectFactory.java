package com.obliquity.genealogy.gedcom;

import java.io.IOException;
import java.util.HashMap;
import com.obliquity.genealogy.Core;
import com.obliquity.genealogy.PropertyException;

public abstract class GedcomObjectFactory {
	protected GedcomObjectFactory parent;

	protected HashMap xrefTable = new HashMap();
	
	protected boolean debugging = false;

	public GedcomObjectFactory(GedcomObjectFactory parent) {
		this.parent = parent;
	}
	
	public void setDebugging(boolean debugging) {
		this.debugging = debugging;
	}
	
	public boolean isDebugging() {
		return debugging || (parent != null && parent.isDebugging());
	}

	public Core processGedcomRecord(GedcomRecord rootRecord, GedcomReader reader)
			throws IOException, GedcomException, PropertyException {
		int rootLevel = rootRecord.getLevel();

		if (debugging)
			report("### Processing root record", rootRecord, reader);
		
		Core root = createRootObject(rootRecord);
		
		if (root == null) {
			ignoreRecord(rootRecord, reader);
			return null;
		}

		for (GedcomRecord record = reader.nextRecord(); record != null; record = reader
				.nextRecord()) {
			int level = record.getLevel();

			if (level <= rootLevel) {
				reader.pushback();
				return root;
			}
			
			if (debugging)
				report("Processing record", record, reader);

			if (level == rootLevel + 1) {
				GedcomObjectFactory factory = findFactoryForTag(record.getTag());

				if (factory == null) {
					boolean handled = handleRecord(root, record, reader);
					
					if (debugging)
						System.out.println("\thandleRecord --> " + handled);
					
					if (!handled)
						ignoreRecord(record, reader);
				} else {
					Core child = factory.processGedcomRecord(record, reader);
					
					if (debugging)
						System.out.println("\tfactory --> " + child);
					
					if (child != null)
						root.add(child);
				}
			}
		}

		return null;
	}
	
	protected void report(String message, GedcomRecord record, GedcomReader reader) {
		java.io.PrintStream ps = System.out;
		
		String classname = getClass().getName();
		int lastdot = classname.lastIndexOf('.');
		classname = classname.substring(lastdot+1);
		
		ps.print(message);
		ps.print(" in class ");
		ps.print(classname);
		ps.print(" at line ");
		ps.print(reader.getLineNumber());
		ps.print(", record=");
		ps.println(record);
	}

	public Core getObjectByXref(String xref) {
		return (parent == null) ? (Core) xrefTable.get(xref) : parent
				.getObjectByXref(xref);
	}

	public void putObjectByXref(String xref, Core object) {
		if (parent == null)
			xrefTable.put(xref, object);
		else
			parent.putObjectByXref(xref, object);
	}

	public void ignoreRecord(GedcomRecord rootRecord, GedcomReader reader)
			throws IOException, GedcomException {
		int rootLevel = rootRecord.getLevel();

		for (GedcomRecord record = reader.nextRecord(); record != null; record = reader
				.nextRecord()) {
			int level = record.getLevel();

			if (level <= rootLevel) {
				reader.pushback();
				return;
			}
		}
	}

	public abstract Core createRootObject(GedcomRecord record);

	public abstract boolean handleRecord(Core root, GedcomRecord record,
			GedcomReader reader);

	public abstract GedcomObjectFactory findFactoryForTag(String tag);
}
