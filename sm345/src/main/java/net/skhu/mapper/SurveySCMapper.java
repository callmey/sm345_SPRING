package net.skhu.mapper;
import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.SurveySubjectContent;

@Mapper
public interface SurveySCMapper {
	void insert (SurveySubjectContent surveySC);
}
