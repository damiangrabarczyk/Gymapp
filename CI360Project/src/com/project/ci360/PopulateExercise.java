package com.project.ci360;

import android.content.Context;

public class PopulateExercise {
	
	public DBAdapter db;
	public PopulateExercise(Context c){
		db  = new DBAdapter(c);
	}
	
	public void populate(){
		 db.open();
		 db.insertExercise("Bench Press");
		 db.insertExercise("Dumbell Press");
		 db.insertExercise("Flys ");
		 db.insertExercise("Sit ups");
		 db.insertExercise("Push ups");
		 db.insertExercise("Running");
		 db.insertExercise("Crosstrainer");
		 db.insertExercise("Bendover rows");
		 
		 
		 db.close();
	}

}
