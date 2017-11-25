package net.skhu.service;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.mapper.UserMapper;

@Service
public class UserService {

	@Autowired UserMapper userMapper;

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
