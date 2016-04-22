/*
 * Assignment #7
 * Lawrence Ismail, Siddhartha Kondakindi, Jyothisri Kandimalla
 */package com.example.midterms2015;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import android.util.Xml;


public class ParseUtil {
	
	
	static public class SaxParser extends DefaultHandler {

		ArrayList<NewsItem> newsItems;
		NewsItem newsItem;
		Map<String, String> thumbnails;
		StringBuilder xmlInnerText;
		boolean firstItemReached = false;

		static public ArrayList<NewsItem> parseUrl(InputStream in)
				throws IOException, SAXException {
			SaxParser parser = new SaxParser();
			Xml.parse(in, Xml.Encoding.UTF_8, parser);
			return parser.getnewsItems();
		}

		private ArrayList<NewsItem> getnewsItems() {
			return newsItems;
		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
			newsItems = new ArrayList<NewsItem>();
			xmlInnerText = new StringBuilder();
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			xmlInnerText.append(ch, start, length);
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			//Log.d("debug", qName);
			if (localName.equals("item")) {
				thumbnails = new HashMap<String, String>();
				newsItem = new NewsItem();
				//Log.d("debug", "News item instantiated...");
				firstItemReached = true;
				
			} else if (localName.equals("thumbnail")){
				thumbnails.put(attributes.getValue("width").toString().trim(), attributes.getValue("url").toString().trim());
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (firstItemReached) {
				if (localName.equals("item")) {
					int maxWidth = 0;
					for (String width : thumbnails.keySet()) {
						if (maxWidth < Integer.parseInt(width)){
							//Log.d("debug", "URL " + width);
							newsItem.setUrl(thumbnails.get(width));
							maxWidth = Integer.parseInt(width);
						}
					}
					newsItems.add(newsItem);
				} else if (localName.equals("title")) {
					newsItem.setTitle(xmlInnerText.toString().trim());

				} else if (localName.equals("description")) {
					newsItem.setDescription(xmlInnerText.toString().trim());

				} else if (localName.equals("link")) {
					newsItem.setLink(xmlInnerText.toString().trim());

				} else if (localName.equals("pubDate")) {
					newsItem.setPubDate(new Date(xmlInnerText.toString().trim()));

				}
			}
			
			xmlInnerText.setLength(0);
		}

	}
}