package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.User;

@Mapper
public interface UserMapper {
    User selectByUserId(int user_id);
    void updateBeforelogin(User user);
    void updateUserauth(int mento_id);
}
