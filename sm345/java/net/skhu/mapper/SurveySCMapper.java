package net.skhu.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.SurveySubjectContent;
import net.skhu.dto.SurveySubjectQuestion;

@Mapper
public interface SurveySCMapper {
	List<SurveySubjectQuestion> findByYear(int question_id);
	void insert (SurveySubjectContent surveySC);
	void SurveyCheck (int id);
}
