package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.User;

@Mapper
public interface UserMapper {
	List<User> findAll(int user_auth);
    User selectByUserId(String user_id);
    User selectByUserAuth(int user_auth);
    void updateBeforelogin(User user);
}
