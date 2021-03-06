/*
 * Project SimpBrowLauncher
 * 
 * Package com.fyiadevelopment.simpbrowlauncher
 * 
 * @author Sease, Brandon
 * 
 * date    Sep 8, 2013
 * 
 */
package com.fyiadevelopment.simpbrowlauncher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UrlLauncherActivity extends Activity {

	Context _context;
	EditText _searchField;
	TextView _header;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_context = this;
		_searchField = (EditText) findViewById(R.id.textBox);
		//Add HTTP to urls for people to hopefully ensure no errors.
		_searchField.setText("http://");
		
		//Create button and on click
		Button goButton = (Button) findViewById(R.id.goButton);
		goButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Create intent
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				//Add url to intent
				sendIntent.putExtra(Intent.EXTRA_TEXT, _searchField.getText().toString());
				sendIntent.setType("text/plain");
				//Force chooser to pop up. 
				startActivity(Intent.createChooser(sendIntent, "Choose Browser"));
				
			}
		});
	}


}
