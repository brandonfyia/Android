package com.example.lib;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.LinearLayout.LayoutParams;

public class FormThings {

	public static LinearLayout singleEntryWithButton(Context context, String buttonText)
	{
		LinearLayout ll = new LinearLayout(context);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setLayoutParams(lp);
		
		Button b = new Button(context);
		b.setText(buttonText);
		b.setId(2);
		ll.addView(b);
		
		
		return ll;
	}
	
	public static RadioGroup getOptions(Context context, String[] options)
	{
		RadioGroup boxes = new RadioGroup(context);
		
		for (int i = 0; i < options.length; i++) 
		{
			RadioButton rb = new RadioButton(context);
			rb.setText(options[i]);
			rb.setId(i+1);
			boxes.addView(rb);
		}
		
		return boxes;
	}
	
	
}
