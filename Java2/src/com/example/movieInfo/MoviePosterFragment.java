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

		return view;
	}

	public void displayPoster(String link) {
		// Poster
		_link = new String(link);
		_iView.setImageUrl(_link);
	}

	// Clear Poster
	public void clearPoster() {
		_iView.setImageUrl(null);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
}
