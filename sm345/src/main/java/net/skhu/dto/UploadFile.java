package net.skhu.dto;

import java.sql.Timestamp;

public class UploadFile {

	int id;
	String file_name; //중복처리방지를 위한 파일 이름
	long file_size;
	String file_path;
	byte[] file_data;
	int file_kind;
	int mentoroom_id;
	String file_originalname;
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
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public byte[] getFile_data() {
		return file_data;
	}
	public void setFile_data(byte[] file_data) {
		this.file_data = file_data;
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
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getFile_originalname() {
		return file_originalname;
	}
	public void setFile_originalname(String file_originalname) {
		this.file_originalname = file_originalname;
	}



}
