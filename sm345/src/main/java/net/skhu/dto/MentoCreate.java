package net.skhu.dto;

import org.springframework.web.multipart.MultipartFile;

public class MentoCreate {
	
	MultipartFile picture;
	MultipartFile file;
	Mentoroom mentoroom;
	
	public MultipartFile getPicture() {
		return picture;
	}
	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public Mentoroom getMentoroom() {
		return mentoroom;
	}
	public void setMentoroom(Mentoroom mentoroom) {
		this.mentoroom = mentoroom;
	}
	
	
	
	
}
