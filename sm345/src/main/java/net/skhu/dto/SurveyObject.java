package net.skhu.dto;

public class SurveyObject {
	int id;
	int question_id;
	String object_question;
	int object_answer1;
	int object_answer2;
	int object_answer3;
	int object_answer4;
	int object_answer5;
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
	public String getObject_question() {
		return object_question;
	}
	public void setObject_question(String object_question) {
		this.object_question = object_question;
	}
	public int getObject_answer1() {
		return object_answer1;
	}
	public void setObject_answer1(int object_answer1) {
		this.object_answer1 = object_answer1;
	}
	public int getObject_answer2() {
		return object_answer2;
	}
	public void setObject_answer2(int object_answer2) {
		this.object_answer2 = object_answer2;
	}
	public int getObject_answer3() {
		return object_answer3;
	}
	public void setObject_answer3(int object_answer3) {
		this.object_answer3 = object_answer3;
	}
	public int getObject_answer4() {
		return object_answer4;
	}
	public void setObject_answer4(int object_answer4) {
		this.object_answer4 = object_answer4;
	}
	public int getObject_answer5() {
		return object_answer5;
	}
	public void setObject_answer5(int object_answer5) {
		this.object_answer5 = object_answer5;
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
