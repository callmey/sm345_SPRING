package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.Mentoroom;

@Mapper
public interface MentoroomMapper {
    void insert(Mentoroom mentoroom);
    List<Mentoroom> findAll();
}
