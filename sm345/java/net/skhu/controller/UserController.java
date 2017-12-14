package net.skhu.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.User;
import net.skhu.mapper.StudentMapper;
import net.skhu.mapper.UserMapper;
import net.skhu.service.UserService;

@RestController
@RequestMapping("api/")
public class UserController {

	@Autowired UserService userService;
	@Autowired UserMapper userMapper;
	@Autowired StudentMapper studentMapper;

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

	//survey_check 받아오기
    @RequestMapping("{u_id}/survey_check")
    public int check(@PathVariable("u_id") int u_id, Model model, HttpServletRequest request) throws Exception {
    	return studentMapper.selectSurveycheck(u_id);
    }

    //엑셀 등록
    @RequestMapping(value="excel", method = RequestMethod.POST)
    public void excel(@RequestBody User[] user, Model model,HttpServletRequest request) {
      for(int i=0; i<user.length; i++)
         userMapper.insert(user[i]);
    }

}