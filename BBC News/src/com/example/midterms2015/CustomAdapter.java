/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */package com.example.midterms2015;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<NewsItem>{

	ArrayList<NewsItem> newsItems;
	Context context;
	
	public CustomAdapter(Context context,ArrayList<NewsItem> newsItems) {
		super(context, R.layout.custom_layout, newsItems);
		this.newsItems = newsItems;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.custom_layout, parent,false);
		holder = new ViewHolder();
		holder.image = (ImageView) convertView.findViewById(R.id.custom_imageView1);
		holder.text = (TextView) convertView.findViewById(R.id.custom_textView1);
		convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		ImageView image = holder.image;
		TextView text = holder.text;
		Picasso.with(context).load(newsItems.get(position).getUrl()).into(image);
		text.setText(newsItems.get(position).getTitle());
		return convertView;
		}
	
		static class ViewHolder{
		ImageView image;
		TextView text;
		}
		
}
