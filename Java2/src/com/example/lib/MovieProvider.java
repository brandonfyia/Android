package com.example.lib;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

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
		
		public static final String[] PROJECTION = {"_Id", TITLE_COLUMN, YEAR_COLUMN, MPAA_COLUMN, RUNTIME_COLUMN, CRITICS_COLUMN, POSTER_COLUMN};
		
		private MovieData() {};
	}
	
	public static final int ITEMS = 1;
	public static final int ITEMS_ID = 2;
	
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS);
		uriMatcher.addURI(AUTHORITY, "items/#", ITEMS_ID);
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
		case ITEMS_ID:
			return MovieData.CONTENT_ITEM_TYPE;
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
		
//		String JSONStr
//		switch (uriMatcher.match(uri)) {
//		case ITEMS:
//			return MovieData.CONTENT_TYPE;
//		case ITEMS_ID:
//			return MovieData.CONTENT_ITEM_TYPE;
//		}
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
