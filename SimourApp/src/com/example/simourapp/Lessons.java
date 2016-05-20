package com.example.simourapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Lessons extends Activity {

	protected ListView lessonList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_lessons);
		lessonList = (ListView) findViewById(R.id.lessonList);
		new GetAllBooks().execute(new Connector());
	}

	
	public void setListAdapter(JSONArray array){
		
		this.lessonList.setAdapter(new MyListAdapter(array, this, R.layout.mylistview));
		
	}

	    private class GetAllBooks extends AsyncTask<Connector,Long,JSONArray>
	    {
	        @Override
	        protected JSONArray doInBackground(Connector... params) {

	            // it is executed on Background thread

	             return params[0].GetAllCustomers("http://centipedestudio.co.nf/getBooks.php");
	        }

	        @Override
	        protected void onPostExecute(JSONArray jsonArray) {

	            setListAdapter(jsonArray);


	        }
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lessons, menu);
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
