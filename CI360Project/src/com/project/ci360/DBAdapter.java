package com.project.ci360;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter  {
	public static final String KEY_EXID = "exerciseID";
	public static final String KEY_WNAME = "title";
	public static final String KEY_UID = "userID";
	public static final String KEY_DAYNAME = "dayname";
	public static final String KEY_DAYID = "dayID";
	private static final String TAG = "DBAdapter";
	private static final String TAGTEST = "test";
	
	private static final String DATABASE_NAME = "newDB";
	private static final String DATABASE_EXERCISE = "exercise";
	private static final String DATABASE_USER = "user";
	private static final String DATABASE_WEEK = "weekdays";
	private static final String DATABASE_DAILY = "dayilyworkout";
	private static final int DATABASE_VERSION = 2;
	
	private static final String CREATE_EXERCISE = "create table if not exists Exercise (exerciseID integer primary key autoincrement, " + "title VARCHAR not null);";
	private static final String CREATE_USER = "create table if not exists user (userID VARCHAR primary key not null);";
	private static final String CREATE_WEEK = "create table if not exists weekdays (dayID integer primary key autoincrement, " + "dayname VARCHAR not null);";
	private static final String CREATE_DAILY = "create table if not exists dayilyworkout (exerciseID integer," + "userID VARCHAR," + "dayID integer);";
	
	private final Context context;
	
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	
	public DBAdapter(Context ctx){
		
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
		
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME,null,DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try{
				db.execSQL(CREATE_EXERCISE);
				db.execSQL(CREATE_USER);
				db.execSQL(CREATE_WEEK);
				db.execSQL(CREATE_DAILY);
				
			}catch(SQLException e){
				e.printStackTrace();
			}
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + CREATE_EXERCISE);
	        db.execSQL("DROP TABLE IF EXISTS " + CREATE_USER);
	        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WEEK);
	        db.execSQL("DROP TABLE IF EXISTS " + CREATE_DAILY);
	 
	        // create new tables
	        onCreate(db);
		}
	}
	
	
	//open the database
	public DBAdapter open(){
		db = DBHelper.getWritableDatabase();
		return this;
		
	}
	
	//close the database
	public void close(){
		DBHelper.close();
	}
	
	//insert record into the database
	public long insertExercise(String title){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_WNAME, title);
		return db.insert(DATABASE_EXERCISE, null, initialValues);
		
	}
	//insert user
	public long insertUser(String uID){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_UID, uID);
		Log.i(TAGTEST, uID);
		return db.insert(DATABASE_USER, null, initialValues);
	}
	//insert user
	public long insertWeekDay( String dayName){
		ContentValues initialValues = new ContentValues();
			
		initialValues.put(KEY_DAYNAME, dayName);
		Log.i(TAGTEST, dayName);
		return db.insert(DATABASE_WEEK, null, initialValues);
	}
	//insert dailyworkout
	public long insertDailyWorkout(String userID, int dayID, int exID){
		ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_UID, userID);
			initialValues.put(KEY_DAYID, dayID);
			initialValues.put(KEY_EXID, exID);
		
		
		
		//Log.i(TAGTEST, dayName);
		return db.insert(DATABASE_DAILY, null, initialValues);
	}	
	
	//get all records
	public Cursor getCurrentWorkout(String useID, String dayID){
		return db.query(DATABASE_DAILY, new String []{KEY_EXID,KEY_UID,KEY_DAYID}, null,null,null,null,null);
		
	}
	
	//get all records
		public Cursor getAllRecords(){
			return db.query(DATABASE_EXERCISE, new String []{KEY_EXID,KEY_WNAME}, null,null,null,null,null);
			
		}
	//get users
	public Cursor getUserRecords(){
		return db.query(DATABASE_USER, new String []{KEY_UID}, null,null,null,null,null);
	}

	public Cursor getDayID(String dayN) {
		return db.query(DATABASE_WEEK, new String []{KEY_DAYID,KEY_DAYNAME}, null ,null,null,null,null);
		
	}

	//get exercise 
	public Cursor getExercise() {
		// TODO Auto-generated method stub
		return db.query(DATABASE_EXERCISE, new String []{KEY_EXID,KEY_WNAME}, null,null,null,null,null);
	}
	
	
	//delete record
	public boolean deleteRecord(long rowId){
		return db.delete(DATABASE_EXERCISE,KEY_EXID + "=" + rowId, null) > 0;
	}
	//delete exercise from workout	
	public Cursor deleteDailyExercise(String userID, int dayID, int exID){
		//return db.delete(DATABASE_DAILY,KEY_EXID + "=" + exID + " AND " + KEY_UID + "=" + userID +  " AND " + KEY_DAYID + "="  +dayID, null)>0;
		return db.rawQuery("delete from dayilyworkout where " + KEY_EXID + " = ? And " + KEY_DAYID + " = ? and " + KEY_UID + " =  " + userID  , new String[] {String.valueOf(exID),String.valueOf(dayID) });
	}
		
	
	

}
