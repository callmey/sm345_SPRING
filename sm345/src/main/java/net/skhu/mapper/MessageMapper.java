package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.Message;

@Mapper
public interface MessageMapper {
	List<Message> findAll();
	List<Message> selectByToId(int to_id);
	void insert(Message message);
    void delete(int id);
}
