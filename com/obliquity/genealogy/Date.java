package com.obliquity.genealogy;

public class Date {
	public static final int EXACT = 0;
	public static final int BEFORE = 1;
	public static final int AFTER = 2;
	public static final int ABOUT = 4;
	public static final int ESTIMATED = 8;
	public static final int BETWEEN = 16;
	public static final int OLD_STYLE = 32;
	
	protected int year;
	protected int month;
	protected int day;
	
	protected int modifier;
	
	protected Date laterDate = null;
	
	public Date(int year, int month, int day, int modifier) {
		this.year = year;
		this.month = month;
		this.day = day;
		
		this.modifier = modifier;
	}
	
	public Date(int year, int month, int day) {
		this(year, month, day, EXACT);
	}
	
	public Date(int year, int month) {
		this(year, month, 0, EXACT);
	}
	
	public Date(int year) {
		this(year, 0, 0, EXACT);
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getModifier() {
		return modifier;
	}
	
	public boolean isExact() {
		return modifier == EXACT;
	}
	
	public boolean isBefore() {
		return (modifier & BEFORE) != 0;
	}
	
	public boolean isAfter() {
		return (modifier & AFTER) != 0;
	}
	
	public boolean isAbout() {
		return (modifier & ABOUT) != 0;
	}
	
	public boolean isEstimated() {
		return (modifier & ESTIMATED) != 0;
	}
	
	public boolean isBetween() {
		return (modifier & BETWEEN) != 0;
	}
	
	public boolean isOldStyle() {
		return (modifier & OLD_STYLE) != 0;
	}
	
	public Date getLaterDate() {
		return laterDate;
	}
	
	public void setLaterDate(Date laterDate) {
		this.laterDate = laterDate;
		
		if (laterDate == null)
			modifier ^= BETWEEN;
		else
			modifier |= BETWEEN;
	}
	
	public String toString() {
		return "Date[year=" + year + ", month=" + month + ", day=" + day + ", modifier=" + modifier +
			(isBetween() ? ", laterDate=" + laterDate : "") + "]";
	}
}
