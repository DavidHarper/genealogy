package com.obliquity.genealogy.gedcom;

import java.util.HashMap;

import com.obliquity.genealogy.Event;

public class GedcomEvent {
	protected static HashMap personEvents = new HashMap();

	protected static HashMap familyEvents = new HashMap();

	public static final int PERSONAL = 1;

	public static final int FAMILY = 2;

	protected String tag;

	protected String description;

	protected int type;

	public GedcomEvent(String tag, String description, int type) {
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

	protected static void registerEvent(String tag, String description,
			int type, int subject) {
		GedcomEvent event = new GedcomEvent(tag, description, type);

		switch (subject) {
		case PERSONAL:
			personEvents.put(tag, event);
			break;

		case FAMILY:
			familyEvents.put(tag, event);
			break;
		}
	}
	
	static {
		registerEvent("BIRT", "Birth", Event.BIRTH, PERSONAL);
		registerEvent("CHR", "Christening", Event.CHRISTENING, PERSONAL);
		registerEvent("DEAT", "Death", Event.DEATH, PERSONAL);
		registerEvent("BURI", "Burial", Event.BURIAL, PERSONAL);
		registerEvent("CREM", "Cremation", Event.CREMATION, PERSONAL);
		registerEvent("ADOP", "Adoption", Event.ADOPTION, PERSONAL);
		registerEvent("BAPM", "Baptism", Event.BAPTISM, PERSONAL);
		registerEvent("BARM", "Bar mitzvah", Event.BAR_MITZVAH, PERSONAL);
		registerEvent("BASM", "Bas mitzvah", Event.BAS_MITZVAH, PERSONAL);
		registerEvent("BLES", "Blessing", Event.BLESSING, PERSONAL);
		registerEvent("CHRA", "Adult christening", Event.ADULT_CHRISTENING, PERSONAL);
		registerEvent("CONF", "Confirmation", Event.CONFIRMATION, PERSONAL);
		registerEvent("FCOM", "First communion", Event.FIRST_COMMUNION, PERSONAL);
		registerEvent("ORDN", "Ordination", Event.ORDINATION, PERSONAL);
		registerEvent("NATU", "Naturalisation", Event.NATURALISATION, PERSONAL);
		registerEvent("EMIG", "Emigration", Event.EMIGRATION, PERSONAL);
		registerEvent("IMMI", "Immigration", Event.IMMIGRATION, PERSONAL);
		registerEvent("CENS", "Census", Event.CENSUS, PERSONAL);
		registerEvent("PROB", "Probate", Event.PROBATE, PERSONAL);
		registerEvent("WILL", "Will", Event.WILL, PERSONAL);
		registerEvent("GRAD", "Graduation", Event.GRADUATION, PERSONAL);
		registerEvent("RETI", "Retirement", Event.RETIREMENT, PERSONAL);
		registerEvent("BAPL", "Baptism (LDS)", Event.LDS_BAPTISM, PERSONAL);
		registerEvent("CONL", "Confirmation (LDS)", Event.LDS_CONFIRMATION, PERSONAL);
		registerEvent("ENDL", "Endowment (LDS)", Event.LDS_ENDOWMENT, PERSONAL);
		registerEvent("SLGC", "Sealing of child (LDS)", Event.LDS_SEALING_CHILD, PERSONAL);

		registerEvent("ANUL", "Annulment", Event.ANNULMENT, FAMILY);
		registerEvent("CENS", "Census", Event.CENSUS, FAMILY);
		registerEvent("DIV", "Divorce", Event.DIVORCE, FAMILY);
		registerEvent("DIVF", "Divorce filed", Event.DIVORCE_FILED, FAMILY);
		registerEvent("ENGA", "Engagement", Event.ENGAGEMENT, FAMILY);
		registerEvent("MARR", "Marriage", Event.MARRIAGE, FAMILY);
		registerEvent("MARB", "Marriage banns", Event.MARRIAGE_BANNS, FAMILY);
		registerEvent("MARC", "Marriage contract", Event.MARRIAGE_CONTRACT, FAMILY);
		registerEvent("MARL", "Marriage licence", Event.MARRIAGE_LICENCE, FAMILY);
		registerEvent("MARS", "Marriage settlement", Event.MARRIAGE_SETTLEMENT, FAMILY);
		registerEvent("SLGS", "Sealing of spouse (LDS)", Event.LDS_SEALING_SPOUSE, FAMILY);
	}
	
	public GedcomEvent findEvent(String tag, int subject) {
		switch (subject) {
		case PERSONAL:
			return (GedcomEvent) personEvents.get(tag);

		case FAMILY:
			return (GedcomEvent) familyEvents.get(tag);

		default:
			return null;
		}
	}
}
