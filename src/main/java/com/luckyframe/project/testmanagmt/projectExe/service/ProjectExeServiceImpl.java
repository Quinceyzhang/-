package com.luckyframe.project.testmanagmt.projectExe.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.constant.ProjectConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.testmanagmt.projectExe.domain.GetResult;
import com.luckyframe.project.testmanagmt.projectExe.domain.ProjectExe;
import com.luckyframe.project.testmanagmt.projectExe.mapper.ProjectExeMapper;

/**
 * 测试项目管理 服务层实现
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Service
public class ProjectExeServiceImpl implements IProjectExeService 
{
	@Autowired
	private ProjectExeMapper projectExeMapper;

	@Override
	public List<ProjectExe> selectDataListByProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		return projectExeMapper.selectDataListByProjectId(projectId);
	}

	@Override
	public int insertProjectresult(ProjectExe projectExe) {
		projectExeMapper.insertProjectresult(projectExe);
		return 1;
	}

	@Override
	public void setProjectStatusAs(Integer projectId) {
		projectExeMapper.setProjectStatusAs(projectId);
		
	}

	@Override
	public List<Project> selectTestProjectList(Project project) {
		// TODO Auto-generated method stub
		return projectExeMapper.selectTestProjectList(project);
	}

	@Override
	public List<Project> selectOverProjectList(Project project) {
		// TODO Auto-generated method stub
		return projectExeMapper.selectOverProjectList(project);
	}

	@Override
	public int calculateProject(Integer projectId) {
		int result = 0;
		List<GetResult> getList = projectExeMapper.getProjectResult(projectId);
		
		for(GetResult getResult:getList){
			if(getResult.getType().equals("0")){
				
				if(Float.parseFloat(getResult.getResult())>Float.parseFloat(getResult.getMaxValue())){
					getResult.setCalcResult("测试结果偏大");
					getResult.setIsQualified("0");
				}else if(Float.parseFloat(getResult.getResult())<Float.parseFloat(getResult.getMinValue())){
					getResult.setCalcResult("测试结果偏小");
					getResult.setIsQualified("0");
				}else{
					getResult.setCalcResult("测试结果在正常范围");
					getResult.setIsQualified("1");
				}
			}else{
				if(getResult.getResult().equals(getResult.getMinValue())){
					getResult.setCalcResult("合格");
					getResult.setIsQualified("1");
				}else{
					getResult.setCalcResult("不合格");
					getResult.setIsQualified("0");
				}
			}
			projectExeMapper.insertCalcResult(getResult);
			result++;
		}
		
		
		return result;
	}

	@Override
	public void setProjectStatusAsEnd(Integer projectId) {
		projectExeMapper.setProjectStatusAsEnd(projectId);
		
	}

	@Override
	public List<ProjectExe> selectResultListByProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		return projectExeMapper.selectResultListByProjectId(projectId);
	}

	@Override
	public int resetResult(Map map) {
		projectExeMapper.resetResult(map);
		return 1;
	}

}
