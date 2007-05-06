package com.obliquity.genealogy;

public class DateRange extends Date {
	public static final int BEFORE = 1;
	public static final int AFTER = 2;
	public static final int BETWEEN = 3;
	
	protected int flags;
	protected Date date2 = null;
	
	public DateRange(Date date1, Date date2, int flags) {
		this.year = date1.year;
		this.month = date1.month;
		this.day = date1.day;
		this.JD = date1.JD;
		this.modifier = date1.modifier;
		this.oldStyle = date1.oldStyle;
		
		this.date2 = date2;
		
		this.flags = flags;
	}
	
	public DateRange(Date date1, int flags) {
		this(date1, null, flags);
	}
	
	public DateRange(Date date1, Date date2) {
		this(date1, date2, BETWEEN);
	}

	public int getFlags() {
		return flags;
	}

	public Date getDate2() {
		return date2;
	}
	
	public String toString() {
		switch (flags) {
		case BEFORE:
		return "DateRange[BEFORE " + super.toString() + "]";
		
		case AFTER:
			return "DateRange[AFTER " + super.toString() + "]";
			
		case BETWEEN:
			return "DateRange[BETWEEN " + super.toString() + " AND " + date2.toString() + "]";
			
		default:
			return "DateRange[UNKNOWN " + super.toString() + "]";
		}
	}

}
