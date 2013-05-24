/*
 * 
 */
package com.example.java1week2_4;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.lib.WebStuff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {

	// Global variables
	Context _context;
	LinearLayout _appLayout;
	String[] _detailNames = new String[5];
	TextView _resultsView;
	Boolean _connected = false;
	JSONObject _json;
	SearchForm _search;
	InfoSpinner _infoSpin;
	ShowResults _results;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_context = this;
		_appLayout = new LinearLayout(_context);
		_appLayout.setOrientation(LinearLayout.VERTICAL);

		// Title Display
		TextView titleText = new TextView(this);
		titleText.setText("Movie Info");
		titleText.setGravity(Gravity.CENTER);
		titleText.setTextSize(30);
		_appLayout.addView(titleText);

		// Search Display
		_search = new SearchForm(_context, "Enter Movie Name", "GO!");
		_appLayout.addView(_search);

		// Detect Network connection
		_connected = WebStuff.getConnectionStatus(_context);
		if (_connected) {
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));
		} else {
			//If no connection load last viewed movie details
			
			// Warning Display
			TextView warningText = new TextView(this);
			warningText.setText("No connection. Viewing last searched movie.");
			warningText.setGravity(Gravity.CENTER);
			warningText.setTextSize(30);
			_appLayout.addView(warningText);

			// Load last JSON file
			Object stored = FileStuff.readObjectFile(_context, "Movie", true);
			if (stored != null) {
				String temp = (String) stored;
				try {
					_json = new JSONObject(temp);
					Log.i("JSON", _json.toString());

				} catch (JSONException e) {
					Log.e("SAVED FILE", "CANT MAKE JSON");
				}
			}

			// Set Search Text
			EditText searchField = _search.getField();
			try {
				searchField.setText(_json.getString("title").toString());
			} catch (JSONException e) {
				Log.e("SAVED FILE", "CANT LOAD MOVIE TITLE");
			}
		}

		// Search Handler
		Button searchButton = _search.getButton();
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("CLICK HANDLER", _search.getField().getText().toString());
				// Send text field entry to URL builder and request API
				getQuote(_search.getField().getText().toString());
			}
		});

		// Info Spinner Display
		_infoSpin = new InfoSpinner(_context);
		_appLayout.addView(_infoSpin);

		// Info Spinner Handler
		Spinner iSpin = _infoSpin.getSpinner();
		iSpin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
					long id) {
				Log.i("DETAIL SELECTED", parent.getItemAtPosition(pos)
						.toString());
				//If previous view remove, and then load new requested details
				_appLayout.removeView(_results);
				_results = new ShowResults(_context, parent.getItemAtPosition(
						pos).toString(), _json);
				_results.setGravity(Gravity.CENTER);
				_appLayout.addView(_results);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// Set Content View
		setContentView(_appLayout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Build URL
	private void getQuote(String movieName) {
		String baseURL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=zns9a4f2hfm94ju3unu4axzk&q=";
		String qs;
		try {
			qs = URLEncoder.encode(movieName, "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("BAD URL", "ENCODING PROBLEM");
			qs = "";
		}
		// Send URl to Async Task
		URL finalURL;
		try {
			finalURL = new URL(baseURL + qs + "&page_limit=1");
			QuoteRequest qr = new QuoteRequest();
			qr.execute(finalURL);
		} catch (MalformedURLException e) {
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}

	// Send URL to web stuff to pull data
	private class QuoteRequest extends AsyncTask<URL, Void, String> {
		@Override
		protected String doInBackground(URL... urls) {
			String response = "";
			for (URL url : urls) {
				response = WebStuff.getURLStringResponse(url);
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				// Set data
				Log.i("JSON", result);
				JSONObject resultJSON = new JSONObject(result);
				try {
					// JSON Array is Movies. object 0 is the first result. get
					// string provides the detail Object.
					if (resultJSON.getString("total").toString().compareTo("0") == 0) {
						Toast toast = Toast.makeText(_context,
								"No movie by that name exists",
								Toast.LENGTH_LONG);
						toast.show();
					} else {
						_json = resultJSON.getJSONArray("movies")
								.getJSONObject(0);
						//Store object in external memory
						FileStuff.storeObjectFile(_context, "Movie",
								_json.toString(), true);
					}
				} catch (JSONException e) {
					Log.e("JSON", "JSON OBJECT EXCEPTION");
				}
			} catch (JSONException e) {
				Log.e("JSON", "JSON OBJECT EXCEPTION");
			}
		}
	}

}
