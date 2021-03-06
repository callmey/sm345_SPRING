package net.skhu.dto;

import java.sql.Timestamp;

public class Message {

	int id;
	int to_id;
	int from_id;
	String message_title;
	String message_content;
	Timestamp timestamp;
	int all_message;
	int read_check;
	String user_name; //조인

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTo_id() {
		return to_id;
	}
	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}
	public int getFrom_id() {
		return from_id;
	}
	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}
	public String getMessage_title() {
		return message_title;
	}
	public void setMessage_title(String message_title) {
		this.message_title = message_title;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
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
	public int getAll_message() {
		return all_message;
	}
	public void setAll_message(int all_message) {
		this.all_message = all_message;
	}
	public int getRead_check() {
		return read_check;
	}
	public void setRead_check(int read_check) {
		this.read_check = read_check;
	}

}
