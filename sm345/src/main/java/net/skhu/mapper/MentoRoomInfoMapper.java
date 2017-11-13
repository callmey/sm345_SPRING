package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.MentoRoomInfo;

@Mapper
public interface MentoRoomInfoMapper {
	List<MentoRoomInfo> findAll();
    void insert(MentoRoomInfo mentoroominfo);
    void delete();
}
