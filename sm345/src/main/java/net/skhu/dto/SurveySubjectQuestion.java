package net.skhu.dto;

public class SurveySubjectQuestion {
	
	int id;
	String subject_question1;
	String subject_question2;
	int survey_year;
	int survey_semester;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject_question1() {
		return subject_question1;
	}
	public void setSubject_question1(String subject_question1) {
		this.subject_question1 = subject_question1;
	}
	public String getSubject_question2() {
		return subject_question2;
	}
	public void setSubject_question2(String subject_question2) {
		this.subject_question2 = subject_question2;
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
