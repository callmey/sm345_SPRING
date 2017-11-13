package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.Message;
import net.skhu.dto.User;

@Mapper
public interface MessageMapper {
	List<Message> findAll();
	Message selectByToId(int to_id);
	Message selectByFromId(int from_id);
	void insert(Message message);
    void delete(int id);
}
