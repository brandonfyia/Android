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
package com.example.movieInfo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.WebServiceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.java1week2_4.R;
import com.example.lib.MovieProvider;
import com.example.lib.WebManager;

/**
 * The Class MainActivity.
 */

public class MovieSearchActivity extends Activity implements
		MovieSearchFragment.onGOClicked {

	// Global variables
	Context _context;
	Boolean _connected = false;
	JSONObject _json;
	MovieInfoFragment     _infoFragment;
	MovieSearchFragment _searchFragment;
	MoviePosterFragment _posterFragment;

	String _JSONString;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set Content View
		setContentView(R.layout.movie_search_fragments);

		// Set defaults for variables
		_context = this;
		_searchFragment = (MovieSearchFragment) getFragmentManager()
				.findFragmentById(R.id.fragmentMovieSearch);
		_infoFragment = (MovieInfoFragment) getFragmentManager()
				.findFragmentById(R.id.fragmentMovieInfo);
		_posterFragment = (MoviePosterFragment) getFragmentManager()
				.findFragmentById(R.id.fragmentMoviePoster);

		// Check for previously saved state
		if (savedInstanceState != null) {
			// Restore old Values
			_JSONString = savedInstanceState.getString("JSON");
		}

		// Detect Network connection
		_connected = WebManager.getConnectionStatus(_context);
		if (_connected) {
			Log.i("NETWORK CONNECTION", WebManager.getConnectionType(_context));
		} else {
			// If no connection, load last viewed movie details

			// Load last JSON file
			FileStorageManager.getFileStuffInstance();
			String stored = (String) FileStorageManager.readStringFile(
					_context, "Movie", true);
			if (stored != null) {
				try {
					_json = new JSONObject(stored);
					Log.i("SAVED JSON", _json.toString());
					/*
					 * displayResults(String header, String title, String year,
					 * String mpaa, String runtime, String critics, String
					 * poster, String info)
					 */
					_searchFragment.displaySearchAndHeader("No connection. Viewing last searched movie.", _json.getString(getString(R.string.titleAPI)));
					_posterFragment.displayPoster(_json.getJSONObject("posters").getString(getString(R.string.thumbnailAPI).toString()));
					_infoFragment.displayResults(
							_json.getString(getString(R.string.titleAPI))
									.toString(),
							_json.getString(getString(R.string.yearAPI))
									.toString(),
							_json.getString(getString(R.string.mpaa_ratingAPI))
									.toString(),
							_json.getString(getString(R.string.runtimeAPI))
									.toString(),
							_json.getString(
									getString(R.string.critics_consensusAPI))
									.toString(),
							_json.getJSONObject("links").getString(
									getString(R.string.infoAPI).toString()));

				} catch (JSONException e) {
					Log.e("SAVED FILE", "CANT MAKE JSON");
				}
			}
		}
	};

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 0) {
			Bundle results = data.getExtras();
			if (results != null) {
				// Trim JSON to just needed part
				int movieNumber = results.getInt("MOVIE_NUMBER");
				try {
					JSONObject storedJSON = new JSONObject(_JSONString)
							.getJSONArray("movies").getJSONObject(movieNumber);
					// Store object in external memory
					FileStorageManager.getFileStuffInstance();
					FileStorageManager.storeStringFile(_context, "Movie",
							storedJSON.toString(), true);
					// Pull data back from content provider (movie provider)
					Cursor cursor = getContentResolver().query(
							MovieProvider.MovieData.CONTENT_URI, null, null,
							null, null);
					cursor.moveToFirst();
					String temp = new String(cursor.getString(2));
					Log.i("Cursor", temp);
					//Display Results
					_searchFragment.displaySearchAndHeader("Movie Found!", cursor.getString(1));
					_posterFragment.displayPoster(cursor.getString(6));
					_infoFragment.displayResults(
							cursor.getString(1), cursor.getString(2),
							cursor.getString(3), cursor.getString(4),
							cursor.getString(5),
							cursor.getString(7));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// Web Stuff
	@SuppressLint("HandlerLeak")
	public void webHandler(String movie) {
		
		// Clear old data
				_infoFragment.clearInfo();
				_posterFragment.clearPoster();
				
		// HTTP Service Handler
		Handler webHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.arg1 == RESULT_OK && msg.obj != null) {
					try {
						_JSONString = (String) msg.obj;
						JSONObject resultJSON = new JSONObject(_JSONString);
						// Set data
						/*
						 * JSON Array is Movies. object 0 is the first result.
						 * get string provides the detail Object.
						 */
						if (resultJSON.getString("total").toString()
								.compareTo("0") == 0) {
							Toast toast = Toast.makeText(_context,
									"No movie by that name exists",
									Toast.LENGTH_LONG);
							toast.show();	
							_searchFragment.displaySearchAndHeader("Try Again", null);
							// Close Progress Bar
							_searchFragment.closeProgressBar();
						} else {

							// Loop through a JSON Array and get Movie
							// Names
							ArrayList<HashMap<String, String>> movieList = new ArrayList<HashMap<String, String>>();
							JSONArray movies = resultJSON
									.getJSONArray("movies");
							int movieSize = movies.length();
							for (int i = 0; i < movieSize; i++) {
								JSONObject movieDetails = movies
										.getJSONObject(i);
								String title = movieDetails
										.getString(getString(R.string.titleAPI)
												.toString());

								HashMap<String, String> displayMap = new HashMap<String, String>();
								displayMap.put("TITLE", title);

								movieList.add(displayMap);
							}
							// Close Progress Bar
							_searchFragment.closeProgressBar();
							
							// Load Picker Activity
							Intent nextActivity = new Intent(_context,
									MoviePickerActivity.class);
							nextActivity
									.putExtra(
											"HEADER",
											"We found "
													+ String.valueOf(movieSize)
													+ " movies by that name. Select the one you wanted below.");
							nextActivity.putExtra("MOVIES", movieList);
							startActivityForResult(nextActivity, 0);

						}
					} catch (Exception e) {
						Log.e("HTTP HANDLER", e.getMessage().toString());
						e.printStackTrace();
						_searchFragment.displaySearchAndHeader("No info found. Please double check that the movie title was entered correctly", null);
						// Close Progress Bar
						_searchFragment.closeProgressBar();

					}
				}
			}

		};
		// HTTP Service Messenger
		Messenger webMessenger = new Messenger(webHandler);
		// HTTP Service Intent
		Intent getWebIntent = new Intent(_context, WebServiceManager.class);
		getWebIntent.putExtra(WebServiceManager.MESSENGER_KEY, webMessenger);
		getWebIntent.putExtra(WebServiceManager.MOVIE_KEY, movie);
		// Start service
		startService(getWebIntent);
		// Close keyboard
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(
				getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString("JSON", _JSONString);

	}
}
