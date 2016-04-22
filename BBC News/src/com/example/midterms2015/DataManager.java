package com.example.midterms2015;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */public class DataManager {
	Context mContext;
	DatabaseHelper dbOpenHelper;
	SQLiteDatabase db;
	NewsItemDAO newsItemDAO;

	public DataManager(Context mContext) {
		Log.d("debug", "in datamanager con");

		this.mContext = mContext;
		dbOpenHelper = new DatabaseHelper(mContext);
		db = dbOpenHelper.getWritableDatabase();
		newsItemDAO = new NewsItemDAO(db);
	}

	public void close() {
		db.close();
	}

	public String saveNewsItem(NewsItem newsItem) {
		return newsItemDAO.save(newsItem);
	}
	
	public boolean updateNewsItem(NewsItem newsItem) {
		return newsItemDAO.update(newsItem);
	}
	
	public boolean deleteNewsItem(NewsItem newsItem) {
		return newsItemDAO.delete(newsItem);
	}
	
	public boolean deleteNewsItems() {
		return newsItemDAO.deleteAll();
	}

	public NewsItem getNewsItem(String link) {
		return newsItemDAO.get(link);
	}

	public List<NewsItem> getNewsItem() {
		return newsItemDAO.getAll();
	}
}
