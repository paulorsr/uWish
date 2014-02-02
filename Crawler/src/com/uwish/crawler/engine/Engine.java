package com.uwish.crawler.engine;

import com.uwish.crawler.crawlers.CrawlerAbola;
import com.uwish.crawler.manager.CrawlerManager;

public final class Engine {
	
	public Engine() {
		CrawlerManager.getInstance().insertCrawler(new CrawlerAbola());
		startEngine();
	}
	
	public void startEngine() {
		CrawlerManager.getInstance().startCrawlers();
	}
	
	public void stopEngine() {
		CrawlerManager.getInstance().stopCrawlers();
	}
	
}
