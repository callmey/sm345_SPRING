package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.ReportDate;


@Mapper
public interface ReportDateMapper {
   List<ReportDate> findAll();
   void insert(ReportDate reportdate);
   void delete();
}
