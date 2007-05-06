package com.obliquity.genealogy;

public class Place extends Core {
	protected String locality;
	protected String county;
	protected String state;
	protected String country;
	
	protected String placeName;
	
	public Place(String placeName) {
		this.placeName = placeName;
	}
	
	public Place(String locality, String county, String state, String country) {
		this.locality = locality;
		this.county = county;
		this.state = state;
		this.country = country;
	}
	
	public String getLocality() {
		return locality;
	}
	
	public String getCounty() {
		return county;
	}
	
	public String getState() {
		return state;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String toString() {
		if (placeName != null)
			return placeName;
		else
			return locality + ", " + county + ", " + state + ", " + country;
	}

	public void add(Object o) throws PropertyException {
	}
}
