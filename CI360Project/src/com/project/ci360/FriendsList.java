package com.project.ci360;

import java.util.ArrayList;
import java.util.List;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsList extends Activity {
	private List<Product> myProduct = new ArrayList<Product>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_layout);
		
		populateProductList();
		populateListView();
		registerClickCallBack();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friends_list, menu);
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
	
private class MyListAdapter extends ArrayAdapter<Product>{
		
		public MyListAdapter(){
			super(FriendsList.this,R.layout.item_view,myProduct);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			View itemView = convertView;
			if(itemView == null){
				itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
			}
			
			
			//find the product
			Product CurrentPro = myProduct.get(position);
			
			//fill the view
			
			
			TextView textName = (TextView) itemView.findViewById(R.id.txtName);
			textName.setText(CurrentPro.getName());
			
			TextView textDesc = (TextView) itemView.findViewById(R.id.txtDesc);
			textDesc.setText(CurrentPro.getDescription());
			
			
			return itemView;
			
			
		}
		
		
	}
	private void populateProductList() {
		myProduct.add(new Product("DTitle","sdfdfsdf",R.drawable.domain1));
		myProduct.add(new Product("Title","this is some text",R.drawable.facebook29));
		myProduct.add(new Product("Dprod","sdfsdf",R.drawable.internet5));
		myProduct.add(new Product("Customer","sdfsdf",R.drawable.plate7));
		myProduct.add(new Product("Product2","sdfsdf",R.drawable.settings21));
		myProduct.add(new Product("Product3","sdfsdf",R.drawable.social19));
		myProduct.add(new Product("Product4","sdfsdf",R.drawable.vintage2));
		myProduct.add(new Product("Product5","sdfsdf",R.drawable.wifi33));
		
		
		
	}
	
	
	private void populateListView() {
		ArrayAdapter<Product> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.ListView);
		list.setAdapter(adapter);
	}
	
	private void registerClickCallBack() {
		ListView list = (ListView) findViewById(R.id.ListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent friendIntent = new Intent(FriendsList.this, FriendActivity.class);
				friendIntent.putExtra("Name", "hi");
				startActivity(friendIntent);
				String message = "you click on the list item";
				Toast.makeText(FriendsList.this, message, Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}
}
