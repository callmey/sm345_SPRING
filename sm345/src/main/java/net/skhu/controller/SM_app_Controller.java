package net.skhu.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.User;
import net.skhu.mapper.ArticleMapper;
import net.skhu.mapper.UserMapper;
import net.skhu.service.UserService;

@RestController
@RequestMapping("/app")
public class SM_app_Controller {
	@Autowired ArticleMapper articleMapper;
	@Autowired UserService userService;
	@Autowired UserMapper userMapper;

	// 로그인
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
					map.put("title", "로그인 되었습니다");
					map.put("key", 0);
					map.put("user_id", db_user.getUser_id());
					map.put("user_auth", db_user.getUser_auth());
					//map.put("user_name", db_user.getUser_nickname()); //studentmapper에서 이름추가하깅
					return map;
				}
				map.put("title", "비밀번호가 맞지 않습니다");
				map.put("key", 2);
				return map;
			}

		}


}
