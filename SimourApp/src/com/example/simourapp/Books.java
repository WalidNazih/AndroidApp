package com.example.simourapp;

import java.io.IOException;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Books extends Activity {

	protected ListView bookList;
	protected Context context;
	protected List<JSONObject> books;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_books);
		books = new ArrayList<JSONObject>();
		context = this;
		bookList = (ListView) findViewById(R.id.lessonList);
		new GetAllBooks().execute(new Connector());
		bookList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					Toast.makeText(context, books.get(position).getString("url"), Toast.LENGTH_LONG).show();
					new Downloader(context, "http://192.168.43.69:8070/Simour/"+books.get(position).getString("url"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void setListAdapter(JSONArray array) {

		this.bookList.setAdapter(new MyListAdapter(array, this, R.layout.booklist));

	}

	private class GetAllBooks extends AsyncTask<Connector, Long, JSONArray> {
		@Override
		protected JSONArray doInBackground(Connector... params) {
			return params[0].GetAll("http://192.168.43.69:8084/getBooks.php");
		}

		@Override
		protected void onPostExecute(JSONArray jsonArray) {

			setListAdapter(jsonArray);
			for(int i=0; i<jsonArray.length();i++){
				try {
					books.add(jsonArray.getJSONObject(i));
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
		getMenuInflater().inflate(R.menu.books, menu);
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
