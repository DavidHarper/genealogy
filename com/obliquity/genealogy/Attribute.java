package com.obliquity.genealogy;

public class Attribute {
	public static final int UNKNOWN = 0;

	public static final int CASTE = 1;

	public static final int DESCRIPTION = 2;

	public static final int EDUCATION = 3;

	public static final int ID_NUMBER = 4;

	public static final int NATIONALITY = 5;

	public static final int NUMBER_OF_CHILDREN = 6;

	public static final int NUMBER_OF_MARRIAGES = 7;

	public static final int OCCUPATION = 8;

	public static final int PROPERTY = 9;

	public static final int RELIGION = 10;

	public static final int RESIDENCE = 11;

	public static final int SOCIAL_SECURITY_NUMBER = 12;

	public static final int TITLE = 13;

	protected int type = UNKNOWN;

	protected String typeName;

	protected String value;
	
	protected Event event;

	public Attribute(int type, String value) {
		this.type = type;
		this.value = value;
	}

	public Attribute(String typeName, String value) {
		this.typeName = typeName;
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public String getTypeName() {
		switch (type) {
		case CASTE:
			return "Caste";
		case DESCRIPTION:
			return "Description";
		case EDUCATION:
			return "Education";
		case ID_NUMBER:
			return "ID number";
		case NATIONALITY:
			return "Nationality";
		case NUMBER_OF_CHILDREN:
			return "Number of children";
		case NUMBER_OF_MARRIAGES:
			return "Number of marriages";
		case OCCUPATION:
			return "Occupation";
		case PROPERTY:
			return "Property";
		case RELIGION:
			return "Religion";
		case RESIDENCE:
			return "Residence";
		case SOCIAL_SECURITY_NUMBER:
			return "Social Security Number";
		case TITLE:
			return "Title";
		default:
			return typeName;
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
}