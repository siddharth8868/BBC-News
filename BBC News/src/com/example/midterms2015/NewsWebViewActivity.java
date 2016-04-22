/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */package com.example.midterms2015;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsWebViewActivity extends Activity {
	Intent intent;
	WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_web_view);
		intent = getIntent();
		webView = (WebView) findViewById(R.id.webView1);
		webView.loadUrl(intent.getStringExtra(NewsDetailsActivity.LINK));
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;
			}
		});
	}
}