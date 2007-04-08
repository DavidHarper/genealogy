package com.obliquity.genealogy.gedcom;

import java.io.IOException;
import com.obliquity.genealogy.Core;
import com.obliquity.genealogy.PropertyException;

public abstract class GedcomObjectFactory {
	protected GedcomObjectFactory parent;
	
	public GedcomObjectFactory(GedcomObjectFactory parent) {
		this.parent = parent;
	}
	
	public Core processGedcomRecord(GedcomRecord rootRecord, GedcomReader reader)
		throws IOException, GedcomException, PropertyException {
		int rootLevel = rootRecord.getLevel();
		
		Core root = createRootObject(rootRecord);
		
		for (GedcomRecord record = reader.nextRecord(); record != null; record = reader.nextRecord()) {
			int level = record.getLevel();
			
			if (level <= rootLevel) {
				reader.pushback();
				return root;
			}
			
			if (level > rootLevel + 1)
				throw new GedcomException("Level " + level + " inconsistent with root level " + rootLevel,
						reader.getLineNumber());
			
			GedcomObjectFactory factory = findFactoryForTag(record.getTag());
			
			if (factory == null) {
				handleRecord(root, record, reader);
			} else {
				Core child = factory.processGedcomRecord(record, reader);
				root.add(child);
			}
		}
		
		return null;
	}
	
	public abstract Core createRootObject(GedcomRecord record);
	
	public abstract void handleRecord(Core root, GedcomRecord record, GedcomReader reader);
	
	public abstract GedcomObjectFactory findFactoryForTag(String tag); 
}
