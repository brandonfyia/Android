package com.example.movieInfo;

import com.example.java1week2_4.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class MoviePickerActivity extends Activity {

	//Global Variables
	Context _context;
	ListView _listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Set Content View
		setContentView(R.layout.picker);
		
		//Set defaults for variables
		_context = this;
		_listView = (ListView) this.findViewById(R.id.pickerList);
		
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}
