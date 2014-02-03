package com.uwish.crawler.crawlers;

public abstract class BaseCrawler extends Thread {
	
	protected boolean active;
	protected String name;
	
	public BaseCrawler(final String name) {
		active = false;
		this.name = name;
	}
	
	public void startThread() {
		active = true;
		start();
	}
	
	public void stopThread() {
		active = false;
		interrupt();
	}
	
	public String getCrawlerName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseCrawler other = (BaseCrawler) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
