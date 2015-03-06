package com.project.ci360;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainFragment extends Fragment {
	ArrayList<String> userList = new ArrayList<String>();  
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	@Override
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	    
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_main, container, false);
	    LoginButton btnlogin = (LoginButton) view.findViewById(R.id.btnLogin);
	    btnlogin.setFragment(this);

	    return view;
	}
	
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		 
	    if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	        
	        
	     /*   Request.newMeRequest(session, new Request.GraphUserCallback() {  
	        	StringBuilder userInfo = new StringBuilder("");
	        	
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						userInfo.append(String.format("%s\n\n",user.getId()));
						
						
						Toast.makeText(getActivity(), userInfo, Toast.LENGTH_LONG).show();;
	                    if(userExist(user)){
	                    	Intent intent = new Intent(getActivity(), MainMenu.class);
	            	        startActivity(intent);
	                    }else
	                    {
	                    	addUser(user);
	                    	Toast.makeText(getActivity(), "add user", Toast.LENGTH_LONG).show();
	                    	Intent intent = new Intent(getActivity(), MainMenu.class);
	            	        startActivity(intent);
	                    }
	                    
	                }
					
				}
	        }).executeAsync();
	       */ 
	        Toast.makeText(getActivity(), "logged in", Toast.LENGTH_SHORT).show();
	        
        	Intent intent = new Intent(getActivity(), MainMenu.class);
	        startActivity(intent);
	       
	        
	        
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	    }
	}
	
	protected void addUser(GraphUser user) {
		// TODO Auto-generated method stub
		StringBuilder userInfo = new StringBuilder("");
		userInfo.append(String.format("%s\n\n",user.getId()));
		String testid = userInfo.toString();
		 DBAdapter db = new DBAdapter(getActivity());
		    db.open();
		   long id = db.insertUser(testid);
		   db.close();
		
		
	}

	protected boolean userExist(GraphUser user) {
		// TODO Auto-generated method stub
		//user.getUsername();
		DBAdapter db = new DBAdapter(getActivity());
		
		
		
		StringBuilder userInfo = new StringBuilder("");
		userInfo.append(String.format("%s\n\n",user.getId()));
		String testid = userInfo.toString();
		Toast.makeText(getActivity(), testid, Toast.LENGTH_LONG).show();
		
		Cursor c = db.getUserRecords();
	    if(c.moveToFirst())
	    {
	    	do{
	    		userList.add(c.getString(0));
	    	}while (c.moveToNext());
	    }
	    db.close();
		
		for(int i = 0;i<=userList.size(); i++)
	    if(testid != userList.get(i).toString() ){
	    	return false;
			
		}
		
		
		return true;
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onResume() {
	    super.onResume();
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }

	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	
}
