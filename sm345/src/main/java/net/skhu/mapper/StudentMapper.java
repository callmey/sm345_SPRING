package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    String selectStudentname(int student_id);

}
