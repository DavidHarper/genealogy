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

public class NoteFactory extends GedcomObjectFactory {
	protected SourceFactory sourceFactory;

	public NoteFactory(GedcomObjectFactory parent) {
		super(parent);
	}

	public Core createRootObject(GedcomRecord record) {
		String xref = record.getXref();

		if (xref == null) {
			return new Note(record.getContent());
		} else {
			Note note = (Note) getObjectByXref(xref);

			if (note == null) {
				note = new Note(record.getContent());
				putObjectByXref(xref, note);
			}

			return note;
		}
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		if (tag.equalsIgnoreCase("SOUR"))
			return sourceFactory;
		else
			return null;
	}

	public boolean handleRecord(Core root, GedcomRecord record,
			GedcomReader reader) {
		String tag = record.getTag();

		if (tag.equalsIgnoreCase("CONC")) {
			((Note) root).concatenateText(record.getContent());
			return true;
		} else if (tag.equalsIgnoreCase("CONT")) {
			((Note) root).continueText(record.getContent());
			return true;
		} else
			return false;
	}

	public void setSourceFactory(SourceFactory sourceFactory) {
		this.sourceFactory = sourceFactory;
	}

}
