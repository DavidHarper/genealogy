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

package com.obliquity.genealogy;

public class Place extends Core {
	protected String locality;
	protected String county;
	protected String state;
	protected String country;
	
	protected String placeName;
	
	public Place(String placeName) {
		this.placeName = placeName;
	}
	
	public Place(String locality, String county, String state, String country) {
		this.locality = locality;
		this.county = county;
		this.state = state;
		this.country = country;
	}
	
	public String getLocality() {
		return locality;
	}
	
	public String getCounty() {
		return county;
	}
	
	public String getState() {
		return state;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String toString() {
		if (placeName != null)
			return placeName;
		else
			return locality + ", " + county + ", " + state + ", " + country;
	}

	public void add(Object o) throws PropertyException {
	}
}
