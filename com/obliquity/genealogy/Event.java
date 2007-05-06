package com.obliquity.genealogy;

public class Event extends Core {
	public static final int EVENT = 0;

	public static final int BIRTH = 1;

	public static final int CHRISTENING = 2;

	public static final int DEATH = 3;

	public static final int BURIAL = 4;

	public static final int CREMATION = 5;

	public static final int ADOPTION = 6;

	public static final int BAPTISM = 7;

	public static final int BAR_MITZVAH = 8;

	public static final int BAS_MITZVAH = 9;

	public static final int BLESSING = 10;

	public static final int ADULT_CHRISTENING = 11;

	public static final int CONFIRMATION = 12;

	public static final int FIRST_COMMUNION = 13;

	public static final int ORDINATION = 14;

	public static final int NATURALISATION = 15;

	public static final int NATURALIZATION = NATURALISATION;

	public static final int EMIGRATION = 16;

	public static final int IMMIGRATION = 17;

	public static final int CENSUS = 18;

	public static final int PROBATE = 19;

	public static final int WILL = 20;

	public static final int GRADUATION = 21;

	public static final int RETIREMENT = 22;

	public static final int LDS_BAPTISM = 23;

	public static final int LDS_CONFIRMATION = 24;

	public static final int LDS_ENDOWMENT = 25;

	public static final int LDS_SEALING_CHILD = 26;

	public static final int ANNULMENT = 27;

	public static final int DIVORCE = 28;

	public static final int DIVORCE_FILED = 29;

	public static final int ENGAGEMENT = 30;

	public static final int MARRIAGE = 31;

	public static final int MARRIAGE_BANNS = 32;

	public static final int MARRIAGE_CONTRACT = 33;

	public static final int MARRIAGE_LICENCE = 34;

	public static final int MARRIAGE_LICENSE = MARRIAGE_LICENCE;

	public static final int MARRIAGE_SETTLEMENT = 35;

	public static final int LDS_SEALING_SPOUSE = 36;

	protected int type = EVENT;

	protected String typeName;

	protected Date date;

	protected Place place;

	public Event() {
		this(EVENT);
	}

	public Event(int type) {
		this.type = type;
	}

	public void add(Object o) throws PropertyException {
		if (o instanceof Date)
			setDate((Date)o);
		else if (o instanceof Place)
			setPlace((Place)o);
		else
			throw new PropertyException("Unable to add object of type "
					+ o.getClass().getName() + " to an Event");
	
	}

	public int getType() {
		return type;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		switch (type) {
		case BIRTH:
			return "Birth";
		case CHRISTENING:
			return "Christening";
		case DEATH:
			return "Death";
		case BURIAL:
			return "Burial";
		case CREMATION:
			return "Cremation";
		case ADOPTION:
			return "Adoption";
		case BAPTISM:
			return "Baptism";
		case BAR_MITZVAH:
			return "Bar Mitzvah";
		case BAS_MITZVAH:
			return "Bas Mitzvah";
		case BLESSING:
			return "Blessing";
		case ADULT_CHRISTENING:
			return "Adult Christening";
		case CONFIRMATION:
			return "Confirmation";
		case FIRST_COMMUNION:
			return "First Communion";
		case ORDINATION:
			return "Ordination";
		case NATURALISATION:
			return "Naturalisation";
		case EMIGRATION:
			return "Emigration";
		case IMMIGRATION:
			return "Immigration";
		case CENSUS:
			return "Census";
		case PROBATE:
			return "Probate";
		case WILL:
			return "Will";
		case GRADUATION:
			return "Graduation";
		case RETIREMENT:
			return "Retirement";
		case LDS_BAPTISM:
			return "Baptism (LDS)";
		case LDS_CONFIRMATION:
			return "Confirmation (LDS)";
		case LDS_ENDOWMENT:
			return "Endowment (LDS)";
		case LDS_SEALING_CHILD:
			return "Sealing of child (LDS)";

		case ANNULMENT:
			return "Annulment";
		case DIVORCE:
			return "Divorce";
		case DIVORCE_FILED:
			return "Divorce filed";
		case ENGAGEMENT:
			return "Engagement";
		case MARRIAGE:
			return "Marriage";
		case MARRIAGE_BANNS:
			return "Marriage Banns";
		case MARRIAGE_CONTRACT:
			return "Marriage Contract";
		case MARRIAGE_LICENCE:
			return "Marriage Licence";
		case MARRIAGE_SETTLEMENT:
			return "Marriage Settlement";
		case LDS_SEALING_SPOUSE:
			return "Sealing of spouse (LDS)";

		default:
			return typeName;
		}
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Place getPlace() {
		return place;
	}
	
	public String getDateAndPlace() {
		return ((date == null) ? "Date unknown" : date.toString()) +
			((place == null) ? ", place unknown" : ", " + place.toString());
	}
	
	public String toString() {
		return "Event[code=" + type + ", type=\"" + getTypeName() + "\"" +
			((date == null) ? "" : ", date=" + date) +
			((place == null) ? "" : ", place=" + place) + "]";
	}
}
