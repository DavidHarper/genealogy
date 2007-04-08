package com.obliquity.genealogy.gedcom.factory;

import com.obliquity.genealogy.*;
import com.obliquity.genealogy.gedcom.*;

public class NameFactory extends GedcomObjectFactory {
	public NameFactory(GedcomObjectFactory parent) {
		super(parent);
	}

	public Core createRootObject(GedcomRecord record) {
		return parseName(record.getContent());
	}
	
	protected Name parseName(String contents) {
		int firstSlash = contents.indexOf('/');
		int lastSlash = contents.indexOf('/', firstSlash + 1);
		String surname = null;
		String givenname = null;

		if (firstSlash >= 0 && lastSlash > firstSlash) {
			surname = contents.substring(firstSlash + 1,
					lastSlash);
			surname = surname.trim();
			if (firstSlash > 0) {
				givenname = contents.substring(0, firstSlash);
			} else {
				givenname = contents.substring(lastSlash + 1);
			}
			if (givenname != null) {
				givenname = givenname.trim();
			}
		} else {
			givenname = contents.trim();
		}

		return new Name(givenname, surname);
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		return null;
	}

	public boolean handleRecord(Core root, GedcomRecord record, GedcomReader reader) {
		return false;
	}
}
