package com.obliquity.genealogy;

public class DatePeriod extends Date {
	public static final int FROM = 1;
	public static final int TO = 2;
	public static final int FROM_TO = 3;
	
	protected int flags;
	protected Date date2 = null;
	
	public DatePeriod(Date date1, Date date2, int flags) {
		this.year = date1.year;
		this.month = date1.month;
		this.day = date1.day;
		this.JD = date1.JD;
		this.modifier = date1.modifier;
		this.oldStyle = date1.oldStyle;
		
		this.date2 = date2;
		
		this.flags = flags;
	}
	
	public DatePeriod(Date date1, int flags) {
		this(date1, null, flags);
	}
	
	public DatePeriod(Date date1, Date date2) {
		this(date1, date2, FROM_TO);
	}

	public int getFlags() {
		return flags;
	}
	
	public Date getDate2() {
		return date2;
	}
	
	public String toString() {
		switch (flags) {
		case FROM:
		return "FROM " + super.toString();
		
		case TO:
			return "TO " + super.toString();
			
		case FROM_TO:
			return "FROM " + super.toString() + " TO " + date2.toString();
			
		default:
			return "DatePeriod[UNKNOWN " + super.toString() + "]";
		}
	}
}
