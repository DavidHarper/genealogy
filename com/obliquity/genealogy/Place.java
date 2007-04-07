package com.obliquity.genealogy;

public class Place {
	protected String locality;
	protected String county;
	protected String state;
	protected String country;
	
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
		return "Place[locality=" + locality + ", county=" + county + ", state=" + state +
			", country=" + country + "]";
	}
}
