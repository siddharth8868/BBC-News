/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */package com.example.midterms2015;

import java.text.SimpleDateFormat;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetailsActivity extends Activity {
	public static final String LINK = "link";
	TextView title, pubDate, description, count;
	ImageView image;
	Intent intent, intent2;
	NewsItem news;
	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_details);
		title = (TextView) findViewById(R.id.textView1);
		pubDate = (TextView) findViewById(R.id.textView2);
		description = (TextView) findViewById(R.id.textView4);
		count = (TextView) findViewById(R.id.textView5);
		image = (ImageView) findViewById(R.id.imageView1);
		intent = getIntent();
		news = (NewsItem) intent.getSerializableExtra(NewsActivity.NEWS_ITEM);
		title.setText(news.getTitle());
		pubDate.setText(format.format(news.getPubDate()));
		description.setText(news.getDescription());
		count.setText(news.getCount()+" Views");
		Picasso.with(getApplicationContext()).load(news.getUrl()).into(image, new com.squareup.picasso.Callback() {
			@Override
			public void onError() {
				image.setImageDrawable(getResources().getDrawable(R.drawable.photo_not_found));
				image.setVisibility(View.VISIBLE);
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				image.setVisibility(View.VISIBLE);
			}
		});
	}
	
	public void goToLink (View v){
		intent2 = new Intent(getApplicationContext(), NewsWebViewActivity.class);
		intent2.putExtra(LINK,news.getLink());
		startActivity(intent2);
	}
}