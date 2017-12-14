package net.skhu.dto;

public class User {

	int user_id;
	String user_password;
	int user_auth;
	String user_name;
	int Login_record;

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public int getUser_auth() {
		return user_auth;
	}
	public void setUser_auth(int user_auth) {
		this.user_auth = user_auth;
	}
	public int getLogin_record() {
		return Login_record;
	}
	public void setLogin_record(int login_record) {
		Login_record = login_record;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


}
