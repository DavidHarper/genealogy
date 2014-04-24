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

import com.obliquity.genealogy.Core;
import com.obliquity.genealogy.Attribute;
import com.obliquity.genealogy.gedcom.*;

public class AttributeFactory extends GedcomObjectFactory {
	private DateFactory dateFactory = new DateFactory(this);	
	private PlaceFactory placeFactory = new PlaceFactory(this);

	public AttributeFactory(GedcomObjectFactory parent) {
		super(parent);
	}
	
	public Core createRootObject(GedcomRecord record) {
		String tag = record.getTag();
		String value = record.getContent();
		
		GedcomAttribute gedcomAttribute = GedcomAttribute.findAttribute(tag);
		
		if (gedcomAttribute == null)
			return new Attribute();
		else
			return new Attribute(gedcomAttribute.getType(), value);
	}

	public GedcomObjectFactory findFactoryForTag(String tag) {
		if (tag.equalsIgnoreCase("DATE"))
			return dateFactory;
		else if (tag.equalsIgnoreCase("PLAC"))
			return placeFactory;
		else
			return null;
	}

	public boolean handleRecord(Core root, GedcomRecord record, GedcomReader reader) {
		return false;
	}
}
