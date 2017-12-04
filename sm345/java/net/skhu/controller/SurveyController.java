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
		@RequestMapping(value="surveySQ/insert" , method = RequestMethod.POST)
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
		public void surveySC_insert(Model model, HttpServletRequest request, @RequestBody String[] a,  @PathVariable("u_id") int  uid, @PathVariable("u_id") int  qid) {
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
			List<SurveySubjectQuestion> sq = surveysqMapper.findByYear(surveySQ);
			for(int i = 0; i< sq.size(); i++) {
				int id = sq.get(i).getId();
				SurveySubjectContent sc = new SurveySubjectContent();
				sc.setQuestion_id(id);
				sc.setSurvey_answer(a[i]);
				surveyscMapper.insert(sc);						
			}
			surveyscMapper.SurveyCheck(uid);
		}
		
		
		//설문 결과
		/*@RequestMapping("survey/result")
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
			int a1 = surveyOb.getObject_answer1();
			int b1 = surveyOb.getObject_answer2();
			int c1 = surveyOb.getObject_answer3();
			int d1 = surveyOb.getObject_answer4();
			int e1 = surveyOb.getObject_answer5();
			int sum = a1+b1+c1+d1+e1;
			int a2 = (int)((double)a1/(double)sum*100);
			int b2 = (int)((double)b1/(double)sum*100);
			int c2 = (int)((double)c1/(double)sum*100);
			int d2 = (int)((double)d1/(double)sum*100);
			int e2 = (int)((double)e1/(double)sum*100);
			surveyOb.setSurvey_year(y);; 
			surveyOb.setSurvey_semester(semester);
			surveyOb.setObject_answer1(a2);
			surveyOb.setObject_answer2(b2);
			surveyOb.setObject_answer3(c2);
			surveyOb.setObject_answer4(d2);
			surveyOb.setObject_answer5(e2);
			surveyobMapper.update(surveyOb);	
			return surveyobMapper.findByYear(surveyOb);
		}*/
			
		//객관식 설문 결과
      @RequestMapping("survey/object/result")
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
         List<SurveyObject> so = surveyobMapper.findObjectSurvey(surveyOb);
         for(int i=0; i<so.size(); i++){
            int all = so.get(i).getObject_answer1()+so.get(i).getObject_answer2()+so.get(i).getObject_answer3()+so.get(i).getObject_answer4()+so.get(i).getObject_answer5();
            int a1 = (int)((so.get(i).getObject_answer1()/all)*100);
            int a2 = (int)((so.get(i).getObject_answer2()/all)*100);
            int a3 = (int)((so.get(i).getObject_answer3()/all)*100);
            int a4 = (int)((so.get(i).getObject_answer4()/all)*100);
            int a5 = (int)((so.get(i).getObject_answer5()/all)*100);
            
            System.out.println((double)so.get(i).getObject_answer1()/all);
            
            so.get(i).setObject_answer1(a1);
            so.get(i).setObject_answer2(a2);
            so.get(i).setObject_answer3(a3);
            so.get(i).setObject_answer4(a4);
            so.get(i).setObject_answer5(a5);   
            
         }
         
         return so;
      }
		
}

