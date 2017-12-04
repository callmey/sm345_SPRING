package net.skhu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.SurveyObject;
import net.skhu.dto.SurveySubjectContent;
import net.skhu.dto.SurveySubjectQuestion;
import net.skhu.dto.User;
import net.skhu.mapper.SurveyObMapper;
import net.skhu.mapper.SurveySCMapper;
import net.skhu.mapper.SurveySQMapper;


@RestController
@RequestMapping("api/")
public class SurveyController {
	
	@Autowired SurveyObMapper surveyobMapper;
	@Autowired SurveySCMapper surveyscMapper;
	@Autowired SurveySQMapper surveysqMapper;
	
	
	// 객관식설문항목 등록
	@RequestMapping(value="surveyOB/insert" , method = RequestMethod.POST)
	public String surveyOQ_insert(Model model, HttpServletRequest request, @RequestBody String[] q) {
		Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String date = sdf.format(d); 
	    String year = date.substring(0,4);
	    String month = date.substring(5,7);
	    int y = Integer.parseInt(year);
	    int m = Integer.parseInt(month);
		int semester=0;
		if(m>=3 && m<=7)
			semester=1;
		if(m>=9 && m<=12)
			semester=2;
		
	    for(int i = 0; i < q.length; i++) {
	    	SurveyObject surveyOb = new SurveyObject();
	    	surveyOb.setQuestion_id(i+1);
	    	surveyOb.setSurvey_year(y);
	    	surveyOb.setSurvey_semester(semester);
	    	surveyOb.setObject_question(q[i]);
	    	surveyobMapper.insert(surveyOb);
	    }
		return "객관식설문항목이 등록되었습니다.";
	}
	
	// 주관식설문항목 등록
		@RequestMapping(value="surveySQ /insert" , method = RequestMethod.POST)
		public String surveySQ_insert(Model model, HttpServletRequest request, @RequestBody String[] SQ) {
			Date d = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String date = sdf.format(d);
		    String year = date.substring(0,4);
		    String month = date.substring(5,7);
		    int y = Integer.parseInt(year);
		    int m = Integer.parseInt(month);
			int semester=0;
			if(m>=3 && m<=7)
				semester=1;
			if(m>=9 && m<=12)
				semester=2;
			
			for(int i = 0; i < SQ.length; i++) {
		    	SurveySubjectQuestion surveySQ = new SurveySubjectQuestion();
		    	surveySQ.setQuestion_id(i+1);
		    	surveySQ.setSurvey_year(y);
		    	surveySQ.setSurvey_semester(semester);
		    	surveySQ.setSubject_question(SQ[i]);
		    	surveysqMapper.insert(surveySQ);
		    }
			return "주관식설문항목이 등록되었습니다.";
		}
		
		//객관식 설문항목 목록
		@RequestMapping("surveyOB/list")
	    public List<SurveyObject> surveyOb_list (Model model, HttpServletRequest request) {
			Date d = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String date = sdf.format(d);
		    String year = date.substring(0,4);
		    String month = date.substring(5,7);
		    int y = Integer.parseInt(year);
		    int m = Integer.parseInt(month);
			int semester=0;
			if(m>=3 && m<=7)
				semester=1;
			if(m>=9 && m<=12)
				semester=2;
			SurveyObject surveyOb = new SurveyObject();
			surveyOb.setSurvey_year(y);
			surveyOb.setSurvey_semester(semester);
			return surveyobMapper.findByYear(surveyOb);
	    }
		
		//주관식 설문항목 목록
		@RequestMapping("surveySQ/list")
	    public List<SurveySubjectQuestion> surveySQ_list (Model model, HttpServletRequest request) {
			Date d = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String date = sdf.format(d);
		    String year = date.substring(0,4);
		    String month = date.substring(5,7);
		    int y = Integer.parseInt(year);
		    int m = Integer.parseInt(month);
			int semester=0;
			if(m>=3 && m<=7)
				semester=1;
			if(m>=9 && m<=12)
				semester=2;
			SurveySubjectQuestion surveySQ = new SurveySubjectQuestion();
			surveySQ.setSurvey_year(y);
			surveySQ.setSurvey_semester(semester);
			return surveysqMapper.findByYear(surveySQ);
		}
		
		//객관식 설문조사 답변 등록
		@RequestMapping(value="surveyOB/insert/{u_id}", method = RequestMethod.POST)
		public void surveyOb_insert(Model model, HttpServletRequest request, @RequestBody int[] a) {
//			
			for(int i=0; i<a.length; i++) {
				if(a[i] == 1)
					surveyobMapper.updateCount(i);
				if(a[i] == 2)
					surveyobMapper.updateCount2(i);
				if(a[i] == 3)
					surveyobMapper.updateCount3(i);
				if(a[i] == 4)
					surveyobMapper.updateCount4(i);
				if(a[i] == 5)
					surveyobMapper.updateCount5(i);
			}
		}
		
		//주관식 설문조사 답변 등록
		@RequestMapping(value="surveySC/insert/{u_id}", method = RequestMethod.POST)
		public void surveySC_insert(Model model, HttpServletRequest request, @RequestBody String[] a,  @PathVariable("u_id") int  uid) {
			for(int i=0; i<a.length; i++ ) {
				SurveySubjectContent surveySC= new SurveySubjectContent();
				surveySC.setQuestion_id(i+1);
				surveySC.setSurvey_answer(a[i]);
				surveyscMapper.insert(surveySC);
			}
			surveyscMapper.SurveyCheck(uid);
		}
		
		/*
		//설문 결과
		@RequestMapping("survey/result")
	    public List<SurveyObject> survey_result(Model model, HttpServletRequest request) {
			Date d = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String date = sdf.format(d);
		    String year = date.substring(0,4);
		    String month = date.substring(5,7);
		    int y = Integer.parseInt(year);
		    int m = Integer.parseInt(month);
			int semester=0;
			if(m>=3 && m<=7)
				semester=1;
			if(m>=9 && m<=12)
				semester=2;
			SurveyObject surveyOb = new SurveyObject();
			surveyOb.setSurvey_year(y);
			surveyOb.setSurvey_semester(semester);
			int sum = surveyobMapper.findSum(surveyOb);
			surveyOb.setObject_answer1((surveyOb.getObject_answer1()/sum)*100);
			surveyOb.setObject_answer2((surveyOb.getObject_answer2()/sum)*100);
			surveyOb.setObject_answer3((surveyOb.getObject_answer3()/sum)*100);
			surveyOb.setObject_answer4((surveyOb.getObject_answer4()/sum)*100);
			surveyOb.setObject_answer5((surveyOb.getObject_answer5()/sum)*100);
			surveyobMapper.insert(surveyOb);	
			return surveyobMapper.findByYear(surveyOb);
		}
		*/
}

