package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.SurveyObject;

@Mapper
public interface SurveyObMapper {
	int findSum(SurveyObject surveyOb);
	List<SurveyObject> findByYear(SurveyObject surveyOb);
	List<SurveyObject> findByQuestion(SurveyObject surveyOb);
	void insert(SurveyObject surveyOb);
	void updateAnswer(int[] surveyob);
	void updateCount(int question_id);
	void updateCount2(int question_id);
	void updateCount3(int question_id);
	void updateCount4(int question_id);
	void updateCount5(int question_id);
}
