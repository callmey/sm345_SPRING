package net.skhu.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.MentoRoomInfo;
import net.skhu.dto.Mentoroom;
import net.skhu.dto.ReportDate;
import net.skhu.dto.Student;
import net.skhu.dto.UploadFile;
import net.skhu.dto.User;
import net.skhu.mapper.ArticleMapper;
import net.skhu.mapper.MentoRoomInfoMapper;
import net.skhu.mapper.MentoroomMapper;
import net.skhu.mapper.ReportDateMapper;
import net.skhu.mapper.StudentMapper;
import net.skhu.mapper.UploadFileMapper;
//import net.skhu.mapper.ReportDateMapper;
import net.skhu.mapper.UserMapper;

@RestController
@RequestMapping("api/")
public class AdminController {

	@Autowired ArticleMapper articleMapper;
	@Autowired UserMapper userMapper;
	@Autowired MentoroomMapper mentoroomMapper;
	@Autowired MentoRoomInfoMapper mentoroominfoMapper;
	@Autowired UploadFileMapper uploadFileMapper;
	@Autowired ReportDateMapper reportdateMapper;
	@Autowired StudentMapper studentMapper;


		//멘토방 목록 (관리자)
		@RequestMapping("admin/mentoroom/{year}")
		public List<Mentoroom> mentoroomList_admn(HttpServletRequest request, Model model, @PathVariable("year") int year) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
			if(year == 0)
				return mentoroomMapper.findAll();
			else{
			String y = String.valueOf(year);
			int yyyy = Integer.parseInt(y.substring(0,4));
			int s =  Integer.parseInt(y.substring(4,5));
			Mentoroom mentoroom = new Mentoroom();
			mentoroom.setTeam_year(yyyy);
			mentoroom.setTeam_semester(s);
			return mentoroomMapper.findAllByYear(mentoroom);
			}
		}

		//보고서 목록 (관리자)
		@RequestMapping("admin/report/{year}")
		public List<UploadFile> reportList_admn(HttpServletRequest request, Model model, @PathVariable("year") int year) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
			if(year == 0)
				return uploadFileMapper.findAll();
			else{
			String y = String.valueOf(year);
			int yyyy = Integer.parseInt(y.substring(0,4));
			int s =  Integer.parseInt(y.substring(4,5));
			Mentoroom mentoroom = new Mentoroom();
			mentoroom.setTeam_year(yyyy);
			mentoroom.setTeam_semester(s);
			return uploadFileMapper.findAllByYear(mentoroom);
			}
		}

		//보고서 삭제 (관리자)
	    @RequestMapping(value="admin/filedelete/{f_id}")
	    public String filedelete(@PathVariable("f_id") int f_id, HttpServletRequest request, Model model ) throws UnsupportedEncodingException {
	    	uploadFileMapper.delete(f_id);
	    	return "파일이 삭제되었습니다";
	   }

		//멘토방 승인
		@RequestMapping("admin/mentoroom/{r_id}/{m_id}/confirm")
		public void mentoroom_confirm(HttpServletRequest request, Model model, @PathVariable("r_id") int r_id, @PathVariable("m_id") int m_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
			mentoroomMapper.updateTeamconfirm(r_id); //team_confirm 1로 업데이트
			userMapper.updateUserauth(m_id); //user_auth 1로 업데이트(멘토승격)
		}

		//멘토방 승인거절
		@RequestMapping("admin/mentoroom/{r_id}/reject")
		public void mentoroom_reject(HttpServletRequest request, Model model, @PathVariable("r_id") int r_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
			mentoroomMapper.deleteMentoroom(r_id);
		}

		//멘토방 폐설
	    @RequestMapping(value="admin/mentoroom/{r_id}/close")
	    public void mentoroom_close(Model model, HttpServletRequest request, @PathVariable("r_id") int r_id) {
	        mentoroomMapper.updateClose(r_id);
	    }

		//멘토방 설정 데이터
	    @RequestMapping("admin/room_info")
	  	public MentoRoomInfo mentoRoomInfo(Model model, HttpServletRequest request) {
	        return mentoroominfoMapper.findMentoRoomInfo();
	  	}

	  	// 멘토방 설정 수정
	  	@RequestMapping(value="admin/room_info/edit", method = RequestMethod.POST)
	    public void mentoRoomInfo_edit(@RequestBody MentoRoomInfo mentoroominfo, Model model, HttpServletRequest request ) {
	        mentoroominfoMapper.update(mentoroominfo);
	    }
	  	
	 // 보고서 제출 날짜 삽입
        @RequestMapping(value="admin/report_date/create", method = RequestMethod.POST)
       public void reportdate_create(@RequestBody ReportDate[] reportdate, Model model, HttpServletRequest request ) {
         reportdateMapper.delete();
           for(int i=0; i<reportdate.length; i++){
              reportdateMapper.insert(reportdate[i]);
           }
       }

        //보고서 제출 날짜 데이터
       @RequestMapping("admin/report_date")
        public List<ReportDate> reportdate(Model model, HttpServletRequest request) {
          return reportdateMapper.findAll();
        }
       
	    //답변 완료
	    @RequestMapping(value="admin/{a_id}/answer")
	    public void answer(Model model, HttpServletRequest request, @PathVariable("a_id") int a_id) {
	        articleMapper.updateAnswer(a_id);
	    }

	    //사용자 목록
	    @RequestMapping("admin/user/{auth}")
	    public List<User> user_list(Model model, HttpServletRequest request, @PathVariable("auth") int auth) {
	        if(auth == 4) {//보고서 미제출멘토
	        	int meeting_number = Integer.parseInt(mentoroominfoMapper.findMentoRoomInfo().getMeeting_number());
	        	return userMapper.selectReportNotYet(meeting_number);
	    	}
	        if(auth == 0) //전체 학생 목록
	        	return userMapper.selectStudent();
	    	List<User> list = userMapper.findAll(auth);
	        return list;
	    }

	    //관리자 지정
	    @RequestMapping(value="admin/empower/{u_id}")
	    public void admin_empower(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) {
	        userMapper.updateEmpower(u_id);
	    }

	    //관리자 권한 해제
	    @RequestMapping(value="admin/leave/{u_id}")
	    public void admin_leave(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) {
	        userMapper.updateLeave(u_id);
	    }

	    //보고서 승인
	    @RequestMapping(value="admin/report_confirm/{f_id}")
	    public void report_confirm(Model model, HttpServletRequest request, @PathVariable("f_id") int f_id) {
	        uploadFileMapper.updateConfirm(f_id);
	    }

	    //보고서 반려
	    @RequestMapping(value="admin/report_reject/{f_id}")
	    public void report_reject(Model model, HttpServletRequest request, @PathVariable("f_id") int f_id) {
	        uploadFileMapper.updateReject(f_id);
	    }
	    
	    //신입생 엑셀등록
	    @RequestMapping(value="admin/excel", method = RequestMethod.POST)
	    public void execel(@RequestBody Student[] student, Model model,HttpServletRequest request) {
			for(int i=0; i<student.length; i++) {
				studentMapper.insert(student[i]);
				User user = new User();
				user.setUser_id(student[i].getStudent_id());
				user.setUser_name(student[i].getStudent_name());
				userMapper.insert(user);
			}
			//userMapper.updateAuth();

	    }

	    




}