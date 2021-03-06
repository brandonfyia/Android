/*
 * Project SimpBrow
 * 
 * Package com.example.simpbrow
 * 
 * @author Sease, Brandon
 * 
 * date    Sep 8, 2013
 * 
 */
package com.example.simpbrow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class BrowserActivity extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);

		// Create Webview
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		// Set default page
		webView.loadUrl("http://www.google.com");
		webView.setWebViewClient(new webViewClient());

		// Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
		if (action.equalsIgnoreCase(Intent.ACTION_SEND)
				&& intent.hasExtra(Intent.EXTRA_TEXT)) {
			String s = intent.getStringExtra(Intent.EXTRA_TEXT);
			//Goto sent page
			webView.loadUrl(s);
		}

	}
	//This keeps new pages from being opened in the default browser. 
	private class webViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView webView, String url) {
			webView.loadUrl(url);
			return true;
		}
	}
	
	//This binds the back key to the browser if there is a page to go back to. 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
