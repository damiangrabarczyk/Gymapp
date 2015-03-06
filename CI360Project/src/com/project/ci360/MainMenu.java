package com.project.ci360;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;


public class MainMenu extends Activity implements OnClickListener{
	
	private List<Workout> myWorkout = new ArrayList<Workout>();
	MenuDrawer mDrawer;
	Button btnFriends;
	Button btnLogout;
	Button btnMe;	
	String dID;
	String dayN;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mDrawer = MenuDrawer.attach(MainMenu.this);
	    mDrawer.setMenuView(R.layout.menu_drawer);
	    mDrawer.setContentView(R.layout.main_menu);
	    
	    DBAdapter db = new DBAdapter(this);
	    PopulateExercise popEx = new PopulateExercise(this);
	   // popEx.populate();
	    
	   try{
	    	String destPath ="/data/data" + getPackageName() + "/database/newDB";
	    	File  f = new File(destPath);
	    	if(!f.exists()){
	    		CopyDB(getBaseContext().getAssets().open("newDB"),new FileOutputStream(destPath));
	    	}
	    } catch(FileNotFoundException e){
	    	e.printStackTrace();
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
	    
	   //insert days into the database
	    //db.open();
	  // long id = db.insertUser("dg148");
	  // db.insertWeekDay("Monday");
	  // db.insertWeekDay("Tuesday");
	 // db.insertWeekDay("Wednesday");
	  // db.insertWeekDay("Thursday");
	   // db.insertWeekDay("Friday");
	    //db.insertWeekDay("Saturday");
	  //db.insertWeekDay("Sunday");
	   
	   //db.insertDailyWorkout("dg148",2 , 3);
	 //  db.insertDailyWorkout("dg148", 2 , 6);
	  // db.insertDailyWorkout("dg148", 2 , 7);
	   
	   //db.close();
	    
		db.open();
		/*
		 * Cursor c = db.getAllRecords(); if(c.moveToFirst()) { do{
		 * myWorkout.add(new Workout(c.getString(0),c.getString(1))); }while
		 * (c.moveToNext()); }
		 */

		/*
		 * Cursor c = db.getAllRecords(); if(c.moveToFirst()) { do{
		 * myWorkout.add(new Workout(c.getString(0),c.getString(1))); }while
		 * (c.moveToNext()); }
		 */
		String dayNames[] = new DateFormatSymbols().getWeekdays();
		Calendar date2 = Calendar.getInstance();
		dayN = dayNames[date2.get(Calendar.DAY_OF_WEEK)].toString();

		db.open();

		Cursor d = db.getDayID(dayN);
		if (d.moveToFirst()) {
			do {
				if (dayN.equals(d.getString(1))) {
					dID = d.getString(0);
					Toast.makeText(MainMenu.this, dayN + "id is " + dID,
							Toast.LENGTH_SHORT).show();
				}

			} while (d.moveToNext());
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
								myWorkout.add(new Workout(exid, exName));
							}
						}while (e.moveToNext());
					}
					
				}
			} while (c.moveToNext());
		}
		db.close();
		    
		    
		    populateListView();
	    
	    btnFriends = (Button)findViewById(R.id.btnFriends);
	    btnLogout = (Button)findViewById(R.id.btnLogout);
	    btnMe = (Button)findViewById(R.id.btnMe);
	    btnFriends.setOnClickListener(this);
	    btnLogout.setOnClickListener(this);
	    btnMe.setOnClickListener(this);
	    
	    
	    //db.open();
	    //Cursor c1 = db.getUserRecords();
	    
	    //DisplayRecord(c);
	   // db.close();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btnFriends){
      		Toast.makeText(MainMenu.this, "Loading Friends", Toast.LENGTH_SHORT).show();
      		Intent friendsIntent = new Intent(MainMenu.this, FriendsList.class);
	        startActivity(friendsIntent);
 		
      		mDrawer.closeMenu(true);
      		
		}else if(v.getId() == R.id.btnLogout){
			
			if(Session.getActiveSession()!=null){
				Session.getActiveSession().closeAndClearTokenInformation();
					Intent MainActivityIntenet = new Intent(MainMenu.this, MainActivity.class);
					startActivity(MainActivityIntenet);
				
			}
			Session.setActiveSession(null);
		}else if(v.getId() == R.id.btnMe){
			
			Toast.makeText(MainMenu.this, "loading my workout.......", Toast.LENGTH_SHORT).show();
			Intent myWorkoutIntent = new Intent(MainMenu.this, MyActivity.class);
	        startActivity(myWorkoutIntent);
			
		}
	}	
	
	public void CopyDB(InputStream inputStream, OutputStream outputStream) 
		    throws IOException {
		        //---copy 1K bytes at a time---
		        byte[] buffer = new byte[1024];
		        int length;
		        while ((length = inputStream.read(buffer)) > 0) {
		            outputStream.write(buffer, 0, length);
		        }
		        inputStream.close();
		        outputStream.close();
		    }
	
	public void DisplayRecord(Cursor c)
    {
        Toast.makeText(this, 
                "User: " + c.getString(0),
                Toast.LENGTH_SHORT).show();        
    } 
	
	private void populateListView() {
		ArrayAdapter<Workout> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.mylist);
		list.setAdapter(adapter);
	}
	
private class MyListAdapter extends ArrayAdapter<Workout>{
		
		public MyListAdapter(){
			super(MainMenu.this,R.layout.myworkout_list,myWorkout);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			View itemView = convertView;
			if(itemView == null){
				itemView = getLayoutInflater().inflate(R.layout.myworkout_list,parent,false);
			}
			
			
			//find the product
			Workout CurrentPro = myWorkout.get(position);
			
			
			
			//fill the view
			
			
			TextView TextexID = (TextView) itemView.findViewById(R.id.txtId);
			TextexID.setText(CurrentPro.getExerciseID());
			
			TextView textDesc = (TextView) itemView.findViewById(R.id.txtTitle);
			textDesc.setText(CurrentPro.getDesc());
			
			
			return itemView;
			
			
		}
	
}
}
