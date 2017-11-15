package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.User;

@Mapper
public interface UserMapper {
	List<User> findAll(int user_auth);
    User selectByUserId(int user_id);
    String selectByUserName(int user_id);
    void updateBeforelogin(User user);
    void updateUserauth(int mento_id);
    void updateMentiauth(int menti_id);
    void updateMentiCanceal(int menti_id);
    User selectByUserAuth(int user_auth);
}
