package com.example.movieInfo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.java1week2_4.R;

public class MovieInfoFragment extends Fragment implements OnClickListener {

	

	TextView _titleTV;
	TextView _yearTV;
	TextView _mpaaTV;
	TextView _runtimeTV;
	TextView _criticsTV;
	TextView _infoTV;



	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.movie_search, container);

		// Set Defaults for variables

	
		
//		//Check for saved instance
//		if (savedInstanceState != null) {
//			displayResults(
//					savedInstanceState.getString("TITLE"),
//					savedInstanceState.getString("YEAR"),
//					savedInstanceState.getString("MPAA"),
//					savedInstanceState.getString("RUNTIME"),
//					savedInstanceState.getString("CRITICS"),
//					savedInstanceState.getString("INFO"));
//		}

		return view;
	}

	// Display results
	public void displayResults(String title, String year,
			String mpaa, String runtime, String critics, String info) {
		Log.i("MovieInfoFragment - display Results", title);
		_titleTV = (TextView) getActivity().findViewById(R.id.titleTV);
		_yearTV = (TextView) getActivity().findViewById(R.id.yearTV);
		_mpaaTV = (TextView) getActivity().findViewById(R.id.mpaaTV);
		_runtimeTV = (TextView) getActivity().findViewById(R.id.runtimeTV);
		_criticsTV = (TextView) getActivity().findViewById(R.id.criticsTV);
		_infoTV = (TextView) getActivity().findViewById(R.id.infoTV);
		
		// Title
		_titleTV.setText(title);
		// Year
		_yearTV.setText(year);
		// Rating
		_mpaaTV.setText(mpaa);
		// Runtime
		_runtimeTV.setText(runtime + " minutes");
		// Critics
		_criticsTV.setText(critics);
		// Info
		_infoTV.setText(info);
		
		// More Info Link handler
		_infoTV.setPaintFlags(_infoTV.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);
		_infoTV.setOnClickListener(this);
	}

	//Clear text
	public void clearInfo (){
		_titleTV = (TextView) getActivity().findViewById(R.id.titleTV);
		_yearTV = (TextView) getActivity().findViewById(R.id.yearTV);
		_mpaaTV = (TextView) getActivity().findViewById(R.id.mpaaTV);
		_runtimeTV = (TextView) getActivity().findViewById(R.id.runtimeTV);
		_criticsTV = (TextView) getActivity().findViewById(R.id.criticsTV);
		_infoTV = (TextView) getActivity().findViewById(R.id.infoTV);
		
		// Clear out all text fields
		_titleTV.setText("");
		_yearTV.setText("");
		_mpaaTV.setText("");
		_runtimeTV.setText("");
		_criticsTV.setText("");
		_infoTV.setText("");
	}

	@Override
	public void onClick(View v) {
		Intent infoIntent = new Intent(Intent.ACTION_VIEW, Uri
				.parse(_infoTV.getText().toString()));
		startActivity(infoIntent);
	}

//	@Override
//	public void onSaveInstanceState(Bundle savedInstanceState) {
//		super.onSaveInstanceState(savedInstanceState);
//		_titleTV = (TextView) getActivity().findViewById(R.id.titleTV);
//		_yearTV = (TextView) getActivity().findViewById(R.id.yearTV);
//		_mpaaTV = (TextView) getActivity().findViewById(R.id.mpaaTV);
//		_runtimeTV = (TextView) getActivity().findViewById(R.id.runtimeTV);
//		_criticsTV = (TextView) getActivity().findViewById(R.id.criticsTV);
//		_infoTV = (TextView) getActivity().findViewById(R.id.infoTV);
//		
//		if (_titleTV.getText().toString() != null) {
//			savedInstanceState.putString("TITLE", _titleTV.getText().toString());
//			savedInstanceState.putString("YEAR", _yearTV.getText().toString());
//			savedInstanceState.putString("MPAA", _mpaaTV.getText().toString());
//			savedInstanceState.putString("RUNTIME", _runtimeTV.getText().toString());
//			savedInstanceState.putString("CRITICS", _criticsTV.getText().toString());
//			savedInstanceState.putString("INFO", _infoTV.getText().toString());
//
//		}
//		
//	}
}
