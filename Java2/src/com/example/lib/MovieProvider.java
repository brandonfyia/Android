package com.example.lib;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.movieInfo.FileStorageManager;

public class MovieProvider extends ContentProvider{

	public static final String AUTHORITY = "com.example.java1week2_4.movieprovider";
	
	public static class MovieData implements BaseColumns {
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY +"/items");
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.dgardinier.museum.item";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.dgardinier.museum.item";
		
		//Define Columns
		public static final String TITLE_COLUMN = "title";
		public static final String YEAR_COLUMN = "year";
		public static final String MPAA_COLUMN = "mpaa";
		public static final String RUNTIME_COLUMN = "runtime";
		public static final String CRITICS_COLUMN = "critics";
		public static final String POSTER_COLUMN = "poster";
		public static final String INFO_COLUMN = "info";
		
		public static final String[] PROJECTION = {"_Id", TITLE_COLUMN, YEAR_COLUMN, MPAA_COLUMN, RUNTIME_COLUMN, CRITICS_COLUMN, POSTER_COLUMN, INFO_COLUMN};
		
		private MovieData() {};
	}
	
	public static final int ITEMS = 1;
	public static final int ITEMS_TITLE_FILTER = 2;
	public static final int ITEMS_YEAR_FILTER = 3;
	public static final int ITEMS_MPAA_FILTER = 4;
	public static final int ITEMS_RUNTIME_FILTER = 5;
	public static final int ITEMS_CRITICS_FILTER = 6;
	public static final int ITEMS_POSTER_FILTER = 7;
	public static final int ITEMS_INFO_FILTER = 8;
	
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS);
		uriMatcher.addURI(AUTHORITY, "items/title/*", ITEMS_TITLE_FILTER);
		uriMatcher.addURI(AUTHORITY, "items/year/*", ITEMS_YEAR_FILTER);
		uriMatcher.addURI(AUTHORITY, "items/mpaa/*", ITEMS_MPAA_FILTER);
		uriMatcher.addURI(AUTHORITY, "items/runtime/*", ITEMS_RUNTIME_FILTER);
		uriMatcher.addURI(AUTHORITY, "items/critics/*", ITEMS_CRITICS_FILTER);
		uriMatcher.addURI(AUTHORITY, "items/poster/*", ITEMS_POSTER_FILTER);
		uriMatcher.addURI(AUTHORITY, "items/info/*", ITEMS_INFO_FILTER);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			return MovieData.CONTENT_TYPE;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		MatrixCursor result = new MatrixCursor(MovieData.PROJECTION);
		//Pull saved JSON data and parse
		FileStorageManager.getFileStuffInstance();
		String JSONString = (String) FileStorageManager.readStringFile(getContext(), "Movie", true);
		JSONObject job = null;
		try {
			job = new JSONObject(JSONString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			try {
				String title;
				String year;
				String mpaa_rating;
				String runtime;
				String critics_consensus;
				String posters;
				String links;
				if (job.has("title")) {
					title = job.getString("title");
				} else {
					title = "No information availible";
				}
				if (job.has("year")) {
					year = job.getString("year");
				} else {
					year = "No information availible";
				}
				if (job.has("mpaa_rating")) {
					mpaa_rating = job.getString("mpaa_rating");
				} else {
					mpaa_rating = "No information availible";
				}
				if (job.has("runtime")) {
					runtime = job.getString("runtime");
				} else {
					runtime = "No information availible";
				}
				if (job.has("critics_consensus")) {
					critics_consensus = job.getString("critics_consensus");
				} else {
					critics_consensus = "No information availible";
				}
				if (job.has("posters") && job.getJSONObject("posters").has("detailed")) {
					posters = job.getJSONObject("posters").getString("detailed");
				} else {
					posters = "No information availible";
				}
				if (job.has("links") && job.getJSONObject("links").has("alternate")) {
					links = job.getJSONObject("links").getString("alternate");
				} else {
					links = "No information availible";
				}
				result.addRow(new Object[]{1, 
						title,
						year,
						mpaa_rating,
						runtime,
						critics_consensus, 
						posters, 
						links});
				Log.i("PROVIDER", "Got Values");
				Log.i("PROVIDER", "Returned Values");
				return result;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case ITEMS_TITLE_FILTER:
			try {
				result.addRow(new Object[]{1, job.getString("title")});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		case ITEMS_YEAR_FILTER:
			try {
				result.addRow(new Object[]{1, job.getString("year")});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case ITEMS_MPAA_FILTER:
			try {
				result.addRow(new Object[]{1, job.getString("mpaa_rating")});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case ITEMS_RUNTIME_FILTER:
			try {
				result.addRow(new Object[]{1, job.getString("runtime")});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case ITEMS_CRITICS_FILTER:
			try {
				result.addRow(new Object[]{1, job.getString("critics_consensus")});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case ITEMS_POSTER_FILTER:
			try {
				result.addRow(new Object[]{1,job.getJSONObject("posters").getString("detailed")});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case ITEMS_INFO_FILTER:
			try {
				result.addRow(new Object[]{1,job.getJSONObject("links").getString("alternate")});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		default:
				Log.e("QUERY", "invalid uri - " + uri.toString());
		}
		//Send data out
		Log.i("PROVIDER", "Returned Values");
		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
