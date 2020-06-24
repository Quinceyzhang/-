package com.luckyframe.project.system.post.mapper;

import com.luckyframe.project.system.post.domain.FileIndex;

public interface FileIndexMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileIndex record);

    int insertSelective(FileIndex record);

    FileIndex selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileIndex record);

    int updateByPrimaryKey(FileIndex record);
}