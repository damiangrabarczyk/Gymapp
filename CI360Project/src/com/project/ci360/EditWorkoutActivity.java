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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditWorkoutActivity extends Activity {
	private List<Workout> myWorkout = new ArrayList<Workout>();
	// private List<CurrentWorkout> myCurrentWorkout = new
	// ArrayList<CurrentWorkout>();
	private String dID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_workout);

		DBAdapter db = new DBAdapter(this);

		Intent intent = getIntent();
		String day = intent.getStringExtra("day");
		dID = intent.getStringExtra("dayID");

		Toast.makeText(EditWorkoutActivity.this, day, Toast.LENGTH_SHORT)
				.show();

		db.open();
		Cursor c = db.getExercise();

		if (c.moveToFirst()) {
			do {
				String exID = c.getString(0);
				String exTitle = c.getString(1);

				myWorkout.add(new Workout(exID, exTitle));

			} while (c.moveToNext());
		}
		db.close();

		populateListView();
		onclickList();

	}

	private void onclickList() {
		// TODO Auto-generated method stub
		ListView list = (ListView) findViewById(R.id.editList);
		list.setClickable(true);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Workout CurrentPro = myWorkout.get(position);

				String toast = "you clicked on " + position + " "
						+ CurrentPro.getDesc();
				Toast.makeText(EditWorkoutActivity.this, toast,
						Toast.LENGTH_LONG).show();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_workout, menu);
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

	private void populateListView() {
		ArrayAdapter<Workout> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.editList);
		list.setAdapter(adapter);
	}

	private class MyListAdapter extends ArrayAdapter<Workout> implements
			OnClickListener {

		DBAdapter db;
		int exID = 0;

		public MyListAdapter() {
			super(EditWorkoutActivity.this, R.layout.item_view, myWorkout);
			db = new DBAdapter(EditWorkoutActivity.this);

		}

		public boolean isAdded(int id) {

			String temp = new Integer(id).toString();
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

								if (ex_id.equals(exid)) {// if exercise exist
															// get name
									if (ex_id.equals(temp)) {
										return true;
									}

								}

							} while (e.moveToNext());
						}

					}
				} while (c.moveToNext());
			}

			db.close();
			return false;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.item_view,
						parent, false);
			}

			// find the product
			Workout CurrentPro = myWorkout.get(position);

			// fill the view

			TextView textName = (TextView) itemView.findViewById(R.id.txtDesc);
			textName.setText(CurrentPro.getExerciseID());

			TextView textDesc = (TextView) itemView.findViewById(R.id.txtName);
			textDesc.setText(CurrentPro.getDesc());

			Button btnAction = (Button) itemView.findViewById(R.id.btnAction);
			btnAction.setTag(CurrentPro.getExerciseID());
			btnAction.setOnClickListener(this);

			int tempexID = Integer.parseInt(CurrentPro.getExerciseID());

			if (isAdded(tempexID)) {
				btnAction.setText("-");
			} else {
				btnAction.setText("+");
			}
			return itemView;

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(EditWorkoutActivity.this, "button testing",
					Toast.LENGTH_SHORT).show();

			Button btnAct = (Button) v;
			// Button btnAction = (Button)findViewById(R.id.btnAction);

			db.open();
			Cursor c = db.getExercise();
			if (c.moveToFirst()) {
				do {
					if (btnAct.getTag().toString().equals(c.getString(0))) {
						Toast.makeText(EditWorkoutActivity.this,
								c.getString(1), Toast.LENGTH_LONG).show();
						Toast.makeText(
								EditWorkoutActivity.this,
								"the id of the exercise is: "
										+ btnAct.getTag().toString(),
								Toast.LENGTH_LONG).show();
						exID = c.getInt(0);
					}
				} while (c.moveToNext());
			}
			db.close();
			int dayID = Integer.parseInt(dID);

			if (btnAct.getTag() == v.getTag()) {
				if (btnAct.getText().equals("+")) {

					if (isAdded(exID)) {
						Toast.makeText(
								EditWorkoutActivity.this,
								"this exercise has already been added"
										+ btnAct.getTag().toString()
										+ v.getTag().toString(),
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(
								EditWorkoutActivity.this,
								"added" + btnAct.getTag().toString()
										+ v.getTag().toString(),
								Toast.LENGTH_LONG).show();
						db.open();
						db.insertDailyWorkout("dg148", dayID, exID);
						db.close();
						btnAct.setText("-");
					}

				} else {

					Toast.makeText(
							EditWorkoutActivity.this,
							"deleted" + btnAct.getTag().toString()
									+ v.getTag().toString(), Toast.LENGTH_LONG)
							.show();
					db.open();
					Cursor delete;
					delete = db.deleteDailyExercise("dg148", dayID, exID);
					if (delete.moveToFirst()) {
						do {
							db.deleteDailyExercise("dg148", dayID, exID);

						} while (delete.moveToNext());
					}
					db.close();
					btnAct.setText("+");
				}

			}
			db.close();

			
		}
	}

}
