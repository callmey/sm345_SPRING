package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.SurveySubjectQuestion;

@Mapper
public interface SurveySQMapper {
	void insert (SurveySubjectQuestion surveySQ);
}
