package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.UploadFile;

@Mapper
public interface UploadFileMapper {
    void insert(UploadFile uploadFile);
    void delete(int id);
    UploadFile findOne(int id);

}
