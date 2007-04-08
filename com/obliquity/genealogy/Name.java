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
		return "Name[givenName=" + givenName + ", familyName=" + familyName + "]";
	}

	public void add(Object o) throws PropertyException {
	}
}
