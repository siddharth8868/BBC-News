/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */package com.example.midterms2015;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	static final String DATABASE_NAME = "newsItem.db";
	static final int DATABASE_VERSION = 1;
	
	DatabaseHelper(Context mContext){
	 	 super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("debug", "databasehelper cons");

	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("debug", "databasehelper oncreate");
		NewsItemTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		NewsItemTable.onUpgrade(db, oldVersion, newVersion);
	}


}
