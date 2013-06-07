/*
 * Project Java2
 * 
 * Package com.example.java1week2_4
 * 
 * @author Sease, Brandon
 * 
 * date    Jun 6, 2013
 * 
 */
package com.example.java1week2_4;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import services.MyHTTPService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lib.WebStuff;
import com.loopj.android.image.SmartImageView;

/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {

	// Global variables
	Context _context;
	Boolean _connected = false;
	JSONObject _json;
	EditText _searchField;
	TextView _results;
	Spinner _iSpin;
	TextView _header;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set Content View
		setContentView(R.layout.main);

		// Set defaults for variables
		_searchField = (EditText) findViewById(R.id.searchField);
		_results = (TextView) findViewById(R.id.resultsText);
		_iSpin = (Spinner) findViewById(R.id.infoSpinner);
		_header = (TextView) findViewById(R.id.header);
		_context = this;

		// Set Header Default text
		_header.setText("Search For A Movie");

		// Detect Network connection
		_connected = WebStuff.getConnectionStatus(_context);
		if (_connected) {
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));
		} else {
			// If no connection, load last viewed movie details

			// Warning Display
			_header.setText("No connection. Viewing last searched movie.");

			// Load last JSON file
			Object stored = FileStuff.getFileStuffInstance().readObjectFile(
					_context, "Movie", true);
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
			try {
				_searchField.setText(_json.getString("title").toString());
			} catch (JSONException e) {
				Log.e("SAVED FILE", "CANT LOAD MOVIE TITLE");
			}
		}

		// Search Handler
		Button searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("CLICK HANDLER", _searchField.getText().toString());
				// Send text field entry to URL builder and request API
				// getQuote(_searchField.getText().toString());
				//HTTP Service Handler
				Handler HTTPHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {

						if (msg.arg1 == RESULT_OK && msg.obj != null) {

							try {
								JSONObject resultJSON = new JSONObject((String)msg.obj);
								// Set data
								/*
								 * JSON Array is Movies. object 0 is the first
								 * result. get string provides the detail
								 * Object.
								 */
								if (resultJSON.getString("total").toString()
										.compareTo("0") == 0) {
									Toast toast = Toast.makeText(_context,
											"No movie by that name exists",
											Toast.LENGTH_LONG);
									toast.show();
									_header.setText("Try Again");
								} else {
									_json = resultJSON.getJSONArray("movies")
											.getJSONObject(0);
									// Store object in external memory
									FileStuff.getFileStuffInstance()
											.storeObjectFile(_context, "Movie",
													_json.toString(), true);
									// Set Text Values
									_searchField.setText(_json.getString(
											"title").toString());
									_results.setText("");
									_header.setText("Movie Found!");
								}
							} catch (Exception e) {
								Log.e("HTTP HANDLER", e.getMessage().toString());
								e.printStackTrace();
								_results.setText("No info found. Please double check that the movie title was entered correctly");
							}
						}
					}
				};
				//HTTP Service Messenger
				Messenger HTTPMessenger = new Messenger(HTTPHandler);
				//HTTP Service Intent
				Intent getHTTPIntent = new Intent(_context, MyHTTPService.class);
				getHTTPIntent.putExtra(MyHTTPService.MESSENGER_KEY, HTTPMessenger);
				getHTTPIntent.putExtra(MyHTTPService.MOVIE_KEY, _searchField.getText().toString());
				//Start service
				startService(getHTTPIntent);

				// Set spinner back to default
				_iSpin.setSelection(0);
				// Close keyboard
				InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});

		// Create Info Spinner Display
		ArrayAdapter<CharSequence> listAdapter = ArrayAdapter
				.createFromResource(_context, R.array.detailsArray,
						android.R.layout.simple_spinner_item);
		listAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		_iSpin.setAdapter(listAdapter);

		// Info Spinner Handler
		_iSpin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
					long id) {
				Log.i("DETAIL SELECTED", parent.getItemAtPosition(pos)
						.toString());
				// Check to see if JSON has been created yet
				if (_json != null) {
					try {
						// Set selected value
						String selected = new String(parent.getItemAtPosition(
								pos).toString());
						Log.i("DETAIL SELECTED", selected);
						// If not at default value fetch results
						if (!selected.matches(getString(R.string.see_details))) {
							// Change from nice formatting to API formatting
							// Title
							if (selected.matches(getString(R.string.title))) {
								_results.setText("Movie Title:\n \t"
										+ _json.getString(
												getString(R.string.titleAPI))
												.toString());
								// Year
							} else if (selected
									.matches(getString(R.string.year))) {
								_results.setText("Year Released:\n \t"
										+ _json.getString(
												getString(R.string.yearAPI))
												.toString());
								// Rating
							} else if (selected
									.matches(getString(R.string.mpaa_rating))) {
								_results.setText("MPAA Rating:\n \t"
										+ _json.getString(
												getString(R.string.mpaa_ratingAPI))
												.toString());
								// Runtime
							} else if (selected
									.matches(getString(R.string.runtime))) {
								_results.setText("Movie Runtime:\n \t"
										+ _json.getString(
												getString(R.string.runtimeAPI))
												.toString() + " minutes");
								// Critics
							} else if (selected
									.matches(getString(R.string.critics_consensus))) {
								_results.setText("Critics Consensus:\n \t"
										+ _json.getString(
												getString(R.string.critics_consensusAPI))
												.toString());
								// Movie Poster
							} else if (selected
									.matches(getString(R.string.thumbnail))) {
								_results.setText("Poster:\n \t");
								String link = new String(_json.getJSONObject(
										"posters").getString(
										getString(R.string.thumbnailAPI)
												.toString()));
								SmartImageView iView = new SmartImageView(
										_context);
								iView.setImageUrl(link);
								LayoutParams lp = new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.MATCH_PARENT,
										LinearLayout.LayoutParams.WRAP_CONTENT);
								LinearLayout ll = (LinearLayout) findViewById(R.id.mainLayout);
								iView.setLayoutParams(lp);
								ll.addView(iView);
								// try {
								// iView.setImageUrl(link);
								// LinearLayout ll = (LinearLayout)
								// findViewById(R.layout.main);
								// ll.addView(iView);
								// } catch (Exception e) {
								// Log.e("IMAGE", "NO IMAGE");
								// Log.e("IMAGE", link);
								// }
							}
						}
					} catch (JSONException e) {
						Log.e("RESULTS ERROR", "NO RESULTS");
						_results.setText("No info found. Please double check that the movie title was entered correctly");
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
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

	// Service Handler
	static final Handler serviceHandler = new Handler() {
		public void handleMessage(Message message) {
			Object returnedObject = message.obj;

			// Test if status is ok.
			if (message.arg1 == RESULT_OK && returnedObject != null) {

			}
		};
	};

	// Build URL
	private void getQuote(String movieName) {
		String baseURL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=zns9a4f2hfm94ju3unu4axzk&q=";
		String qs;
		try {
			qs = URLEncoder.encode(movieName, "UTF-8");
		} catch (Exception e) {
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
					/*
					 * JSON Array is Movies. object 0 is the first result. get
					 * string provides the detail Object.
					 */

					if (resultJSON.getString("total").toString().compareTo("0") == 0) {
						Toast toast = Toast.makeText(_context,
								"No movie by that name exists",
								Toast.LENGTH_LONG);
						toast.show();
						_header.setText("Try Again");
					} else {
						_json = resultJSON.getJSONArray("movies")
								.getJSONObject(0);
						// Store object in external memory
						FileStuff.getFileStuffInstance().storeObjectFile(
								_context, "Movie", _json.toString(), true);
						// Set Text Values
						_searchField.setText(_json.getString("title")
								.toString());
						_results.setText("");
						_header.setText("Movie Found!");
					}
				} catch (JSONException e) {
					Log.e("JSON", "JSON OBJECT EXCEPTION");
					_results.setText("No info found. Please double check that the movie title was entered correctly");
				}
			} catch (JSONException e) {
				Log.e("JSON", "JSON OBJECT EXCEPTION");
				_results.setText("No info found. Please double check that the movie title was entered correctly");
			}
		}
	}

}