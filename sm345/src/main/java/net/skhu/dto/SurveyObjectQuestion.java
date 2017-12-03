package net.skhu.dto;

public class SurveyObjectQuestion {
	
	int id;
	String object_question1; 
	String object_question2; 
	String object_question3; 
	String object_question4; 
	String object_question5; 
	int survey_year;
	int survey_semester;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getObject_question1() {
		return object_question1;
	}
	public void setObject_question1(String object_question1) {
		this.object_question1 = object_question1;
	}
	public String getObject_question2() {
		return object_question2;
	}
	public void setObject_question2(String object_question2) {
		this.object_question2 = object_question2;
	}
	public String getObject_question3() {
		return object_question3;
	}
	public void setObject_question3(String object_question3) {
		this.object_question3 = object_question3;
	}
	public String getObject_question4() {
		return object_question4;
	}
	public void setObject_question4(String object_question4) {
		this.object_question4 = object_question4;
	}
	public String getObject_question5() {
		return object_question5;
	}
	public void setObject_question5(String object_question5) {
		this.object_question5 = object_question5;
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
