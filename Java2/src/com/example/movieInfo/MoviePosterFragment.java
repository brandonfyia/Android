package com.example.movieInfo;

import com.example.java1week2_4.R;
import com.loopj.android.image.SmartImageView;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoviePosterFragment extends Fragment {

	SmartImageView _iView;
	String _link;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.movie_search_poster, container);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//Check for saved instance
				if (savedInstanceState != null) {

					Log.i("MoviePosterFragment - onCreateView", "" + savedInstanceState.getString("LINK"));
					_link = savedInstanceState.getString("LINK");
					displayPoster(_link);
				}
	}

	public void displayPoster(String link) {

		setDefaults();

		_link = link;
		if (link != null) {
			_iView.setImageUrl(_link);
		}

	}

	// Clear Poster
	public void clearPoster() {
		setDefaults();
		_link = null;
		_iView.setImageDrawable(null);
		
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.i("MoviePosterFragment - onSaveInstanceState", "" + _link);
		if (_link != null) {
			savedInstanceState.putString("LINK", _link);
		}

	}
	//Set Defaults
	public void setDefaults(){
		_iView = (SmartImageView) getActivity().findViewById(R.id.posterIV);
	};
}
