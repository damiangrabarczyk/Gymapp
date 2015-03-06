package com.project.ci360;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WorkoutActivity extends Activity implements OnClickListener {

	private List<CurrentWorkout> myCurrentWorkout = new ArrayList<CurrentWorkout>();
	Button btnEdit;
	String dayN;
	String userid;
	String exid;
	String dayid;
	String dID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		TextView tv = (TextView)findViewById(R.id.txtTitle);
		
		
		//day of the work out from previous activity
		dayN = getIntent().getExtras().getString("day");		
		tv.setText(dayN);
		
		//button click
		btnEdit = (Button)findViewById(R.id.btnEdit);
		btnEdit.setOnClickListener(this);
		
		
		
		
		
		//database connection	
		 DBAdapter db = new DBAdapter(this);
		 db.open();
		 
		 Cursor d = db.getDayID(dayN);
		 if(d.moveToFirst())
		    {
		    	do{
		    		     if(dayN.equals(d.getString(1))){
		    		        dID = d.getString(0);
		    		        Toast.makeText(WorkoutActivity.this, dayN + "id is " + dID, Toast.LENGTH_SHORT).show();
		    		     }
		    		
		    		
		    	}while (d.moveToNext());
		    }
		 db.close();
		 db.open();
		 Cursor c = db.getCurrentWorkout("dg148", dID);
			Cursor e = db.getExercise();
			if (c.moveToFirst()) {
				do {

					String exid = c.getString(0);
					String userid = c.getString(1);
					String dayid = c.getString(2);

					if (userid.equals("dg148") && dayid.equals(dID)) {
						
						if (e.moveToFirst()) {
							do {
								String ex_id = e.getString(0);
								String exName = e.getString(1);
								
								if(ex_id.equals(exid)){//if exercises exsist get name
									//myWorkout.add(new Workout(exid, exName));
									myCurrentWorkout.add(new CurrentWorkout(exid,exName,dayid));
								}
							}while (e.moveToNext());
						}
						
					}
				} while (c.moveToNext());
			}
		 
			/*Cursor c = db.getCurrentWorkout("dg148",dID);
		    if(c.moveToFirst())
		    {
		    	do{
		    		
		    		
		    		exid = c.getString(0);
		    		 userid = c.getString(1);
		    		 dayid = c.getString(2);	    		
		    		
		    		 if(userid.equals("dg148") && dayid.equals(dID)){
		    		 myCurrentWorkout.add(new CurrentWorkout(exid,userid,dayid));
		    		 }
		    	}while (c.moveToNext());
		    }*/
		    db.close();
		    
		 //getworkout for the given day and user id - taken from previous actvities
		    
		    
		    
		//method call to populate the listview    
		populateListView();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnEdit){
			Intent EditIntent = new Intent(WorkoutActivity.this, EditWorkoutActivity.class);
			EditIntent.putExtra("day", dayN);
			EditIntent.putExtra("dayID", dID);
	        startActivity(EditIntent);
		}
		
	}
	
	public String DisplayRecord(Cursor c)
    {
        String item = "workoutdID: " + c.getString(0) + "\n" + "title: " + c.getString(1);
        return item;
    }
	
	
	
	private void populateListView() {
		ArrayAdapter<CurrentWorkout> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.ListWorkout);
		list.setAdapter(adapter);
	}
	
private class MyListAdapter extends ArrayAdapter<CurrentWorkout>{
		
		public MyListAdapter(){
			super(WorkoutActivity.this,R.layout.myworkout_list,myCurrentWorkout);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			View itemView = convertView;
			if(itemView == null){
				itemView = getLayoutInflater().inflate(R.layout.myworkout_list,parent,false);
			}
			
			
			//find the product
			CurrentWorkout CurrentWorkout = myCurrentWorkout.get(position);
			
			
			
			//fill the view
			
			
			TextView textName = (TextView) itemView.findViewById(R.id.txtId);
			textName.setText(CurrentWorkout.getExID());
			
			TextView textDesc = (TextView) itemView.findViewById(R.id.txtTitle);
			textDesc.setText(CurrentWorkout.getUserID().toString());
			
			
			return itemView;
			
			
		}
	
}


}
