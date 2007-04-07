package com.obliquity.genealogy;

public class Event extends Core {
	public static final int EVENT = 0;
	
	public static final int BIRTH = 1;
	public static final int BAPTISM = 2;
	public static final int DEATH = 3;
	public static final int BURIAL = 4;
	public static final int MARRIAGE = 5;
	
	protected int type = EVENT;
	protected String typeName; 
	
	protected Date date;
	protected Place place;
	
	public void add(Object o) throws PropertyException {
	}
	
	public int getType() {
		return type;
	}
	
	public String getName() {
		return typeName;
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
}
