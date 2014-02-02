package com.uwish.crawler.crawlers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.uwish.crawler.manager.CrawlerManager;
import com.uwish.crawler.model.Article;

public final class CrawlerAbola extends BaseCrawler {
	
	private final int searchDelay = 60000;
	private final int callDelay = 1000;
	
	@Override
	public void run() {
		
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
						article.setTitle(d.select("#a5g2").text());
						article.setContent(d.select("#noticiatext").text());
						article.setAuthor(d.select("#a5g4 b").first().text());
						if (CrawlerManager.getInstance().insertArticle(article)) {
							System.out.println("Article '" + article.getTitle() + "' saved!");
						} else {
							System.out.println("Article already saved!");
						}
					}
				}
				Thread.sleep(searchDelay);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
