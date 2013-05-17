package com.example.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class JSON.
 */
public class JSON {

	/* This class is to build and read back a JSON Object.
	 * JSON URL being emulated =
	 * http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=zns9a4f2hfm94ju3unu4axzk&q=ong+bak&page_limit=1
	 */
	public static JSONArray buildJSON() 
	{

		// Create Movies JSON Array Object
		JSONArray moviesJArray = new JSONArray();

		try {
			// Fill in movie details with JSON Objects
			JSONObject detailsObject = new JSONObject();

			detailsObject.put("Title", Movie.Title.setValue());
			detailsObject.put("Year", Movie.Year.setValue());
			detailsObject.put("MPAA_Ratings", Movie.MPAA_Ratings.setValue());
			detailsObject.put("RunTime", Movie.RunTime.setValue());
			detailsObject.put("Critics_Consensus", Movie.Critics_Consensus.setValue());

			// Add details to Movies JSON Array
			moviesJArray.put(detailsObject);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return moviesJArray;
	}

	/**
	 * Read the created JSON.
	 *
	 * @param selected passed from MainActivity.java
	 * @return result passes back the requested info from the JSON.
	 */
	public static String readJSON(String selected) 
	{
		String result;
		JSONArray object = buildJSON();
		
		try 
		{
			//JSON Array Object 0 is Movies. get string provides the detail Object.
			result = object.getJSONObject(0).getString(selected);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.toString();
		}
		
		return result;
	}

}
