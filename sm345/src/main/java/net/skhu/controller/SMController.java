package net.skhu.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.Article;
import net.skhu.dto.Mentoroom;
import net.skhu.dto.Message;
import net.skhu.dto.User;
import net.skhu.mapper.ArticleMapper;
import net.skhu.mapper.MentoroomMapper;
import net.skhu.mapper.MessageMapper;
import net.skhu.mapper.StudentMapper;
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
    public @ResponseBody List<Article> list(Model model, HttpServletRequest request, @PathVariable("b_id") int b_id) {
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
    public void list_crate(@RequestBody Article article, @PathVariable("b_id") int b_id,  Model model, HttpServletRequest request ) {
    	System.out.println("생성실행되니??????????/");
        articleMapper.insert(article);
    }

    //게시글 수정
    @RequestMapping(value="list/{b_id}/{a_id}/edit", method = RequestMethod.POST)
    public String edit(@RequestBody Article article, @PathVariable("b_id") int b_id, @PathVariable("a_id") int a_id, Model model, HttpServletRequest request ) {
        articleMapper.update(article);
        return "게시글이 수정되었습니다.";
    }

    //게시글 삭제
    @RequestMapping(value="list/{b_id}/{a_id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("b_id") int b_id, @PathVariable("a_id") int a_id, Model model, HttpServletRequest request ) {
        articleMapper.delete(a_id);
        return "게시글이 삭제되었습니다";
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
    public @ResponseBody List<User> user_list(Model model, HttpServletRequest request, @PathVariable("auth") int auth) {
        List<User> list = userMapper.findAll(auth);
        return list;
    }

  //쪽지함 목록
    @RequestMapping("message")
    public @ResponseBody List<Message> message_list(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) {
        List<Message> list = messageMapper.selectByToId(u_id);
        return list;
    }

    //쪽지함 to_id -> user_name 설정
    @RequestMapping(value = "message/username/{u_id}", method = RequestMethod.POST)
	public Map<String, Object> message(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) throws UnsupportedEncodingException {
		String to_name = userMapper.selectByUserName(u_id);
		HashMap<String, Object> map = new HashMap<>();
		map.put("user_name", to_name);
		System.out.println("받는이 : "+ to_name);
		return map;
    }

    //쪽지함 생성
    @RequestMapping(value="message/create", method = RequestMethod.POST)
    public String create(@RequestBody Message message, Model model, HttpServletRequest request ) {
        messageMapper.insert(message);
        return "쪽지함이 등록되었습니다.";
    }

    //쪽지함 삭제
    @RequestMapping(value="message/{m_id}/delete", method = RequestMethod.POST)
    public String delete(Model model, HttpServletRequest request, @PathVariable("m_id") int m_id) {
        messageMapper.delete(m_id);
        return "쪽지함이 삭제되었습니다";
    }
}