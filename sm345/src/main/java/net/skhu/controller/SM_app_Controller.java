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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.Mentoroom;
import net.skhu.dto.User;
import net.skhu.mapper.ArticleMapper;
import net.skhu.mapper.MentoroomMapper;
import net.skhu.mapper.UserMapper;
import net.skhu.service.UserService;

@RestController
@RequestMapping("api/app/")
public class SM_app_Controller {
	@Autowired ArticleMapper articleMapper;
	@Autowired UserService userService;
	@Autowired UserMapper userMapper;
	@Autowired MentoroomMapper mentoroomMapper;

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

	//최초 비밀번호 변경
	@RequestMapping(value = "update_password", method = RequestMethod.POST)
	public Map<String, Object> update_password(Model model, @RequestBody User user, HttpServletRequest request) throws UnsupportedEncodingException {
		String new_password = userService.encryptPasswd(user.getUser_password());
		user.setUser_password(new_password);
		userMapper.update(user);
		User db_user = userMapper.selectByUserId(user.getUser_id());
		HashMap<String, Object> map = new HashMap<>();
		map.put("title", "로그인 되었습니다!");
		map.put("user_id", db_user.getUser_id());
		map.put("user_auth", db_user.getUser_auth());
		map.put("user_name", db_user.getUser_name());
		return map;
	}

	//멘토신청
	@RequestMapping(value = "insert_mentoroom", method = RequestMethod.POST)
	public Map<String, Object> insert_mentoroom(Model model, @RequestBody Mentoroom mentoroom, HttpServletRequest request) throws UnsupportedEncodingException {

		Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String date = sdf.format(d);
	    String year = date.substring(0,4);
	    String month = date.substring(5,7);
	    int y = Integer.parseInt(year);
	    int m = Integer.parseInt(month);
		System.out.println( "시간" + year + " "+month);
		mentoroom.setTeam_year(y);
		int semester=0;
		if(m>=3 && m<=7)
			semester=1;
		if(m>=9 && m<=12)
			semester=2;
		mentoroom.setTeam_semester(semester);
		mentoroomMapper.insert(mentoroom);
		HashMap<String, Object> map = new HashMap<>();
		map.put("title", "멘토신청이 완료되었습니다");
		return map;
	}

	//멘토방목록
	@RequestMapping("mentoroom")
	public List<Mentoroom> mentoroomList(HttpServletRequest request, Model model) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
		return mentoroomMapper.findAll();
	}

}