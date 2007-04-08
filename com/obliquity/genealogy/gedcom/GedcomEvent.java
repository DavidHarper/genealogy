package com.obliquity.genealogy.gedcom;

import java.util.HashMap;

import com.obliquity.genealogy.Event;

public class GedcomEvent {
	protected static HashMap events = new HashMap();

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

	protected static void registerEvent(String tag, String description, int type) {
		GedcomEvent event = new GedcomEvent(tag, description, type);

		events.put(tag, event);
	}
	
	static {
		registerEvent("BIRT", "Birth", Event.BIRTH);
		registerEvent("CHR", "Christening", Event.CHRISTENING);
		registerEvent("DEAT", "Death", Event.DEATH);
		registerEvent("BURI", "Burial", Event.BURIAL);
		registerEvent("CREM", "Cremation", Event.CREMATION);
		registerEvent("ADOP", "Adoption", Event.ADOPTION);
		registerEvent("BAPM", "Baptism", Event.BAPTISM);
		registerEvent("BARM", "Bar mitzvah", Event.BAR_MITZVAH);
		registerEvent("BASM", "Bas mitzvah", Event.BAS_MITZVAH);
		registerEvent("BLES", "Blessing", Event.BLESSING);
		registerEvent("CHRA", "Adult christening", Event.ADULT_CHRISTENING);
		registerEvent("CONF", "Confirmation", Event.CONFIRMATION);
		registerEvent("FCOM", "First communion", Event.FIRST_COMMUNION);
		registerEvent("ORDN", "Ordination", Event.ORDINATION);
		registerEvent("NATU", "Naturalisation", Event.NATURALISATION);
		registerEvent("EMIG", "Emigration", Event.EMIGRATION);
		registerEvent("IMMI", "Immigration", Event.IMMIGRATION);
		registerEvent("CENS", "Census", Event.CENSUS);
		registerEvent("PROB", "Probate", Event.PROBATE);
		registerEvent("WILL", "Will", Event.WILL);
		registerEvent("GRAD", "Graduation", Event.GRADUATION);
		registerEvent("RETI", "Retirement", Event.RETIREMENT);
		registerEvent("BAPL", "Baptism (LDS)", Event.LDS_BAPTISM);
		registerEvent("CONL", "Confirmation (LDS)", Event.LDS_CONFIRMATION);
		registerEvent("ENDL", "Endowment (LDS)", Event.LDS_ENDOWMENT);
		registerEvent("SLGC", "Sealing of child (LDS)", Event.LDS_SEALING_CHILD);

		registerEvent("ANUL", "Annulment", Event.ANNULMENT);
		registerEvent("CENS", "Census", Event.CENSUS);
		registerEvent("DIV", "Divorce", Event.DIVORCE);
		registerEvent("DIVF", "Divorce filed", Event.DIVORCE_FILED);
		registerEvent("ENGA", "Engagement", Event.ENGAGEMENT);
		registerEvent("MARR", "Marriage", Event.MARRIAGE);
		registerEvent("MARB", "Marriage banns", Event.MARRIAGE_BANNS);
		registerEvent("MARC", "Marriage contract", Event.MARRIAGE_CONTRACT);
		registerEvent("MARL", "Marriage licence", Event.MARRIAGE_LICENCE);
		registerEvent("MARS", "Marriage settlement", Event.MARRIAGE_SETTLEMENT);
		registerEvent("SLGS", "Sealing of spouse (LDS)", Event.LDS_SEALING_SPOUSE);
	}
	
	public static GedcomEvent findEvent(String tag) {
		return (GedcomEvent) events.get(tag);
	}
}
