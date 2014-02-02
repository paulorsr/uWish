package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public final class Main {

	private List<Article> articles = new ArrayList<Article>();
	
	public Main() {
		
		try {
			Document document = Jsoup.connect("http://www.abola.pt/rss/index.aspx").get();
			Elements elements = document.select("item");
			if (elements.isEmpty()) {
				System.out.println("empty list!!!");
			}
			else {
				for (Element element : elements) {
					Article article = new Article();
					article.setLink(element.select("guid").html());
					article.setDate(element.select("pubDate").html());
					Document d = Jsoup.connect(article.getLink()).get();
					article.setTitle(d.select("#a5g2").text());
					article.setContent(d.select("#noticiatext").text());
					article.setAuthor(d.select("#a5g4 b").first().text());
					articles.add(article);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Article a : articles) {
			System.out.println(a.toString());
		}
		
		
	}
	
	private class Article {
		
		private String title;
		private String description;
		private String link;
		private String content;
		private String author;
		private String date;
		
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
		
		public String getContent() {
			return content;
		}
		
		public void setContent(String content) {
			this.content = content;
		}
		
		
		
		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		@Override
		public String toString() {
			return new StringBuilder()
			.append("Title : " + title)
			.append("\nContent : " + content)
			.append("\nLink : " + link)
			.append("\nAuthor : " + author)
			.append("\nDate : " + date.toString())
			.toString();
		}

	}
	
}
