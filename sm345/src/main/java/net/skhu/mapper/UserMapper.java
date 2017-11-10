package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.User;

@Mapper
public interface UserMapper {
    User selectByUserId(String user_id);
    void updateBeforelogin(User user);
}
