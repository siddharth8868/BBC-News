/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */package com.example.midterms2015;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class NewsItem implements Serializable{
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return this.link.equals(((NewsItem)o).getLink());
	}

	String title, description, link, url;
	Date pubDate;
	int count;
	
	public NewsItem(String title, String description, String link, String url,
			Date pubDate, int count) {
		super();
		this.title = title;
		this.description = description;
		this.link = link;
		this.url = url;
		this.pubDate = pubDate;
		this.count = count;
	}
	
	public NewsItem() {}

	@Override
	public String toString() {
		return this.title;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
