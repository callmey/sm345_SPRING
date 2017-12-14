package net.skhu.dto;

public class User {

	int user_id;
	String user_password;
	int user_auth;
	String user_name;
	int Login_record;
	String user_major;
	String user_minor;
	String user_email;
	String user_identity;
	String user_phone;



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
	public String getUser_major() {
		return user_major;
	}
	public void setUser_major(String user_major) {
		this.user_major = user_major;
	}
	public String getUser_minor() {
		return user_minor;
	}
	public void setUser_minor(String user_minor) {
		this.user_minor = user_minor;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_identity() {
		return user_identity;
	}
	public void setUser_identity(String user_identity) {
		this.user_identity = user_identity;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
}
