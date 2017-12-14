package net.skhu.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.skhu.dto.Menti;
import net.skhu.dto.Mentoroom;
import net.skhu.dto.UploadFile;
import net.skhu.mapper.MentiMapper;
import net.skhu.mapper.MentoRoomInfoMapper;
import net.skhu.mapper.MentoroomMapper;
import net.skhu.mapper.StudentMapper;
import net.skhu.mapper.UploadFileMapper;
import net.skhu.mapper.UserMapper;

@RestController
@RequestMapping("api/")
public class MentoroomController {

	@Autowired UserMapper userMapper;
	@Autowired MentoroomMapper mentoroomMapper;
	@Autowired StudentMapper studentMapper;
	@Autowired MentiMapper mentiMapper;
	@Autowired UploadFileMapper uploadFileMapper;
	@Autowired MentoRoomInfoMapper mentoroomInfoMapper;

    	//멘토신청
		@RequestMapping(value = "mentoroom/create", method = RequestMethod.POST)
		public int mentoroom_create(@RequestBody Mentoroom mentoroom, Model model, HttpServletRequest request) throws UnsupportedEncodingException {

	   Date d = new Date();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       String date = sdf.format(d);
       String year = date.substring(0,4);
       String month = date.substring(5,7);
       int y = Integer.parseInt(year);
       int m = Integer.parseInt(month);
      mentoroom.setTeam_year(y);
      int semester=0;
      if(m>=3 && m<=7)
         semester=1;
      if(m>=9 && m<=12)
         semester=2;
      mentoroom.setTeam_semester(semester);
      String mento_name = studentMapper.selectStudentname(mentoroom.getMento_id());
      mentoroom.setMento_name(mento_name);
      mentoroomMapper.insert(mentoroom);

      return mentoroom.getMentoroom_id();

		}

		//멘토방 목록
		@RequestMapping("mentoroom")
		public List<Mentoroom> mentoroomList(HttpServletRequest request, Model model) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
			return mentoroomMapper.findAll();
		}

		//각 멘토방 조회
		@RequestMapping("mentoroom/{r_id}")
		public Mentoroom mentoroom(HttpServletRequest request, Model model, @PathVariable("r_id") int r_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
			return mentoroomMapper.findMentoroom(r_id);
		}

		// 멘티신청
		@RequestMapping(value="mentoroom/{mid}/{uid}/menti_join")
		public String menti_join(Model model, HttpServletRequest request, @PathVariable("mid") int mid, @PathVariable("uid") int uid) {
		userMapper.updateMentiauth(uid);
		Menti menti = new Menti();
		menti.setMenti_id(uid);
		menti.setMento_id(mid);
		mentiMapper.insert(menti);
		mentoroomMapper.updatePersoncount1(mid);
		return "멘티신청이 완료되었습니다";
		}

		// 멘티신청취소
		@RequestMapping(value="mentoroom/{mid}/{uid}/menti_cancel")
		public String menti_cancel(Model model, HttpServletRequest request, @PathVariable("uid") int uid, @PathVariable("mid") int mid) {
		userMapper.updateMentiCancel(uid);
		mentiMapper.delete(uid);
		mentoroomMapper.updatePersoncount2(mid);
		return "멘티신청이 취소되었습니다";
		}

		//멘티목록
		@RequestMapping(value="mentoroom/{m_id}/menti_list")
		public List<Menti> menti_list(Model model, HttpServletRequest request, @PathVariable("m_id") int m_id) {
		return mentiMapper.findAll(m_id);
		}

		//파일 업로드
	    @Transactional
	    @RequestMapping(value="mentoroom/fileupload/{r_id}/{kind}", method = RequestMethod.POST)
	    public void fileupload(@RequestBody MultipartFile uploadFile, MultipartHttpServletRequest mrequest, Model model,HttpServletRequest request, @PathVariable("r_id") int r_id, @PathVariable("kind") int kind ) throws IllegalStateException, IOException {
	    	String file_name = Paths.get(uploadFile.getOriginalFilename()).getFileName().toString();
	    	UploadFile uf = new UploadFile();

	    	uf.setFile_name(file_name);
	    	uf.setFile_data(uploadFile.getBytes());
	    	uf.setFile_type(uploadFile.getContentType());
	    	uf.setFile_kind(kind);
	    	uf.setMentoroom_id(r_id);
	    	uploadFileMapper.insert(uf);

	    	if(kind == 3)
	    		mentoroomMapper.updateReportcount1(r_id);

	    }

	    //보고서, 사진 목록
	    @RequestMapping(value="mentoroom/filelist/{r_id}")
	    public List<UploadFile> file_list(Model model, HttpServletRequest request, @PathVariable("r_id") int r_id) {
	        return uploadFileMapper.findByRoomId(r_id);
	    }

	    //파일 삭제
	    @RequestMapping(value="mentoroom/filedelete/{r_id}/{f_id}")
	    public String filedelete(@PathVariable("r_id") int r_id, @PathVariable("f_id") int f_id, HttpServletRequest request, Model model ) throws UnsupportedEncodingException {
	    	uploadFileMapper.delete(f_id);
	    	mentoroomMapper.updateReportcount2(r_id);
	    	return "파일이 삭제되었습니다";
	   }

	   //사진목록(홈화면)
	    @RequestMapping(value="home/filelist")
	    public List<UploadFile> filelist(Model model, HttpServletRequest request) {
	    	Date d = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String date = sdf.format(d);
		    String year = date.substring(0,4);
		    String month = date.substring(5,7);
		    int y = Integer.parseInt(year);
		    int m = Integer.parseInt(month);
		    Mentoroom mentoroom = new Mentoroom();
			mentoroom.setTeam_year(y);
			int semester=0;
			if(m>=3 && m<=7)
				semester=1;
			if(m>=9 && m<=12)
				semester=2;
			mentoroom.setTeam_semester(semester);
	        return uploadFileMapper.selectPicture(mentoroom);
	    }



}