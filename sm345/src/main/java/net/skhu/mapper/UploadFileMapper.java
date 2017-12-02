package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.Mentoroom;
import net.skhu.dto.UploadFile;

@Mapper
public interface UploadFileMapper {
    void insert(UploadFile uploadFile);
    void delete(int id);
    UploadFile findOne(int id);
    List<UploadFile> findByRoomId(int mentoroom_id);
    List<UploadFile> findAll();
    List<UploadFile> findAllByYear(Mentoroom mentoroom);
    void updateConfirm(int id);
    void updateReject(int id);
}
