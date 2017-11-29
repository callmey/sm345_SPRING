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
@RequestMapping("api/")
public class ArticleController {

	@Autowired ArticleMapper articleMapper;

	//게시판 목록
    @RequestMapping("article/list/{b_id}")
    public List<Article> list(Model model, HttpServletRequest request, @PathVariable("b_id") int b_id) {
        return articleMapper.findAll(b_id);
    }

    //게시글 보기
    @RequestMapping("article/list/{a_id}/{u_id}")
    public @ResponseBody Article article(Model model, HttpServletRequest request, @PathVariable("u_id") int u_id, @PathVariable("a_id") int a_id) {
        Article article = articleMapper.findArticle(a_id);
        if(u_id != article.getArticle_writer())
        	articleMapper.updateHit(article); //조회수 증가
    	return article;
    }

    //게시글 생성
    @RequestMapping(value="article/create", method = RequestMethod.POST)
    public void list_create(@RequestBody Article article, Model model, HttpServletRequest request ) {
        articleMapper.insert(article);
    }

    //게시글 수정
    @RequestMapping(value="article/edit", method = RequestMethod.POST)
    public String edit(@RequestBody Article article, Model model, HttpServletRequest request ) {
        articleMapper.update(article);
        return "게시글이 수정되었습니다";
    }

    //게시글 삭제
    @RequestMapping("article/{a_id}/delete")
    public void delete(@PathVariable("a_id") int a_id, Model model, HttpServletRequest request ) {
        articleMapper.delete(a_id);
    }

}