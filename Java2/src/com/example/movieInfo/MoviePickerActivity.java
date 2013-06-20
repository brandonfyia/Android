package com.example.movieInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.java1week2_4.R;

public class MoviePickerActivity extends Activity {

	//Global Variables
	Context _context;
	ListView _listView;
	TextView _header;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Set Content View
		setContentView(R.layout.picker);
		
		//Set defaults for variables
		_context = this;
		_listView = (ListView) this.findViewById(R.id.pickerList);
		_header = (TextView) this.findViewById(R.id.headerTV);
		
		//Get Intent Data
		Bundle data = getIntent().getExtras();
		if (data != null) {
			String headerText = data.getString("TEST");
			_header.setText(headerText);
		}
		
		//Test Stuff
		Button testButton = (Button) this.findViewById(R.id.testButton);
		testButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Intent data = new Intent();
		data.putExtra("TEST", "GOT IT BACK!");
		setResult(RESULT_OK, data);
		super.finish();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}
