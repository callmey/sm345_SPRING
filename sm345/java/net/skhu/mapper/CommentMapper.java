package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import net.skhu.dto.Comment;

@Mapper
public interface CommentMapper {
	List<Comment> findComment(int article_id);
	int CntComment(int article_id);
	void insert(Comment comment);
	void update(Comment comment);
	void delete(int id);
}
