package com.luckyframe.project.system.post.service;

import com.luckyframe.project.system.post.domain.FileIndex;

public interface FileIndexService {
	
	int deleteByPrimaryKey(Integer id);

    int insert(FileIndex record);
    
    int updateByPrimaryKey(FileIndex record);
    
    FileIndex selectByPrimaryKey(Integer id);
    
    void deleteFile(Integer id);
}
