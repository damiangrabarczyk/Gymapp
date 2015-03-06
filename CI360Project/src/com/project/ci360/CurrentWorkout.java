package com.project.ci360;

public class CurrentWorkout {
	private String userID;
	private String dayID;
	private String exID;
	
	public CurrentWorkout(String xID,String uID, String dID ){
		super();
		this.userID = uID;
		this.dayID =dID;
		this.exID = xID;
		
	}

	public String getUserID() {
		return userID;
	}

	public String getDayID() {
		return dayID;
	}

	public String getExID() {
		return exID;
	}
	

}
