package com.example.simourapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Lessons extends Activity {

	protected ListView lessonList;
	protected Context context;
	protected List<JSONObject> lessons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_lessons);
		lessons = new ArrayList<JSONObject>();
		lessonList = (ListView) findViewById(R.id.lessonList);
		new GetAllBooks().execute(new Connector());
		context = this;
		lessonList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					Toast.makeText(context, lessons.get(position).getString("url"), Toast.LENGTH_LONG).show();
					new Downloader(context, "http://192.168.43.69:8070/Simour/"+lessons.get(position).getString("url"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	
	public void setListAdapter(JSONArray array){
		
		this.lessonList.setAdapter(new MyListAdapter(array, this, R.layout.mylistview));
		
	}

	    private class GetAllBooks extends AsyncTask<Connector,Long,JSONArray>
	    {
	        @Override
	        protected JSONArray doInBackground(Connector... params) {
	             return params[0].GetAll("http://192.168.43.69:8084/getLessons.php");
	        }

	        @Override
	        protected void onPostExecute(JSONArray jsonArray) {
	            setListAdapter(jsonArray);
	            for(int i=0; i<jsonArray.length();i++){
	            	try {
						lessons.add(jsonArray.getJSONObject(i));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
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
