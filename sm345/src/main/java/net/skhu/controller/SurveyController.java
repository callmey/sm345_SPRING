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
	@RequestMapping(value="surveyOQ/insert" , method = RequestMethod.POST)
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
		@RequestMapping(value="surveySQ/insert" , method = RequestMethod.POST)
		public String surveySQ_insert(Model model, HttpServletRequest request, @RequestBody SurveySubjectQuestion surveySQ) {
			Date d = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String date = sdf.format(d);
		    String year = date.substring(0,4);
		    String month = date.substring(5,7);
		    int y = Integer.parseInt(year);
		    int m = Integer.parseInt(month);
		    surveySQ.setSurvey_year(y);
			int semester=0;
			if(m>=3 && m<=7)
				semester=1;
			if(m>=9 && m<=12)
				semester=2;
			surveySQ.setSurvey_semester(semester);
			surveysqMapper.insert(surveySQ);
			return "설문항목이 등록되었습니다.";
		}
		
		//객관식 설문항목 목록
		/*@RequestMapping("surveyOQ/list/{year}")
	    public List<SurveyObject> surveyOb_list (Model model, HttpServletRequest request, @PathVariable("year") int year) {
			String y = String.valueOf(year);
			int yyyy = Integer.parseInt(y.substring(0,4));
			int s =  Integer.parseInt(y.substring(4,5));
			SurveyObject surveyob = new SurveyObject();
			surveyOb.setSurvey_year(yyyy);
			surveyOb.setSurvey_semester(s);
			return surveyobMapper.findByYear(surveyOb);
	    }
		
		//주관식 설문항목 목록
		@RequestMapping("surveySQ/list/{year}")
	    public List<SurveySubjectQuestion> surveySQ_list (Model model, HttpServletRequest request, @PathVariable("year") int year) {
			String y = String.valueOf(year);
			int yyyy = Integer.parseInt(y.substring(0,4));
			int s =  Integer.parseInt(y.substring(4,5));
			SurveySubjectQuestion surveySQ = new SurveySubjectQuestion();
			surveySQ.setSurvey_year(yyyy);
			surveySQ.setSurvey_semester(s);
			return surveysqMapper.findByYear(surveySQ);
		}
		
		//설문조사 답변 등록
		@RequestMapping(value="survey/{q_id}/insert")
		public String survey_insert(Model model, HttpServletRequest request, @PathVariable("q_id") int qid) {
			surveyocMapper.findById(qid);
			SurveyObjectContent surveyOC = new SurveyObjectContent();
			return"설문조사가 완료되었습니다.";
		} */
}
