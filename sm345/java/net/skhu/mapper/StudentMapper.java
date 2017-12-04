package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.Student;

@Mapper
public interface StudentMapper {
    String selectStudentname(int student_id);
    void insert(Student student);
    int selectSurveycheck(int student_id);

}
