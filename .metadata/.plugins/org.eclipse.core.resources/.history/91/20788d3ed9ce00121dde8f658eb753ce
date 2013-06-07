package com.example.lib;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WebStuff {

	//Global variables
	static Boolean _conn = false;
	static String _connectionType = "Unavailable";
	
	//Check type of connection
	public static String getConnectionType(Context context){
		netInfo(context);
		return _connectionType;
	}
	
	//Check if connected
	public static Boolean getConnectionStatus(Context context){
		netInfo(context);
		return _conn;
	}
	
	//Check if connected and what type
	private static void netInfo(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		//Check if connected
		if (ni != null)
		{
			//Check type of connection?
			if (ni.isConnected())
			{
				_connectionType = ni.getTypeName();
				_conn = true;
			}	
		}
	}
	
	public static String getURLStringResponse(URL url){
		String response = "";
		try{
			//Open Connection on URL
			URLConnection conn = url.openConnection();
			//Open Buffer Input Stream
			BufferedInputStream bin = new BufferedInputStream(conn.getInputStream());
			//Start Byte Stream
			byte[] contentBytes = new byte[1025];
			int bytesRead = 0;
			//Buffer Bytes into response buffer
			StringBuffer responseBuffer = new StringBuffer();
			
			while((bytesRead = bin.read(contentBytes)) != -1){
				response = new String(contentBytes, 0, bytesRead);
				responseBuffer.append(response);
			}
			//Return results
			return responseBuffer.toString();
		} catch (Exception e){
			Log.e("URL RESPONCE ERROR", "getURLStringResponce");
		}
		
		return response;
	}
}
