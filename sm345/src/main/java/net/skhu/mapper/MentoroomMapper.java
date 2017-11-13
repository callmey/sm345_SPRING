package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.Mentoroom;

@Mapper
public interface MentoroomMapper {
    void insert(Mentoroom mentoroom);
    List<Mentoroom> findAll();
    Mentoroom findMentoroom(int mentoroom_id);
    void updateTeamconfirm(int mentoroom_id);
    void deleteMentoroom(int mentoroom_id);
}
