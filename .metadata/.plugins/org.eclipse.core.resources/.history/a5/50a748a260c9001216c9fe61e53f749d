package com.example.java1week2_4;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowResults extends LinearLayout{
	
	
//Create Results View
	public ShowResults(Context context, String selected, JSONObject json) {
		super(context);
		
		LayoutParams lp;
		
		TextView result = new TextView(context);
		if (!selected.matches("See Details")) {
			try {
				result.setText(json.getString(selected));
			} catch (JSONException e) {
				Log.e("RESULTS ERROR", "NO RESULTS");
			}
			result.setGravity(Gravity.CENTER);
			result.setTextSize(30);
			
			this.addView(result);
			
			lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			this.setLayoutParams(lp);
		}
		
	}

}
