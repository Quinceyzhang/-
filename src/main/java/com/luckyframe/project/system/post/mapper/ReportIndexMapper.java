package com.luckyframe.project.system.post.mapper;

import com.luckyframe.project.system.post.domain.ReportIndex;

public interface ReportIndexMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReportIndex record);

    int insertSelective(ReportIndex record);

    ReportIndex selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReportIndex record);

    int updateByPrimaryKey(ReportIndex record);
    
    ReportIndex selectByProjectId(Integer id);
}