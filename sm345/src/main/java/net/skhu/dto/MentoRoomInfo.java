package net.skhu.dto;

import java.sql.Date;

public class MentoRoomInfo {
	Date mento_start;
	Date mento_end;
	Date menti_start;
	Date menti_end;
	int max_mento;
	int max_menti;
	int meeting_number;
	int meeting_period;
	Date survey_start;
	Date survey_end;
	
	public Date getMento_start() {
		return mento_start;
	}
	public void setMento_start(Date mento_start) {
		this.mento_start = mento_start;
	}
	public Date getMento_end() {
		return mento_end;
	}
	public void setMento_end(Date mento_end) {
		this.mento_end = mento_end;
	}
	public Date getMenti_start() {
		return menti_start;
	}
	public void setMenti_start(Date menti_start) {
		this.menti_start = menti_start;
	}
	public Date getMenti_end() {
		return menti_end;
	}
	public void setMenti_end(Date menti_end) {
		this.menti_end = menti_end;
	}
	public int getMax_mento() {
		return max_mento;
	}
	public void setMax_mento(int max_mento) {
		this.max_mento = max_mento;
	}
	public int getMax_menti() {
		return max_menti;
	}
	public void setMax_menti(int max_menti) {
		this.max_menti = max_menti;
	}
	public int getMeeting_number() {
		return meeting_number;
	}
	public void setMeeting_number(int meeting_number) {
		this.meeting_number = meeting_number;
	}
	public int getMeeting_period() {
		return meeting_period;
	}
	public void setMeeting_period(int meeting_period) {
		this.meeting_period = meeting_period;
	}
	public Date getSurvey_start() {
		return survey_start;
	}
	public void setSurvey_start(Date survey_start) {
		this.survey_start = survey_start;
	}
	public Date getSurvey_end() {
		return survey_end;
	}
	public void setSurvey_end(Date survey_end) {
		this.survey_end = survey_end;
	}
}
