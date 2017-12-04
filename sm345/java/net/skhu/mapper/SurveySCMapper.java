package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.SurveySubjectContent;

@Mapper
public interface SurveySCMapper {
	List<SurveySubjectContent> findById(int question_id);
	void insert (SurveySubjectContent surveySC);
}
