package com.example.movieInfo;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.java1week2_4.R;
import com.loopj.android.image.SmartImageView;

public class MovieSearchFragment extends Fragment implements OnClickListener {

	Context _context;
	EditText _searchField;
	TextView _header;
	TextView _titleTV;
	TextView _yearTV;
	TextView _mpaaTV;
	TextView _runtimeTV;
	TextView _criticsTV;
	SmartImageView _iView;
	TextView _infoTV;
	ProgressDialog _pDialog;
	String _link;

	public interface onGOClicked {

		public void webHandler(String movie);
//
//		public void displayResults(String header, String title, String year,
//				String mpaa, String runtime, String critics, String poster,
//				String info);

	};

	private onGOClicked parentActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof onGOClicked) {
			parentActivity = (onGOClicked) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ "must implement onGOClicked");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.movie_search, container);

		// Set Defaults for variables
		_context = getActivity();
		_searchField = (EditText) view.findViewById(R.id.searchField);
		_header = (TextView) view.findViewById(R.id.header);
		_titleTV = (TextView) view.findViewById(R.id.titleTV);
		_yearTV = (TextView) view.findViewById(R.id.yearTV);
		_mpaaTV = (TextView) view.findViewById(R.id.mpaaTV);
		_runtimeTV = (TextView) view.findViewById(R.id.runtimeTV);
		_criticsTV = (TextView) view.findViewById(R.id.criticsTV);
		_iView = (SmartImageView) view.findViewById(R.id.posterIV);
		_infoTV = (TextView) view.findViewById(R.id.infoTV);
		
		//Check for saved instance
		if (savedInstanceState != null) {
			displayResults(savedInstanceState.getString("HEADER"),
					savedInstanceState.getString("TITLE"),
					savedInstanceState.getString("YEAR"),
					savedInstanceState.getString("MPAA"),
					savedInstanceState.getString("RUNTIME"),
					savedInstanceState.getString("CRITICS"),
					savedInstanceState.getString("LINK"),
					savedInstanceState.getString("INFO"));
		}

		Button searchButton = (Button) view.findViewById(R.id.searchButton);
		searchButton.setOnClickListener(this);

		// More Info Link handler
		_infoTV.setPaintFlags(_infoTV.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);
		_infoTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent infoIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(_infoTV.getText().toString()));
				startActivity(infoIntent);

			}
		});

		return view;
	}

	@Override
	public void onClick(View v) {

		Log.i("CLICK HANDLER", _searchField.getText().toString());

		// Progress bar
		_pDialog = new ProgressDialog(_context);
		_pDialog.setMessage("Fetching data");
		_pDialog.setIndeterminate(true);
		_pDialog.setCancelable(false);
		_pDialog.show();

		// Clear out all text fields
		_titleTV.setText("");
		_yearTV.setText("");
		_mpaaTV.setText("");
		_runtimeTV.setText("");
		_criticsTV.setText("");
		_infoTV.setText("");
		_iView.setImageUrl(null);

		parentActivity.webHandler(_searchField.getText().toString());
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString("SEARCH_FIELD", _searchField.getText()
				.toString());
		savedInstanceState.putString("HEADER", _header.getText().toString());
		savedInstanceState.putString("TITLE", _titleTV.getText().toString());
		savedInstanceState.putString("YEAR", _yearTV.getText().toString());
		savedInstanceState.putString("MPAA", _mpaaTV.getText().toString());
		savedInstanceState
				.putString("RUNTIME", _runtimeTV.getText().toString());
		savedInstanceState
				.putString("CRITICS", _criticsTV.getText().toString());
		savedInstanceState.putString("LINK", _link);
		savedInstanceState.putString("INFO", _infoTV.getText().toString());

	}
	
	// Display results
		public void displayResults(String header, String title, String year,
				String mpaa, String runtime, String critics, String poster,
				String info) {
			// Set Text Values
			_searchField.setText(title);
			_header.setText(header);
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
			// Poster
			_link = new String(poster);
			_iView.setImageUrl(_link);
			// Info
			_infoTV.setText(info);
		};
	
		//Close progress Bar
		public void closeProgressBar(){
			_pDialog.dismiss();
		};

}
