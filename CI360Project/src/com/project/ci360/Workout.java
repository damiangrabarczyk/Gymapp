package com.project.ci360;

public class Workout {
	private String exID;
	private String exDescription;
	
	public Workout(String id,String description){
		super();
		this.exID = id;
		this.exDescription = description;
	}

	public String getExerciseID() {
		return exID;
	}

	public String getDesc() {
		return exDescription;
	}
	
	
	

}
