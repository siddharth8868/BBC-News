/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */package com.example.midterms2015;

import java.util.Date;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NewsItemTable {
	static final String TABLE_NM = "newsItems";
	static final String NEWSITEM_LINK_ID = "link";
	static final String NEWS_TITLE = "title";
	static final String NEWS_DESC = "description";
	static final String NEWS_URL = "url";
	static final String NEWS_DT = "date";
	static final String COUNT = "count";
	
	static public void onCreate(SQLiteDatabase db){	
		Log.d("debug", "NewsItemTable oncreate");
	 	 StringBuilder sb = new StringBuilder();	 	 	
	 	 sb.append("CREATE TABLE " + NewsItemTable.TABLE_NM + " (");
	 	 sb.append(NewsItemTable.NEWSITEM_LINK_ID + " text primary key, ");
	 	 sb.append(NewsItemTable.NEWS_TITLE + " text, ");
	 	 sb.append(NewsItemTable.NEWS_DESC + " text, ");
	 	 sb.append(NewsItemTable.NEWS_URL + " text, ");
	 	 sb.append(NewsItemTable.NEWS_DT + " text, ");
	 	 sb.append(NewsItemTable.COUNT + " integer");
	 	 sb.append(");");	 	 	
	 	 try{
			 db.execSQL(sb.toString());
	 	 } catch (SQLException e){	 

	 		Log.d("debug", "NewsItemTable error");
			 e.printStackTrace();
	 	 }
}
	 	
static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
	 	 db.execSQL("DROP TABLE IF EXISTS " + NewsItemTable.TABLE_NM);
	 	NewsItemTable.onCreate(db);
}	
}
