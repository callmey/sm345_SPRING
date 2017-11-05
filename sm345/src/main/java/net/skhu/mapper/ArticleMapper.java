package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.Article;

@Mapper
public interface ArticleMapper {
    List<Article> findAll(int board_id);
    void insert(Article article);
    void update(Article article);
    void delete(int id);
}
