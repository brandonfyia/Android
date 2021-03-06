package com.example.movieInfo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.java1week2_4.R;

public class MoviePickerActivity extends Activity {

	//Global Variables
	Context _context;
	ListView _listView;
	TextView _header;
	ArrayList<HashMap<String, String>> _movieList;
	int _movieNumber;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Set Content View
		setContentView(R.layout.movie_picker);
		
		//Set defaults for variables
		_context = this;
		_listView = (ListView) this.findViewById(R.id.pickerList);
		_header = (TextView) this.findViewById(R.id.headerTV);
		
		//Get Intent Data
		Bundle data = getIntent().getExtras();
		if (data != null) {
			String headerText = data.getString("HEADER");
			_header.setText(headerText);
			_movieList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("MOVIES");
		}
		
		//Check for previously saved state
				if (savedInstanceState != null) {
					_movieNumber = savedInstanceState.getInt("MOVIE_NUMBER");
				}
		
		//List View Setup
		SimpleAdapter adapter = new SimpleAdapter(_context, _movieList, R.layout.list_row, new String[] {"TITLE"}, new int[] {R.id.listTV});
		_listView.setAdapter(adapter);
		//List View on click handler
		_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				_movieNumber = position;
				finish();
				return;
			}
		});
		
	}
	
	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra("MOVIE_NUMBER", _movieNumber);
		setResult(RESULT_OK, data);
		super.finish();
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("MOVIE_NUMBER", _movieNumber);
	}

}
