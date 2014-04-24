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

public class Name extends Core {
	protected String familyName;
	protected String givenName;
	protected String nickname;
	protected String namePrefix;
	protected String nameSuffix;
	
	public Name(String givenName, String familyName) {
		this.givenName = givenName;
		this.familyName = familyName;
	}
	
	public String getFullName() {
		return givenName + " " + familyName;
	}
	
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public String getGivenName() {
		return givenName;
	}
	
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	public String getFamilyName() {
		return familyName;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	
	public String getNamePrefix() {
		return namePrefix;
	}
	
	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
	
	public String getNameSuffix() {
		return nameSuffix;
	}
	
	public String toString() {
		if (givenName == null && familyName == null)
			return "[Name unknown]";
		else
			return (givenName == null ? "[Given name unknown]" : givenName + " ") + 
				(familyName == null ? "[Family name unknown]" : familyName);
	}

	public void add(Object o) throws PropertyException {
	}
}
