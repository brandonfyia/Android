package com.example.movieInfo;

import com.example.java1week2_4.R;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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

		View view = inflater.inflate(R.layout.movie_search, container);

		_iView = (SmartImageView) view.findViewById(R.id.posterIV);

		_iView.setImageUrl("http://content8.flixster.com/movie/26/69/266994_mob.jpg");

		//Check for saved instance
		if (savedInstanceState != null) {
			displayPoster(savedInstanceState.getString("LINK"));
		}

		return view;
	}

	public void displayPoster(String link) {
		//_iView = (SmartImageView) getActivity().findViewById(R.id.posterIV);
		// Poster
		_link = link;
		if (link != null) {
			_iView.setImageUrl(_link);
		}
		
	}

	// Clear Poster
	public void clearPoster() {
		_iView = (SmartImageView) getActivity().findViewById(R.id.posterIV);
		_iView.setImageUrl(null);
	}

//	@Override
//	public void onSaveInstanceState(Bundle savedInstanceState) {
//		super.onSaveInstanceState(savedInstanceState);
//		savedInstanceState.putString("LINK", _link);
//	}
}
