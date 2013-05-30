package com.example.java1week2_4;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class InfoSpinner extends LinearLayout{

	Spinner _infoS;
	Context _context;
	ArrayList<String> _details = new ArrayList<String>();
	String _selected = new String("");
	
	public InfoSpinner(Context context) {
		super(context);
		_context = context;
		
		LayoutParams lp;

		//Defalut Text
		_details.add("See Details");
		
		_infoS = new Spinner(_context);
		lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
		_infoS.setLayoutParams(lp);
		
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, _details);
		listAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		_infoS.setAdapter(listAdapter);
		
		//Add Values
		updateDetails();
		
		this.addView(_infoS);
		
	}
	
	public Spinner getSpinner(){
		return _infoS;
	}
	
	//Values
	private void updateDetails() {
		_details.add("title");
		_details.add("year");
		_details.add("mpaa_rating");
		_details.add("runtime");
		_details.add("critics_consensus");
	}

}
