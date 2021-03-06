package com.example.movieInfo;

import com.example.java1week2_4.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MovieSearchFragment extends Fragment implements OnClickListener {

	Context _context;
	EditText _searchField;
	TextView _header;
	ProgressDialog _pDialog;

	public interface onGOClicked {

		public void webHandler(String movie);

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

		View view = inflater.inflate(R.layout.movie_search_search, container);

		_context = getActivity();
		_header = (TextView) view.findViewById(R.id.header);
		_searchField = (EditText) view.findViewById(R.id.searchField);


		Button searchButton = (Button) view.findViewById(R.id.searchButton);
		searchButton.setOnClickListener(this);

		//Check for saved instance
		if (savedInstanceState != null) {
			Log.i("MovieSearchFragment - onCreateView", "" + savedInstanceState.getString("HEADER"));

			displaySearchAndHeader(savedInstanceState.getString("HEADER"), "");
		}

		return view;
	}

	@Override
	public void onClick(View v) { 
		_searchField = (EditText) getActivity().findViewById(R.id.searchField);

		Log.i("CLICK HANDLER", _searchField.getText().toString());

		// Progress bar
		_pDialog = new ProgressDialog(_context);
		_pDialog.setMessage("Fetching data");
		_pDialog.setIndeterminate(true);
		_pDialog.setCancelable(false);
		_pDialog.show();


		parentActivity.webHandler(_searchField.getText().toString());
	}

	// Close progress Bar
	public void closeProgressBar() {
		_pDialog.dismiss();
	};

	// Set Search and Header
	public void displaySearchAndHeader(String header, String search) {
		//setDefaults();
		Log.i("MovieSearchFragment - displaySearchAndHeader", header + " " + search);
		_searchField.setText(search);
		_header.setText(header);
	};


	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		Log.i("MovieSearchFragment - onSaveInstanceState", "" + _header.getText().toString());

		setDefaults();
		savedInstanceState.putString("HEADER", _header.getText().toString());

	}

	//Set Defaults
	public void setDefaults(){
		_header = (TextView) getActivity().findViewById(R.id.header);
		_searchField = (EditText) getActivity().findViewById(R.id.searchField);
	};
}
