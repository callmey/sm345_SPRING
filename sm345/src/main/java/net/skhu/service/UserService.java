package net.skhu.service;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.dto.User;
import net.skhu.mapper.UserMapper;

@Service
public class UserService {

	@Autowired UserMapper userMapper;

	public String validateBeforelogin(int user_id, String user_password) {
		String err = "";
		String userId = String.valueOf(user_id);

		if (userId.isEmpty()) {
			err = (err.length() > 0) ? err : err + " ";
			err += "아이디를 입력하세요.";
		}

		if (user_password.isEmpty()) {
			err = (err.length() > 0) ? err : err + " ";
			err += "비밀번호를 입력하세요.";
		}

		User user = userMapper.selectByUserId(user_id);
		if (user == null) {
			err = (err.length() > 0) ? err : err + " ";
			err += "존재하지 않는 회원입니다.";
		}

		return err.toString();
	}

    public static String encryptPasswd(String passwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = passwd.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++)
                sb.append(Integer.toHexString(0xff & digested[i]));
            return sb.toString();
        }
        catch (Exception e) {
            return passwd;
        }
    }

}
