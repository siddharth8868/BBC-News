/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */
package com.example.midterms2015;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	public static final String URL = "URL";
	ListView listView;
	ArrayList <String> linkNames;
	String [] linkValues;
	ArrayAdapter<String> ad;
	Intent intent;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);
        
        DataManager dm = new DataManager(this);
        
        linkNames = new ArrayList <String>();
        linkNames.add("Top Stories");
        linkNames.add("World");
        linkNames.add("UK");
        linkNames.add("Business");
        linkNames.add("Politics");
        linkNames.add("Health");
        linkNames.add("Education & Family");
        linkNames.add("Science & Environment");
        linkNames.add("Technology");
        linkNames.add("Entrainment & Arts");
        linkNames.add("My History");
        linkValues = new String [] {"http://feeds.bbci.co.uk/news/rss.xml",
					        		"http://feeds.bbci.co.uk/news/world/rss.xml", 
					        		"http://feeds.bbci.co.uk/news/uk/rss.xml", 
					        		"http://feeds.bbci.co.uk/news/business/rss.xml", 
					        		"http://feeds.bbci.co.uk/news/politics/rss.xml",
					        		"http://feeds.bbci.co.uk/news/health/rss.xml", 
					        		"http://feeds.bbci.co.uk/news/education/rss.xml", 
					        		"http://feeds.bbci.co.uk/news/science_and_environment/rss.xml", 
					        		"http://feeds.bbci.co.uk/news/technology/rss.xml", 
					        		"http://feeds.bbci.co.uk/news/entertainment_and_arts/rss.xml",
					        		""};
        
		ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,linkNames);
		ad.setNotifyOnChange(true);
        
		//ad.addAll(linkNames);
		listView.setAdapter(ad);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent(getApplicationContext(), NewsActivity.class);
				intent.putExtra(URL, linkValues[position]);
				startActivity(intent);
				
			}
		});
    }
}