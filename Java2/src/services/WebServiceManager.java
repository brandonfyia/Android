package services;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.lib.WebManager;

public class WebServiceManager extends IntentService {

	public WebServiceManager() {
		super("MyHTTPService");
	}
	
	public static final String MESSENGER_KEY = "Messenger";
	public static final String MOVIE_KEY = "movie";

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.i("HTTP SERVICE", "Started");

		// Get extras from MainActivity
		Bundle extras = intent.getExtras();
		Messenger messenger = (Messenger) extras.get(MESSENGER_KEY);
		String _movieName = (String) extras.get(MOVIE_KEY);

		// Build URL
		String baseURL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=zns9a4f2hfm94ju3unu4axzk&q=";
		String qs;
		try {
			qs = URLEncoder.encode(_movieName, "UTF-8");
		} catch (Exception e) {
			Log.e("BAD URL", "ENCODING PROBLEM");
			qs = "";
		}
		
		String response = "";
		URL finalURL;
		try {
			finalURL = new URL(baseURL + qs);
			

			response = WebManager.getURLStringResponse(finalURL);

		} catch (MalformedURLException e) {
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
		
		Message message = Message.obtain();
		message.arg1 = Activity.RESULT_OK;
		message.obj = response;
		
		try {
			messenger.send(message);
		} catch (RemoteException e) {
			Log.e("onHandelIntent", e.getMessage().toString());
			e.printStackTrace();
		}
	}
}
