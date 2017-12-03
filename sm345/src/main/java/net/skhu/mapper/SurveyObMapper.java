package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.SurveyObject;

@Mapper
public interface SurveyObMapper {
	void insert(SurveyObject surveyOb);
}
