package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.Mentoroom;

@Mapper
public interface MentoroomMapper {
    List<Mentoroom> findAll();
    List<Mentoroom> findAllByYear(Mentoroom mentoroom);
    Mentoroom findMentoroom(int mentoroom_id);
    void insert(Mentoroom mentoroom);
    void updateTeamconfirm(int mentoroom_id);
    void deleteMentoroom(int mentoroom_id);
    void updateReportcheck1(int mentoroom_id);
    void updateReportcheck2(int mentoroom_id);
    void updatePersoncount1(int mento_id);
    void updatePersoncount2(int mento_id);
}
