package net.skhu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.Article;
import net.skhu.mapper.ArticleMapper;

@RestController
//@RequestMapping("/api")
public class SMController {

    @Autowired ArticleMapper articleMapper;

    //게시판 목록
    @RequestMapping("list/{bid}")
    public @ResponseBody List<Article> list(Model model, HttpServletRequest request, @PathVariable("bid") int bid) {
        List<Article> list = articleMapper.findAll(bid);
        model.addAttribute("list", list);
        return list;
    }

    //게시글 생성
    @RequestMapping(value="list/{bid}/create", method = RequestMethod.POST)
    public String crate(@RequestBody Article article, @PathVariable("bid") int bid,  Model model, HttpServletRequest request ) {
        articleMapper.insert(article);
        return "게시글이 등록되었습니다.";
    }

    //게시글 수정
    @RequestMapping(value="list/{bid}/{aid}/edit", method = RequestMethod.POST)
    public String edit(@RequestBody Article article, @PathVariable("bid") int bid, @PathVariable("aid") int aid, Model model, HttpServletRequest request ) {
        articleMapper.update(article);
        return "게시글이 수정되었습니다.";
    }

    //게시글 삭제
    @RequestMapping(value="list/{bid}/{aid}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("bid") int bid, @PathVariable("aid") int aid, Model model, HttpServletRequest request ) {
        articleMapper.delete(aid);
        return "게시글이 삭제되었습니다";
    }




}
