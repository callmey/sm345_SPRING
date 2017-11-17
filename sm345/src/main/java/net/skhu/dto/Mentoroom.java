package net.skhu.dto;

import java.sql.Timestamp;

public class Mentoroom {
	int mentoroom_id;
	String team_name;
	String team_about;
	String team_theme;
	int team_filekind;
	String team_link;
	int team_year;
	int team_semester;
	int mento_id;
	String mento_name;
	int team_confirm;
	int report_check;

	int file_id;
	String file_name;
	byte[] file_data;
	int file_kind;
	Timestamp timestamp;

	int person_count;

	public int getMentoroom_id() {
		return mentoroom_id;
	}
	public void setMentoroom_id(int mentoroom_id) {
		this.mentoroom_id = mentoroom_id;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getTeam_about() {
		return team_about;
	}
	public void setTeam_about(String team_about) {
		this.team_about = team_about;
	}
	public String getTeam_theme() {
		return team_theme;
	}
	public void setTeam_theme(String team_theme) {
		this.team_theme = team_theme;
	}
	public int getTeam_filekind() {
		return team_filekind;
	}
	public void setTeam_filekind(int team_filekind) {
		this.team_filekind = team_filekind;
	}
	public String getTeam_link() {
		return team_link;
	}
	public void setTeam_link(String team_link) {
		this.team_link = team_link;
	}
	public int getTeam_year() {
		return team_year;
	}
	public void setTeam_year(int team_year) {
		this.team_year = team_year;
	}
	public int getTeam_semester() {
		return team_semester;
	}
	public void setTeam_semester(int team_semester) {
		this.team_semester = team_semester;
	}
	public int getMento_id() {
		return mento_id;
	}
	public void setMento_id(int mento_id) {
		this.mento_id = mento_id;
	}
	public String getMento_name() {
		return mento_name;
	}
	public void setMento_name(String mento_name) {
		this.mento_name = mento_name;
	}
	public int getTeam_confirm() {
		return team_confirm;
	}
	public void setTeam_confirm(int team_confirm) {
		this.team_confirm = team_confirm;
	}
	public int getFile_id() {
		return file_id;
	}
	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
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
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public int getPerson_count() {
		return person_count;
	}
	public void setPerson_count(int person_count) {
		this.person_count = person_count;
	}
	public int getReport_check() {
		return report_check;
	}
	public void setReport_check(int report_check) {
		this.report_check = report_check;
	}

}
