/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */package com.example.midterms2015;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewsActivity extends Activity {
	public static final String NEWS_ITEM = "newsItem";
	Intent intent, intent2;
	ArrayList<NewsItem> newsItems;
	String rawUrl;
	ProgressDialog progress;
	ListView listView;
	//ArrayAdapter<NewsItem> ad;
	CustomAdapter adapter;
	DataManager dm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		intent = getIntent();
		rawUrl = intent.getStringExtra(MainActivity.URL);
		listView = (ListView) findViewById(R.id.news_activity_listView1);
		progress = new ProgressDialog(this);
		
		dm = new DataManager(this);
			
		
		newsItems = new ArrayList<NewsItem>();
		//ad = new ArrayAdapter<NewsItem>(this,android.R.layout.simple_list_item_1,newsItems);
		adapter = new CustomAdapter(this, newsItems);
		adapter.setNotifyOnChange(true);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NewsItem item = newsItems.get(position);
				NewsItem dbItem = dm.getNewsItem(item.getLink());
				if (null != dbItem){
					int count = dbItem.getCount();
					if (count != 0){
						dbItem.setCount(++count);
						item = dbItem;
						dm.updateNewsItem(item);
					}
				} else {
					item.setCount(1);
					dm.saveNewsItem(item);
				}
				
				intent2 = new Intent(getApplicationContext(), NewsDetailsActivity.class);
				intent2.putExtra(NEWS_ITEM, item);	
				startActivity(intent2);
				
			}
		});
		
		if (!rawUrl.isEmpty()) {
			new asyncWork().execute(rawUrl);

		} else {
			newsItems = new ArrayList<NewsItem>(dm.getNewsItem());
			Collections.sort(newsItems, new CompareByCountDescen());
			adapter.addAll(newsItems);
			listView.setAdapter(adapter);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_sort_title_a2z) {
			Collections.sort(newsItems, new CompareByTitleAZ());
			adapter.notifyDataSetChanged();
			return true;
		} else if(id == R.id.action_sort_title_z2a){
			Collections.sort(newsItems, new CompareByTitleZA());
			adapter.notifyDataSetChanged();
			return true;
		} else if(id == R.id.action_sort_pubdate_ascending){
			Collections.sort(newsItems, new CompareByTitleDateAscen());
			adapter.notifyDataSetChanged();
			return true;
		} else if(id == R.id.action_sort_pubdate_descending){
			Collections.sort(newsItems, new CompareByTitleDateDescen());
			adapter.notifyDataSetChanged();
			return true;
		} else if(id == R.id.action_show_new){
			adapter.clear();
			adapter.addAll(filterNews());
			adapter.notifyDataSetChanged();
			return true;
		} else if(id == R.id.action_delete_history){
			if (rawUrl.isEmpty())
				newsItems.clear();
				
			adapter.clear();
			adapter.addAll(newsItems);
			adapter.notifyDataSetChanged();
			dm.deleteNewsItems();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	class asyncWork extends AsyncTask<String, Void, ArrayList<NewsItem>> {

		@Override
		protected ArrayList<NewsItem> doInBackground(String... params) {
			if (isConnectedOnline()) {
				try {
					URL url = new URL(params[0]);
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					con.setRequestMethod("GET");
					con.connect();
					int statusCode = con.getResponseCode();
					if (statusCode == HttpURLConnection.HTTP_OK) {
						return ParseUtil.SaxParser.parseUrl(con
									.getInputStream());
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			progress.setMessage(getResources().getString(R.string.loading_news));
			progress.show();
		}

		@Override
		protected void onPostExecute(ArrayList<NewsItem> result) {
			if(result == null){
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.noInternet),
						Toast.LENGTH_SHORT).show();
			} else if (result.size() != 0) {
				for (NewsItem i : result){
					adapter.add(i);
				}
				newsItems = result;
				//adapter = new CustomAdapter(getApplicationContext(), result);
				listView.setAdapter(adapter);
			} else {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.noResults),
						Toast.LENGTH_SHORT).show();
			}

			progress.dismiss();
		}
		
		private boolean isConnectedOnline() {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplication().CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
			return false;
		}

	}
	
	public class CompareByTitleAZ implements Comparator<NewsItem> {
		   
		@Override
		public int compare(NewsItem lhs, NewsItem rhs) {
			return lhs.getTitle().compareTo(rhs.getTitle());
		}
	}
	
	public class CompareByTitleZA implements Comparator<NewsItem> {
		   
		@Override
		public int compare(NewsItem lhs, NewsItem rhs) {
			return rhs.getTitle().compareTo(lhs.getTitle());
		}
	}
	
	public class CompareByTitleDateAscen implements Comparator<NewsItem> {
		   
		@Override
		public int compare(NewsItem lhs, NewsItem rhs) {
			return lhs.getPubDate().compareTo(rhs.getPubDate());
		}
	}
	
	public class CompareByTitleDateDescen implements Comparator<NewsItem> {
		   
		@Override
		public int compare(NewsItem lhs, NewsItem rhs) {
			return rhs.getPubDate().compareTo(lhs.getPubDate());
		}
	}
	
	public class CompareByCountDescen implements Comparator<NewsItem> {
		   
		@Override
		public int compare(NewsItem lhs, NewsItem rhs) {
			return rhs.getCount() - lhs.getCount();
		}
	}
	
	public List<NewsItem> filterNews(){
		List<NewsItem> dbNews = dm.getNewsItem();
		List<NewsItem> filteredNews = new ArrayList<NewsItem>(newsItems);
		Log.d("debug", "Filtered List Size before filtering: "+filteredNews.size());
		filteredNews.removeAll(dbNews);
		Log.d("debug", "Filtered List Size: "+filteredNews.size());
		return filteredNews;
	}
}