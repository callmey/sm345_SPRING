package net.skhu.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import net.skhu.dto.Comment;
import net.skhu.dto.Menti;
import net.skhu.dto.MentoRoomInfo;
import net.skhu.dto.Mentoroom;
import net.skhu.dto.Message;
import net.skhu.dto.UploadFile;
import net.skhu.dto.User;
import net.skhu.mapper.ArticleMapper;
import net.skhu.mapper.CommentMapper;
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
	@Autowired CommentMapper commentMapper;

	//로그인
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public Map<String, Object> login(Model model, @RequestBody User user, HttpServletRequest request) throws UnsupportedEncodingException {
		User db_user = userMapper.selectByUserId(user.getUser_id());
		HashMap<String, Object> map = new HashMap<>();
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

	//로그인기록조회
	@RequestMapping(value = "login_record", method = RequestMethod.POST)
    public Map<String, Object> login_record(HttpServletRequest request, Model model, @RequestBody User user) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(user.getUser_id() == 0 && user.getUser_password().isEmpty()){
			map.put("key", -4);
    		map.put("title", "아이디와 비밀번호를 입력하세요");
    		return map;
		}
		if(user.getUser_id() == 0){
    		map.put("key", -1);
    		map.put("title", "아이디를 입력하세요");
    		return map;
		}
		if (user.getUser_password().isEmpty()) {
			map.put("key", -2);
    		map.put("title", "비밀번호를 입력하세요");
    		return map;
		}
		if (userMapper.selectByUserId(user.getUser_id()) == null) {
			map.put("key", -3);
    		map.put("title", "존재하지 않는 회원입니다");
    		return map;
		}
		int login_record = userMapper.selectByUserId(user.getUser_id()).getLogin_record();
        map.put("login_record", login_record);
    	return map;
    }

	//비밀번호 변경
	@RequestMapping(value ="updatepassword", method = RequestMethod.POST)
	public Map<String, Object> update_password(Model model, @RequestBody User user, HttpServletRequest request) throws UnsupportedEncodingException {
		User db_user = userMapper.selectByUserId(user.getUser_id());
		HashMap<String, Object> map = new HashMap<>();
		String new_password = userService.encryptPasswd(user.getUser_password()); //암호화
		user.setUser_password(new_password);
		if(db_user.getLogin_record() == 1){ //로그인 기록이 있으면
			user.setLogin_record(1);
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

	//각 멘토방 조회
	@RequestMapping("mentoroom/{r_id}")
	public Mentoroom mentoroom(HttpServletRequest request, Model model, @PathVariable("r_id") int r_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
		return mentoroomMapper.findMentoroom(r_id);
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

	//게시판 목록
    @RequestMapping("article/list/{b_id}")
    public List<Article> list(Model model, HttpServletRequest request, @PathVariable("b_id") int b_id) {
        return articleMapper.findAll(b_id);
    }

    //게시글 보기
    @RequestMapping("article/list/{a_id}/{u_id}")
    public @ResponseBody Article article(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id, @PathVariable("a_id") int a_id) {
        Article article = articleMapper.findArticle(a_id);
        if(u_id != article.getArticle_writer())
        	articleMapper.updateHit(article); //조회수 증가
    	return article;
    }

    //게시글 생성
    @RequestMapping(value="article/create", method = RequestMethod.POST)
    public void list_create(@RequestBody Article article, Model model, HttpServletRequest request ) {
        articleMapper.insert(article);
    }

    //게시글 수정
    @RequestMapping(value="article/edit", method = RequestMethod.POST)
    public String edit(@RequestBody Article article, Model model, HttpServletRequest request ) {
        articleMapper.update(article);
        return "게시글이 수정되었습니다";
    }

    //게시글 삭제
    @RequestMapping("article/{a_id}/delete")
    public void delete(@PathVariable("a_id") int a_id, Model model, HttpServletRequest request ) {
        articleMapper.delete(a_id);
    }

    //답변 완료
    @RequestMapping(value="admin/{a_id}/answer")
    public void answer(Model model, HttpServletRequest request, @PathVariable("a_id") int a_id) {
        articleMapper.updateAnswer(a_id);
    }

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

    //댓글 생성
    @RequestMapping(value="comment/create", method = RequestMethod.POST)
    public void comment_create(@RequestBody Comment comment, Model model, HttpServletRequest request) {
        commentMapper.insert(comment);
    }

    //댓글 수정
    @RequestMapping(value="comment/edit", method = RequestMethod.POST)
    public void comment_update(@RequestBody Comment comment, Model model, HttpServletRequest request ) {
        commentMapper.update(comment);
    }

    //댓글 삭제
    @RequestMapping("comment/delete/{c_id}")
    public void delete(Model model, @PathVariable("c_id") int c_id, HttpServletRequest request ) {
        commentMapper.delete(c_id);
    }

    //댓글 목록
    @RequestMapping("comment/list/{a_id}")
    public List<Comment> comment_list (Model model, HttpServletRequest request, @PathVariable("a_id") int a_id) {
    	return commentMapper.findComment(a_id);
    }

    //댓글수 조회
    @RequestMapping("list/CntComment/{a_id}")
    public int replyCnt(Model model, HttpServletRequest request, @PathVariable("a_id") int a_id) {
    	return commentMapper.CntComment(a_id);
    }

   	//쪽지함 목록
    @RequestMapping("message/list/{u_id}")
    public List<Message> messagelist(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
        List<Message> list = messageMapper.selectByToId(u_id);
        return list;
    }

    //쪽지 받는 사람 이름 불러오기
    @RequestMapping("message/username/{u_id}")
	public String to_name(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) throws UnsupportedEncodingException {
		return userMapper.selectByUserName(u_id);
    }

    //쪽지 보내기
    @RequestMapping(value="message/create", method = RequestMethod.POST)
    public String create(@RequestBody Message message, Model model, HttpServletRequest request ) {
        messageMapper.insert(message);
        return "쪽지가 전송되었습니다";
    }

    //쪽지 조회
    @RequestMapping("message/{m_id}")
    public Message message(Model model, HttpServletRequest request, @PathVariable("m_id") int m_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
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

    //관리자 권한 해제
    @RequestMapping(value="admin/leave/{u_id}")
    public void admin_leave(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) {
        userMapper.updateLeave(u_id);
    }

    /*
    //파일업로드
    @RequestMapping(value="mentoroom/fileupload/{r_id}/{kind}", method = RequestMethod.POST)
    public void fileupload(@RequestBody MultipartFile uploadFile, MultipartHttpServletRequest mrequest, Model model,HttpServletRequest request, @PathVariable("r_id") int r_id, @PathVariable("kind") int kind ) throws IllegalStateException, IOException {
    	Random random = new Random();
    	String filename=random.nextInt()+Paths.get(uploadFile.getOriginalFilename()).getFileName().toString();
    	String file_original = Paths.get(uploadFile.getOriginalFilename()).getFileName().toString();
    	String path = mrequest.getSession().getServletContext().getRealPath("/upload/")+filename;
    	PATH = mrequest.getSession().getServletContext().getRealPath("/upload/");
    	File file = new File(path);
    	uploadFile.transferTo(file);
    	String relPath = "/upload/" + filename;
    	long size = uploadFile.getSize();
    	UploadFile uploadfile;
    	uploadfile = uploadFileService.create(filename, size, r_id, null, relPath, kind, file_original);
    	uploadFileMapper.insert(uploadfile);

    	mentoroomMapper.updateReportcheck1(r_id);
    	if(mentoroomMapper.findMentoroom(r_id).getReport_check() == mentoroominfoMapper.findMentoRoomInfo().get)
    	//보고서등록+1하고, user에서 보고서 등록 유저로 하기.

    	//목록별로볼수있도록 등록하기
    }
    */

  //파일업로드
    @Transactional
    @RequestMapping(value="mentoroom/fileupload", method = RequestMethod.POST)
    public String fileupload(@RequestBody MultipartFile uploadFile) throws IllegalStateException, IOException {
    	String fileName = Paths.get(uploadFile.getOriginalFilename()).getFileName().toString();
        UploadFile uploadedFile = new UploadFile();
        uploadedFile.setFile_name(fileName);
        uploadedFile.setFile_content(uploadFile.getBytes());
        uploadedFile.setFile_kind(1);
    	uploadedFile.setMentoroom_id(26);
    	uploadedFile.setFile_type("html");
    	uploadFileMapper.insert(uploadedFile);
    	//mentoroomMapper.updateReportcheck1(uploadFile.getMentoroom_id());
    	//보고서등록+1하고, user에서 보고서 등록 유저로 하기.
    	//목록별로볼수있도록 등록하기
    	return "파일이 업로드 되었습니다";
    }

    //보고서, 사진 목록
    @RequestMapping(value="mentoroom/filelist/{r_id}")
    public List<UploadFile> file_list(Model model, HttpServletRequest request, @PathVariable("r_id") int r_id) {
        return uploadFileMapper.findByRoomId(r_id);
    }

    //파일 삭제
    @RequestMapping(value="mentoroom/filedelete/{f_id}")
    public String filedelete(@PathVariable("f_id") int f_id, HttpServletRequest request, Model model ) throws UnsupportedEncodingException {
    	uploadFileMapper.delete(f_id);
    	return "파일이 삭제되었습니다";
   }

    /*
    //파일 다운로드
    @RequestMapping("file/download")
    public void download(@RequestParam("id") int id, HttpServletResponse response) throws Exception {
        UploadedFile uploadedfile = uploadedFileRepository.findOne(id);
       if (uploadedfile == null) return;
        String fileName = URLEncoder.encode(uploadedfile.getFileName(),"UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
        try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
            output.write(uploadedfile.getData());
        }
    }
*/
    //앱 최초 실행시 user_auth 받아오기
    @RequestMapping("{u_id}/check_userauth")
    public int check_userauth(@PathVariable("u_id") int u_id, Model model, HttpServletRequest request) throws Exception {
        int user_auth = userMapper.selectByUserId(u_id).getUser_auth();
        return user_auth;
    }

}