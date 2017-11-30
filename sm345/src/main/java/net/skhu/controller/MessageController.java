package net.skhu.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.Message;
import net.skhu.mapper.MessageMapper;
import net.skhu.mapper.UserMapper;

@RestController
@RequestMapping("api/")
public class MessageController {

	@Autowired MessageMapper messageMapper;
	@Autowired UserMapper userMapper;

	//쪽지함 목록
    @RequestMapping("message/list/{u_id}")
    public List<Message> messagelist(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
        List<Message> list = messageMapper.selectByToId(u_id);
        return list;
    }

    //쪽지 받는 사람 이름 불러오기
    @RequestMapping("message/username/{u_id}")
	public String to_name(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id) throws UnsupportedEncodingException {
		return userMapper.selectByUserName(u_id);
    }

    //쪽지 보내기
    @RequestMapping(value="message/create", method = RequestMethod.POST)
    public String create(@RequestBody Message message, Model model, HttpServletRequest request ) {
        messageMapper.insert(message);
        return "쪽지가 전송되었습니다";
    }

    //쪽지 조회
    @RequestMapping("message/{m_id}")
    public Message message(Model model, HttpServletRequest request, @PathVariable("m_id") int m_id) throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException {
        messageMapper.updateReadcheck(m_id); //쪽지읽음으로 표시
    	return messageMapper.findMessage(m_id);
    }

    //쪽지 삭제
    @RequestMapping(value="message/{m_id}/delete")
    public void delete_message(Model model, HttpServletRequest request, @PathVariable("m_id") int m_id) {
        messageMapper.delete(m_id);
    }

}

