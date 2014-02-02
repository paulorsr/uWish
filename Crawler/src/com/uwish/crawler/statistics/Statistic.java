package com.uwish.crawler.statistics;

import java.util.HashMap;
import java.util.Map;

public final class Statistic {
	
	public static String ARTICLES = "numArticles";
	public static String NEW_ARTICLES = "numNewArticles";
	public static String CACHED_ARTICLES = "numCachedArticles";
	public static String ERRORS = "numErrors";
	private Map<String, Integer> values;
	
	public Statistic(){
		values = new HashMap<String, Integer>();
	}
	
	public void incElement(final String key) {
		values.put(key, values.get(key) == null ? 1 : values.get(key) + 1);
	}
	
	public void clear() {
		values.clear();
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
		.append("Found ")
		.append(values.get(ARTICLES) == null ? 0 : values.get(ARTICLES))
		.append(" article(s) (")
		.append(values.get(NEW_ARTICLES) == null ? 0 : values.get(NEW_ARTICLES))
		.append(" new, ")
		.append(values.get(CACHED_ARTICLES) == null ? 0 : values.get(CACHED_ARTICLES))
		.append(" cached) and ")
		.append(values.get(ERRORS) == null ? 0 : values.get(ERRORS))
		.append(" error(s).")
		.toString();
	}

}
