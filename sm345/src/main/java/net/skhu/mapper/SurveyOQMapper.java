package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.SurveyObjectQuestion;

@Mapper
public interface SurveyOQMapper {
	List<SurveyObjectQuestion> findByYear(SurveyObjectQuestion surveyOQ);
	void insert (SurveyObjectQuestion surveyOQ);
}
