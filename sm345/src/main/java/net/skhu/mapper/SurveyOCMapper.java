package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.SurveyObjectContent;

@Mapper
public interface SurveyOCMapper {
	void insert (SurveyObjectContent surveyOQ);
}
