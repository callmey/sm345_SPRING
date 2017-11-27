package net.skhu.dto;

import java.sql.Timestamp;

public class UploadFile {

	int id;
	String file_name; //중복처리방지를 위한 파일 이름
	byte[] file_content;
	String file_path;
	int file_kind;
	int mentoroom_id;
	int file_state;
	String file_type;
	Timestamp timestamp;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public byte[] getFile_content() {
		return file_content;
	}
	public void setFile_content(byte[] file_content) {
		this.file_content = file_content;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public int getFile_kind() {
		return file_kind;
	}
	public void setFile_kind(int file_kind) {
		this.file_kind = file_kind;
	}
	public int getMentoroom_id() {
		return mentoroom_id;
	}
	public void setMentoroom_id(int mentoroom_id) {
		this.mentoroom_id = mentoroom_id;
	}
	public int getFile_state() {
		return file_state;
	}
	public void setFile_state(int file_state) {
		this.file_state = file_state;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}



}
