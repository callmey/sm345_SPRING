package net.skhu.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.skhu.dto.Article;
import net.skhu.dto.Menti;
import net.skhu.dto.MentoRoomInfo;
import net.skhu.dto.Mentoroom;
import net.skhu.dto.Message;
import net.skhu.dto.UploadFile;
import net.skhu.dto.User;
import net.skhu.mapper.ArticleMapper;
import net.skhu.mapper.MentiMapper;
import net.skhu.mapper.MentoRoomInfoMapper;
import net.skhu.mapper.MentoroomMapper;
import net.skhu.mapper.MessageMapper;
import net.skhu.mapper.StudentMapper;
import net.skhu.mapper.UploadFileMapper;
import net.skhu.mapper.UserMapper;
import net.skhu.service.UserService;

@RestController
@RequestMapping("api/")
public class SMController {
	@Autowired ArticleMapper articleMapper;
	@Autowired UserService userService;
	@Autowired UserMapper userMapper;
	@Autowired MentoroomMapper mentoroomMapper;
	@Autowired StudentMapper studentMapper;
	@Autowired MessageMapper messageMapper;
	@Autowired MentoRoomInfoMapper mentoroominfoMapper;
	@Autowired MentiMapper mentiMapper;
	@Autowired UploadFileMapper uploadFileMapper;

	//로그인
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public Map<String, Object> login(Model model, @RequestBody User user, HttpServletRequest request) throws UnsupportedEncodingException {
		String message = null;
		message = userService.validateBeforelogin(user.getUser_id(), user.getUser_password());
		User db_user = userMapper.selectByUserId(user.getUser_id());

		HashMap<String, Object> map = new HashMap<>();

		if (message != "") {
			map.put("title", message);
			map.put("key", -1);
			return map;

		} else {
			if (userService.encryptPasswd(user.getUser_password()).equals(db_user.getUser_password())) {
				map.put("title", "로그인 되었습니다!");
				map.put("key", 0);
				map.put("user_id", db_user.getUser_id());
				map.put("user_auth", db_user.getUser_auth());
				map.put("user_name", db_user.getUser_name());
				return map;
			}
			map.put("title", "비밀번호가 맞지 않습니다");
			map.put("key", 2);
			return map;
		}
	}

	//로그인기록조회
	@RequestMapping("login_record/{u_id}")
    public Map<String, Object> login_record(HttpServletRequest request, Model model, @PathVariable("u_id") int u_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
    	int login_record = userMapper.selectByUserId(u_id).getLogin_record();
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("login_record", login_record);
    	return map;
    }

	//비밀번호 변경
	@RequestMapping(value ="updatepassword", method = RequestMethod.POST)
	public Map<String, Object> update_password(Model model, @RequestBody User user, HttpServletRequest request) throws UnsupportedEncodingException {
		String new_password = userService.encryptPasswd(user.getUser_password()); //암호화
		user.setUser_password(new_password);
		User db_user = userMapper.selectByUserId(user.getUser_id());
		HashMap<String, Object> map = new HashMap<>();
		if(db_user.getLogin_record() == 1){ //로그인 기록이 있으면
			userMapper.updateBeforelogin(user);
			map.put("title", "비밀번호가 변경되었습니다");
		}
		else{
			user.setLogin_record(1);
			userMapper.updateBeforelogin(user);
			map.put("title", "로그인 되었습니다!");
			map.put("user_id", db_user.getUser_id());
			map.put("user_auth", db_user.getUser_auth());
			map.put("user_name", db_user.getUser_name());
		}
		return map;
	}

	//멘토신청
	@RequestMapping(value = "mentoroom/create", method = RequestMethod.POST)
	public Map<String, Object> mentoroom_create(Model model, @RequestBody Mentoroom mentoroom, HttpServletRequest request) throws UnsupportedEncodingException {

		System.out.println("실행은되냐");
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
		System.out.println("멘토이름"+mento_name);
		mentoroomMapper.insert(mentoroom);
		HashMap<String, Object> map = new HashMap<>();
		map.put("title", "멘토신청이 완료되었습니다");
		return map;
	}

	//멘토방 목록
	@RequestMapping("mentoroom")
	public List<Mentoroom> mentoroomList(HttpServletRequest request, Model model) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
		return mentoroomMapper.findAll();
	}

	//멘토방 목록 (관리자)
	@RequestMapping("mentoroom/{year}/1")
	public List<Mentoroom> mentoroomList_admn(HttpServletRequest request, Model model, @PathVariable("year") int year) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
		if(year == 0)
			return mentoroomMapper.findAll();
		else{
		String y = String.valueOf(year);
		int yyyy = Integer.parseInt(y.substring(0,4));
		int s =  Integer.parseInt(y.substring(4,5));
		System.out.println(yyyy+" "+s);
		Mentoroom mentoroom = new Mentoroom();
		mentoroom.setTeam_year(yyyy);
		mentoroom.setTeam_semester(s);
		return mentoroomMapper.findAllByYear(mentoroom);
		}
	}

	//각 멘토방 조회
	@RequestMapping("mentoroom/{r_id}")
	public Mentoroom mentoroom(HttpServletRequest request, Model model, @PathVariable("r_id") int r_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
		return mentoroomMapper.findMentoroom(r_id);
	}

	//멘토방 승인
	@RequestMapping(value="mentoroom/{r_id}/confirm", method = RequestMethod.POST)
	public void mentoroom_confirm(HttpServletRequest request, Model model, @RequestBody Mentoroom mentoroom,  @PathVariable("r_id") int r_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
		mentoroomMapper.updateTeamconfirm(mentoroom.getMentoroom_id()); //team_confirm 1로 업데이트
		userMapper.updateUserauth(mentoroom.getMento_id()); //user_auth 1로 업데이트(멘토승격)
	}

	//멘토방 승인거절
	@RequestMapping("mentoroom/{r_id}/reject")
	public void mentoroom_reject(HttpServletRequest request, Model model, @PathVariable("r_id") int r_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
		mentoroomMapper.deleteMentoroom(r_id);
	}

	//게시판 목록
    @RequestMapping("list/{b_id}")
    public List<Article> list(Model model, HttpServletRequest request, @PathVariable("b_id") int b_id) {
        return articleMapper.findAll(b_id);
    }

    //게시글 보기
    @RequestMapping("list/{b_id}/{a_id}")
    public @ResponseBody Article article(Model model, HttpServletRequest request, @PathVariable("b_id") int b_id, @PathVariable("a_id") int a_id) {
        int hit = articleMapper.selectHit(a_id); //조회수 조회
        int new_hit = hit+1;
        Article article = new Article();
        article.setId(a_id);
        article.setArticle_hit(new_hit);
    	articleMapper.updateHit(article); //조회수 증가
    	return articleMapper.findArticle(a_id);
    }

    //게시글 생성
    @RequestMapping(value="list/{b_id}/create", method = RequestMethod.POST)
    public void list_create(@RequestBody Article article, @PathVariable("b_id") int b_id,  Model model, HttpServletRequest request ) {
        articleMapper.insert(article);
    }

    //게시글 수정
    @RequestMapping(value="list/{b_id}/{a_id}/edit", method = RequestMethod.POST)
    public String edit(@RequestBody Article article, @PathVariable("b_id") int b_id, @PathVariable("a_id") int a_id, Model model, HttpServletRequest request ) {
        articleMapper.update(article);
        return "게시글이 수정되었습니다";
    }

    //게시글 삭제
    @RequestMapping("list/{b_id}/{a_id}/delete")
    public void delete(@PathVariable("b_id") int b_id, @PathVariable("a_id") int a_id, Model model, HttpServletRequest request ) {
        articleMapper.delete(a_id);
    }

    //답변 완료
    @RequestMapping(value="list/{b_id}/{a_id}/answer")
    public void answer(Model model, HttpServletRequest request, @PathVariable("a_id") int a_id) {
        articleMapper.updateAnswer(a_id);
    }

/*
    //댓글 생성
    @RequestMapping(value="list/3/{a_id}/create", method = RequestMethod.POST)
    public void comment_crate(@RequestBody Comment comment, @PathVariable("a_id") int a_id,  Model model, HttpServletRequest request ) {
    	System.out.println("생성실행되니??????????/");
        commentMapper.insert(comment);
    }
*/
    //사용자 목록
    @RequestMapping("admin/user/{auth}")
    public List<User> user_list(Model model, HttpServletRequest request, @PathVariable("auth") int auth) {
        if(auth == 4) //보고서 미제출멘토
        	return userMapper.selectReportNotYet();
        if(auth == 0) //전체 학생 목록
        	return userMapper.selectStudent();
    	List<User> list = userMapper.findAll(auth);
        return list;
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

   	// 멘티신청
   	@RequestMapping(value="mentoroom/{rid}/{mid}/{uid}/menti_join")
   	public String menti_join(Model model, HttpServletRequest request, @PathVariable("mid") int mid, @PathVariable("uid") int uid) {
        userMapper.updateMentiauth(uid);
        Menti menti = new Menti();
        menti.setMenti_id(uid);
        menti.setMento_id(mid);
        mentiMapper.insert(menti);
        return "멘티신청이 완료되었습니다";
     }

   	// 멘티신청취소
   	@RequestMapping(value="mentoroom/{rid}/{uid}/menti_canceal")
   	public String menti_canceal(Model model, HttpServletRequest request, @PathVariable("uid") int uid) {
        userMapper.updateMentiCanceal(uid);
        mentiMapper.delete(uid);
        return "멘티신청이 취소되었습니다";
     }

   	//쪽지함 목록
    @RequestMapping("message/{u_id}")
    public List<Message> messagelist(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
        List<Message> list = messageMapper.selectByToId(u_id);
        return list;
    }

    //쪽지 받는 사람 이름 불러오기
    @RequestMapping(value = "message/username/{u_id}", method = RequestMethod.POST)
	public Map<String, Object> to_name(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) throws UnsupportedEncodingException {
		String to_name = userMapper.selectByUserName(u_id);
		HashMap<String, Object> map = new HashMap<>();
		map.put("user_name", to_name);
		return map;
    }

    //쪽지 보내기
    @RequestMapping(value="message/create", method = RequestMethod.POST)
    public String create(@RequestBody Message message, Model model, HttpServletRequest request ) {
        messageMapper.insert(message);
        return "쪽지가 전송되었습니다";
    }

    //쪽지 조회
    @RequestMapping("message/{m_id}")
    public Message message(Model model, HttpServletRequest request, @PathVariable("m_id") int m_id) {
        messageMapper.updateReadcheck(m_id); //쪽지읽음으로 표시
    	return messageMapper.findMessage(m_id);
    }

    //쪽지 삭제
    @RequestMapping(value="message/{m_id}/delete")
    public void delete_message(Model model, HttpServletRequest request, @PathVariable("m_id") int m_id) {
        messageMapper.delete(m_id);
    }

    //관리자 지정
    @RequestMapping(value="admin/empower/{u_id}")
    public void admin_empower(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) {
        userMapper.updateEmpower(u_id);
    }

    //관리자 권한 해제 (user_auth == 4)
    @RequestMapping(value="admin/leave/{u_id}")
    public void admin_leave(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) {
        userMapper.updateLeave(u_id);
    }

    //보고서 저장
    @Transactional
    @RequestMapping(value="mentoroom/{r_id}/{file_kind}/upload", method=RequestMethod.POST)
    public String fileupload(@RequestBody MultipartFile[] uploadFiles, @PathVariable("r_id") int r_id, @PathVariable("file_kind") int file_kind, Model model, HttpServletRequest request) throws IOException {
        for(MultipartFile uploadFile : uploadFiles) {
            if (uploadFile.getSize() <= 0) continue;
            String fileName = Paths.get(uploadFile.getOriginalFilename()).getFileName().toString();
            UploadFile uploadedFile = new UploadFile();
            uploadedFile.setFile_name(fileName);
            uploadedFile.setFile_size((int)uploadFile.getSize());
            //uploadedFile.setTimestamp(new Timestamp(0));
            uploadedFile.setFile_data(uploadFile.getBytes());
            uploadedFile.setFile_kind(file_kind);
            uploadedFile.setMentoroom_id(r_id);
            uploadFileMapper.insert(uploadedFile);
        }
        mentoroomMapper.updateReportcheck(r_id); //보고서 제출시 +1
        //if(mentoroomMapper.findMentoroom(r_id).getReport_check() == Integer.parseInt(mentoroominfoMapper.findMentoRoomInfo().getMeeting_number())) //보고서제출횟수와 모임횟수가 같다면
        	//userMapper.updateReportcheck(uploadFile.getMentoroom_id());//유저테이블 업데이트
        return "보고서가 업로드 되었습니다";
    }

    //파일 삭제
    @RequestMapping("mentoroom/{r_id}/{f_id}/delete")
    public String filedelete(@PathVariable("f_id") int f_id, Model model, HttpServletRequest request) throws Exception {
        uploadFileMapper.delete(f_id);
        //reportcheck
        return "보고서가 삭제되었습니다";
    }

    //파일 다운로드
    @RequestMapping("mentoroom/{r_id}/{f_id}/download")
    public String download(@PathVariable("f_id") int f_id, HttpServletResponse response) throws Exception {
        UploadFile uploadedfile = uploadFileMapper.findOne(f_id);
       if (uploadedfile == null) return "파일이 없습니다";
        String fileName = URLEncoder.encode(uploadedfile.getFile_name(),"UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
        try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
            output.write(uploadedfile.getFile_data());
        }
        return "파일이 다운로드되었습니다";
    }
}