package net.skhu.dto;

import java.sql.Timestamp;

public class Article {

	int id;
	int board_id;
	String article_title;
	String article_content;
	int article_writer;
	int article_hit;
	int article_delete;
	Timestamp timestamp;
	String user_name; //조인

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public String getArticle_content() {
		return article_content;
	}
	public void setArticle_content(String article_content) {
		this.article_content = article_content;
	}
	public int getArticle_writer() {
		return article_writer;
	}
	public void setArticle_writer(int article_writer) {
		this.article_writer = article_writer;
	}
	public int getArticle_hit() {
		return article_hit;
	}
	public void setArticle_hit(int article_hit) {
		this.article_hit = article_hit;
	}
	public int getArticle_delete() {
		return article_delete;
	}
	public void setArticle_delete(int article_delete) {
		this.article_delete = article_delete;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



}
