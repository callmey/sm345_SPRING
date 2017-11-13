package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.MentoRoomInfo;

@Mapper
public interface MentoRoomInfoMapper {
	MentoRoomInfo findMentoRoomInfo();
    void update(MentoRoomInfo mentoroominfo);
}
