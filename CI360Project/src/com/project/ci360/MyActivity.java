package com.project.ci360;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends Activity implements OnClickListener {

	Button btnMonday;
	Button btnTuesday;
	Button btnWednesday;
	Button btnThursday;
	Button btnFriday;
	Button btnSaturday;
	Button btnSunday;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		
		btnMonday = (Button)findViewById(R.id.btnMonday);
		btnTuesday = (Button)findViewById(R.id.btnTuesday);
		btnWednesday = (Button)findViewById(R.id.btnWednesday);
		btnThursday = (Button)findViewById(R.id.btnThursday);
		btnFriday = (Button)findViewById(R.id.btnFriday);
		btnSaturday = (Button)findViewById(R.id.btnSaturday);
		btnSunday = (Button)findViewById(R.id.btnSunday);
		
		
		btnMonday.setOnClickListener(this);
		btnTuesday.setOnClickListener(this);
		btnWednesday.setOnClickListener(this);
		btnThursday.setOnClickListener(this);
		btnFriday.setOnClickListener(this);
		btnSaturday.setOnClickListener(this);
		btnSunday.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btnMonday:
			// do something
			Toast.makeText(MyActivity.this, "Monday", Toast.LENGTH_SHORT).show();
			Intent MondayIntent = new Intent(MyActivity.this, WorkoutActivity.class);
			MondayIntent.putExtra("day", "Monday");
			startActivity(MondayIntent);
			break;
		case R.id.btnTuesday:
			// do something
			Toast.makeText(MyActivity.this, "Tuesday", Toast.LENGTH_SHORT).show();
			Intent TuesdayIntent = new Intent(MyActivity.this, WorkoutActivity.class);
			TuesdayIntent.putExtra("day", "Tuesday");
			startActivity(TuesdayIntent);
			break;
		case R.id.btnWednesday:
			// do something
			Toast.makeText(MyActivity.this, "Wednesday", Toast.LENGTH_SHORT).show();
			Intent WednesdayIntent = new Intent(MyActivity.this, WorkoutActivity.class);
			WednesdayIntent.putExtra("day", "Wednesday");
			startActivity(WednesdayIntent);
			break;
		case R.id.btnThursday:
			// do something
			Toast.makeText(MyActivity.this, "Thursday", Toast.LENGTH_SHORT).show();
			Intent ThursdayIntent = new Intent(MyActivity.this, WorkoutActivity.class);
			ThursdayIntent.putExtra("day", "Thursday");
			startActivity(ThursdayIntent);
			break;
		case R.id.btnFriday:
			// do something
			Toast.makeText(MyActivity.this, "Friday", Toast.LENGTH_SHORT).show();
			Intent FridayIntent = new Intent(MyActivity.this, WorkoutActivity.class);
			FridayIntent.putExtra("day", "Friday");
			startActivity(FridayIntent);
			break;
		case R.id.btnSaturday:
			// do something
			Toast.makeText(MyActivity.this, "Saturday", Toast.LENGTH_SHORT).show();
			Intent SaturdayIntent = new Intent(MyActivity.this, WorkoutActivity.class);
			SaturdayIntent.putExtra("day", "Saturday");
			startActivity(SaturdayIntent);
			break;
		case R.id.btnSunday:
			// do something
			Toast.makeText(MyActivity.this, "Sunday", Toast.LENGTH_SHORT).show();
			Intent SundayIntent = new Intent(MyActivity.this, WorkoutActivity.class);
			SundayIntent.putExtra("day", "Sunday");
			startActivity(SundayIntent);
			break;	
			
		}
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my, menu);
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



	
}
