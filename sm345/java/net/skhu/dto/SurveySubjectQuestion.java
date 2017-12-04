package net.skhu.dto;

public class SurveySubjectQuestion {
	
	int id;
	int question_id;
	String subject_question;
	int survey_year;
	int survey_semester;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public String getSubject_question() {
		return subject_question;
	}
	public void setSubject_question(String subject_question) {
		this.subject_question = subject_question;
	}
	public int getSurvey_year() {
		return survey_year;
	}
	public void setSurvey_year(int survey_year) {
		this.survey_year = survey_year;
	}
	public int getSurvey_semester() {
		return survey_semester;
	}
	public void setSurvey_semester(int survey_semester) {
		this.survey_semester = survey_semester;
	}
}
