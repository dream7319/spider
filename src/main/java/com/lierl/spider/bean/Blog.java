package com.lierl.spider.bean;

/**
 * @author lierl
 * @create 2017-08-31 10:57
 **/
public class Blog {
	String title;
	String author;
	int readNum;
	int voteNum;
	String publishTime;
	int collecotrNum;
	int words;
	String tag;
	String address;
	int fans;
	int blogNum;
	int blogWords;
	String url;

	int comment;

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getReadNum() {
		return readNum;
	}

	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}

	public int getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(int voteNum) {
		this.voteNum = voteNum;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public int getCollecotrNum() {
		return collecotrNum;
	}

	public void setCollecotrNum(int collecotrNum) {
		this.collecotrNum = collecotrNum;
	}

	public int getWords() {
		return words;
	}

	public void setWords(int words) {
		this.words = words;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

	public int getBlogNum() {
		return blogNum;
	}

	public void setBlogNum(int blogNum) {
		this.blogNum = blogNum;
	}

	public int getBlogWords() {
		return blogWords;
	}

	public void setBlogWords(int blogWords) {
		this.blogWords = blogWords;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Blog{" +
			   "title='" + title + '\'' +
			   ", author='" + author + '\'' +
			   ", readNum=" + readNum +
			   ", voteNum=" + voteNum +
			   ", publishTime='" + publishTime + '\'' +
			   ", collecotrNum=" + collecotrNum +
			   ", words=" + words +
			   ", tag='" + tag + '\'' +
			   ", address='" + address + '\'' +
			   ", fans=" + fans +
			   ", blogNum=" + blogNum +
			   ", blogWords=" + blogWords +
			   ", url='" + url + '\'' +
			   ", comment=" + comment +
			   '}';
	}
}
