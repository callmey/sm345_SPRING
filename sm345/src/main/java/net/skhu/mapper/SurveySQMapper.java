package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.SurveySubjectQuestion;

@Mapper
public interface SurveySQMapper {
	List<SurveySubjectQuestion> findByYear(SurveySubjectQuestion surveySQ);
	void insert (SurveySubjectQuestion surveySQ);
}
