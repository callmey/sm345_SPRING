package net.skhu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.Comment;
import net.skhu.mapper.CommentMapper;

@RestController
@RequestMapping("api/")
public class CommentController {

	@Autowired CommentMapper commentMapper;

	 //댓글 생성
    @RequestMapping(value="comment/create", method = RequestMethod.POST)
    public void comment_create(@RequestBody Comment comment, Model model, HttpServletRequest request) {
        commentMapper.insert(comment);
    }

    //댓글 수정
    @RequestMapping(value="comment/edit", method = RequestMethod.POST)
    public void comment_update(@RequestBody Comment comment, Model model, HttpServletRequest request ) {
        commentMapper.update(comment);
    }

    //댓글 삭제
    @RequestMapping("comment/delete/{c_id}")
    public void delete(Model model, @PathVariable("c_id") int c_id, HttpServletRequest request ) {
        commentMapper.delete(c_id);
    }

    //댓글 목록
    @RequestMapping("comment/list/{a_id}")
    public List<Comment> comment_list (Model model, HttpServletRequest request, @PathVariable("a_id") int a_id) {
    	return commentMapper.findComment(a_id);
    }

    //댓글수 조회
    @RequestMapping("list/CntComment/{a_id}")
    public int replyCnt(Model model, HttpServletRequest request, @PathVariable("a_id") int a_id) {
    	return commentMapper.CntComment(a_id);
    }

}