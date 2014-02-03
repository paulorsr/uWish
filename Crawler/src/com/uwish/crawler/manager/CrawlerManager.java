package com.uwish.crawler.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.uwish.crawler.crawlers.BaseCrawler;
import com.uwish.crawler.model.Article;

public final class CrawlerManager {

	private static CrawlerManager instance;
	private List<BaseCrawler> crawlers;
	private Set<Article> articles;

	private CrawlerManager() {
		crawlers = new ArrayList<BaseCrawler>();
		articles = new HashSet<Article>();
	}

	public static CrawlerManager getInstance() {
		if (instance == null) {
			instance = new CrawlerManager();
		}
		return instance;
	}

	public synchronized boolean insertArticle(final Article article) {
		return articles.add(article);
	}

	public synchronized void insertCrawler(final BaseCrawler crawler) {
		crawlers.add(crawler);
	}

	public void startCrawlers() {
		for (BaseCrawler crawler : crawlers) {
			crawler.startThread();
		}
	}

	public void stopCrawlers() {
		for (BaseCrawler crawler : crawlers) {
			crawler.stopThread();
		}
		for (BaseCrawler crawler : crawlers) {
			try {
				crawler.join();
			} catch (InterruptedException e) {
				if (crawler.isAlive()) {
					crawler.interrupt();
					try {
						crawler.join();
					} catch (InterruptedException e1) {
						System.out.println("Not possible to stop crawler!");
					}
				}
			}
		}
	}

}
