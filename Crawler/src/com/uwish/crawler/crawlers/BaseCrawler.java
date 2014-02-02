package com.uwish.crawler.crawlers;

public abstract class BaseCrawler extends Thread {
	
	protected boolean active = false;
	
	public void startThread() {
		active = true;
		start();
	}
	
	public void stopThread() {
		active = false;
		interrupt();
	}

}
