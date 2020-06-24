package com.luckyframe.project.system.post.service;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.luckyframe.project.system.post.domain.FileIndex;
import com.luckyframe.project.system.post.mapper.FileIndexMapper;

@Service
public class FileIndexServiceImpl implements FileIndexService {
	
	@Resource
	private FileIndexMapper mapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FileIndex record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(FileIndex record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public FileIndex selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteFile(Integer id) {
		
		FileIndex fileIndex = mapper.selectByPrimaryKey(id);
		if(fileIndex != null){
			File file = new File(fileIndex.getFilePath());
			if(file.exists()){
				file.delete();
			}
		}
		mapper.deleteByPrimaryKey(id);
	}

}
