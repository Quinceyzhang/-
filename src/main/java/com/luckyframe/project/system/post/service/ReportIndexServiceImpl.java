package com.luckyframe.project.system.post.service;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.luckyframe.project.system.post.domain.ReportIndex;
import com.luckyframe.project.system.post.mapper.ReportIndexMapper;

@Service
public class ReportIndexServiceImpl implements ReportIndexService {
	
	@Resource
	private ReportIndexMapper mapper;

	@Override
	public int insert(ReportIndex record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public ReportIndex selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ReportIndex record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public ReportIndex selectByProjectId(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByProjectId(id);
	}

	@Override
	public void deleteReport(Integer id) {
		ReportIndex index = mapper.selectByProjectId(id);
		if(index != null){
			File file = new File(index.getFilePath());
			if(file.exists()){
				file.delete();
			}
			mapper.deleteByPrimaryKey(index.getId());
		}
		
	}

}
