package com.uwish.crawler.crawlers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.uwish.crawler.manager.CrawlerManager;
import com.uwish.crawler.model.Article;
import com.uwish.crawler.statistics.Statistic;
import com.uwish.crawler.statistics.StatisticsFactory;

public final class CrawlerAbola extends BaseCrawler {
	
	private transient static Logger log = LogManager.getLogger(CrawlerAbola.class.getName());
	private final int searchDelay = 60000;
	private final int callDelay = 1000;
	
	public CrawlerAbola() {
		super(CrawlerAbola.class.getName());
	}
	
	@Override
	public void run() {

		Statistic statistic = StatisticsFactory.getInstance().getStatistics(this);
		
		log.info("Starting crawler");
		
		while (active) {
			
			try {
				Document document = Jsoup.connect("http://www.abola.pt/rss/index.aspx").get();
				Elements elements = document.select("item");
				if (elements.isEmpty()) {
					System.out.println("empty list!!!");
				}
				else {
					for (Element element : elements) {
						Thread.sleep(callDelay);
						Article article = new Article();
						article.setLink(element.select("guid").html());
						article.setDate(element.select("pubDate").html());
						Document d = Jsoup.connect(article.getLink()).get();
						statistic.incElement(Statistic.ARTICLES);
						article.setTitle(d.select("#a5g2").text());
						article.setContent(d.select("#noticiatext").text());
						article.setAuthor(d.select("#a5g4 b").first().text());
						if (CrawlerManager.getInstance().insertArticle(article)) {
							statistic.incElement(Statistic.NEW_ARTICLES);
							log.info("Article '{}' sent", article.getTitle());
						} else {
							statistic.incElement(Statistic.CACHED_ARTICLES);
							log.info("Article '{}' already in cache", article.getTitle());
						}
					}
				}
			} catch (IOException e) {
				statistic.incElement(Statistic.ERRORS);
				log.error("IOException while connecting ({})", e.getMessage());
			} catch (InterruptedException e) {
				log.error("InterruptedException while connecting ({})", e.getMessage());
			} finally {
				log.info(statistic);
				statistic.clear();
				try {
					Thread.sleep(searchDelay);
				} catch (InterruptedException e) {
					log.error("InterruptedException while sleeping thread ({})", e.getMessage());
				}
			}
		}
	}

}
