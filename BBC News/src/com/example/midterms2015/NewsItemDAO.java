package com.example.midterms2015;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */public class NewsItemDAO {
	private SQLiteDatabase db;

	public NewsItemDAO(SQLiteDatabase db) {
		super();
		this.db = db;
	}

	public String save(NewsItem newsItem) {
		ContentValues values = new ContentValues();
		values.put(NewsItemTable.NEWSITEM_LINK_ID, newsItem.getLink());
		values.put(NewsItemTable.NEWS_TITLE, newsItem.getTitle());
		values.put(NewsItemTable.NEWS_DESC, newsItem.getDescription());
		values.put(NewsItemTable.NEWS_URL, newsItem.getUrl());
		values.put(NewsItemTable.NEWS_DT, newsItem.getPubDate().toString());
		values.put(NewsItemTable.COUNT, newsItem.getCount());
		db.insert(NewsItemTable.TABLE_NM, null, values);
		return newsItem.getLink();
	}

	public boolean update(NewsItem newsItem) {
		ContentValues values = new ContentValues();
		values.put(NewsItemTable.NEWSITEM_LINK_ID, newsItem.getLink());
		values.put(NewsItemTable.NEWS_TITLE, newsItem.getTitle());
		values.put(NewsItemTable.NEWS_DESC, newsItem.getDescription());
		values.put(NewsItemTable.NEWS_URL, newsItem.getUrl());
		values.put(NewsItemTable.NEWS_DT, newsItem.getPubDate().toString());
		values.put(NewsItemTable.COUNT, newsItem.getCount());
		return db
				.update(NewsItemTable.TABLE_NM,
						values,
						NewsItemTable.NEWSITEM_LINK_ID + "=?", new String[]{newsItem.getLink()}) > 0;
	}

	public boolean delete(NewsItem newsItem) {
		return db.delete(NewsItemTable.TABLE_NM, NewsItemTable.NEWSITEM_LINK_ID + "=?", new String[]{newsItem.getLink()}) > 0;
	}
	
	public boolean deleteAll() {
		return db.delete(NewsItemTable.TABLE_NM, null, null) > 0;
	}

	public NewsItem get(String link) {
		NewsItem newsItem = null;
		Cursor c = db.query(true, NewsItemTable.TABLE_NM, new String[] {
				NewsItemTable.NEWSITEM_LINK_ID, NewsItemTable.NEWS_TITLE,
				NewsItemTable.NEWS_DESC, NewsItemTable.NEWS_URL,
				NewsItemTable.NEWS_DT, NewsItemTable.COUNT}, NewsItemTable.NEWSITEM_LINK_ID + "=?",new String[]{link}, null, null, null, null);
		if (c != null) {
			if (c.moveToFirst())
				newsItem = this.buildNoteFromCursor(c);
		}
		if (!c.isClosed()) {
			c.close();
		}
		return newsItem;
	}

	public List<NewsItem> getAll() {
		List<NewsItem> list = new ArrayList<NewsItem>();
		Cursor c = db.query(NewsItemTable.TABLE_NM, new String[] {
				NewsItemTable.NEWSITEM_LINK_ID, NewsItemTable.NEWS_TITLE,
				NewsItemTable.NEWS_DESC, NewsItemTable.NEWS_URL,
				NewsItemTable.NEWS_DT, NewsItemTable.COUNT }, null, null, null, null, null);
		if (c != null) {
			if (c.moveToFirst()){
				do {
					NewsItem newsItem = this.buildNoteFromCursor(c);
					if (newsItem != null) {
						list.add(newsItem);
					}
				} while (c.moveToNext());
			}
			if (!c.isClosed()) {
				c.close();
			}
		}
		return list;
	}

	private NewsItem buildNoteFromCursor(Cursor c) {
		NewsItem newsItem = null;
		if (c != null) {
			newsItem = new NewsItem();
			newsItem.setLink(c.getString(0));
			newsItem.setTitle(c.getString(1));
			newsItem.setDescription(c.getString(2));
			newsItem.setUrl(c.getString(3));
			newsItem.setPubDate(new Date(c.getString(4)));
			newsItem.setCount(c.getInt(5));
		}
		return newsItem;
	}
}
