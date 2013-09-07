package com.fyiadevelopment.simpbrowlauncher;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
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
		
		Button goButton = (Button) findViewById(R.id.goButton);
		goButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(_searchField.getText().toString()));
				startActivity(i);
				
			}
		});
	}


}
