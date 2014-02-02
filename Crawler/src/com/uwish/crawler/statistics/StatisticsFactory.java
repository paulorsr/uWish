package com.uwish.crawler.statistics;

import java.util.HashMap;
import java.util.Map;

import com.uwish.crawler.crawlers.BaseCrawler;

public final class StatisticsFactory {
	
	private static StatisticsFactory instance;
	private Map<BaseCrawler, Statistic> statistics;
	
	private StatisticsFactory() {
		statistics = new HashMap<BaseCrawler, Statistic>();
	}
	
	public static synchronized StatisticsFactory getInstance() {
		if (instance == null) {
			instance = new StatisticsFactory();
		}
		return instance;
	}
	
	public Statistic getStatistics(final BaseCrawler crawler) {
		if (statistics.containsKey(crawler)) {
			return statistics.get(crawler);
		} else {
			Statistic statistic = new Statistic();
			statistics.put(crawler, statistic);
			return statistic;
		}
	}

}
