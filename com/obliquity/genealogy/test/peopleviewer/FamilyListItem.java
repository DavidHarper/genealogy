package com.obliquity.genealogy.test.peopleviewer;

import java.util.Vector;

class FamilyListItem implements Comparable<FamilyListItem> {
	protected String familyName;
	protected Vector familyMembers;
	
	public FamilyListItem(String familyName, Vector familyMembers) {
		this.familyName = familyName;
		this.familyMembers = familyMembers;
	}
	
	public String getFamilyName() {
		return familyName;
	}
	
	public Vector getFamilyMembers() {
		return familyMembers;
	}
	
	public String toString() {
		return familyName == null || familyName.length() == 0 ? "UNKNOWN" : familyName;
	}

	public int compareTo(FamilyListItem that) {
		return familyName.compareTo(that.familyName);
	}
}
