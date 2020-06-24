package com.luckyframe.project.system.post.service;

import com.luckyframe.project.system.post.domain.ReportIndex;

public interface ReportIndexService {
	
	int insert(ReportIndex record);
	
	ReportIndex selectByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(ReportIndex record);
	
	int deleteByPrimaryKey(Integer id);
	
	ReportIndex selectByProjectId(Integer id);
	
	void deleteReport(Integer id);

}
