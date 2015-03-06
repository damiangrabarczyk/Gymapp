package com.project.ci360;

public class Day {

	
	private String dayID;
	private String dayName;
	
	public Day(String id, String dayName){

		this.dayID = id;
		this.dayName = dayName;
		
	}

	public String getDayID() {
		return dayID;
	}

	public String getDayName() {
		return dayName;
	}
	
}
