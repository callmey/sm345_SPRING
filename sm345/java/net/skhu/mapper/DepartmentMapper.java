package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
    int findDepartment(String department_name);

}
