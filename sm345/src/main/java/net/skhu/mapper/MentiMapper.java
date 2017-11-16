package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.Menti;

@Mapper
public interface MentiMapper {
    List<Menti> findAll(int mento_id);
    void insert (Menti menti);
    void delete (int user_id);
}
