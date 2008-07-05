package com.obliquity.genealogy.gedcom;

import java.util.HashMap;

import com.obliquity.genealogy.Attribute;

public class GedcomAttribute {
	protected static HashMap<String, GedcomAttribute> attributes = new HashMap<String, GedcomAttribute>();

	protected String tag;

	protected String description;

	protected int type;

	public GedcomAttribute(String tag, String description, int type) {
		this.tag = tag;
		this.description = description;
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public String getDescription() {
		return description;
	}

	public int getType() {
		return type;
	}

	protected static void registerAttribute(String tag, String description,
			int type) {
		GedcomAttribute attribute = new GedcomAttribute(tag, description, type);

		attributes.put(tag, attribute);
	}

	static {
		registerAttribute("CAST", "Caste", Attribute.CASTE);
		registerAttribute("DSCR", "Description", Attribute.DESCRIPTION);
		registerAttribute("EDUC", "Education", Attribute.EDUCATION);
		registerAttribute("IDNO", "ID Number", Attribute.ID_NUMBER);
		registerAttribute("NATI", "Nationality", Attribute.NATIONALITY);
		registerAttribute("NCHI", "Number of children",
				Attribute.NUMBER_OF_CHILDREN);
		registerAttribute("NMR", "Number of marriages",
				Attribute.NUMBER_OF_MARRIAGES);
		registerAttribute("OCCU", "Occupation", Attribute.OCCUPATION);
		registerAttribute("PROP", "Property", Attribute.PROPERTY);
		registerAttribute("RELI", "Religion", Attribute.RELIGION);
		registerAttribute("RESI", "Residence", Attribute.RESIDENCE);
		registerAttribute("SSN", "Social Security Number",
				Attribute.SOCIAL_SECURITY_NUMBER);
		registerAttribute("TITL", "Title", Attribute.TITLE);
	}

	public static GedcomAttribute findAttribute(String tag) {
		return attributes.get(tag);
	}
}
