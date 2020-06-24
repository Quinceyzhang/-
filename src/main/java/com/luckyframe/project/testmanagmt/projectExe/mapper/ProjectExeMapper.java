package com.luckyframe.project.testmanagmt.projectExe.mapper;

import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.domain.ProjectEquipments;
import com.luckyframe.project.testmanagmt.projectExe.domain.GetResult;
import com.luckyframe.project.testmanagmt.projectExe.domain.ProjectExe;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 测试项目管理 数据层
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Component
public interface ProjectExeMapper 
{
	/**
     * 查询测试项目信息
     * 
     * @param projectId 测试项目ID
     * @return 项目信息
     */
	List<ProjectExe> selectDataListByProjectId(Integer projectId);

	void insertProjectresult(ProjectExe projectExe);

	void setProjectStatusAs(Integer projectId);

	List<Project> selectTestProjectList(Project project);

	List<Project> selectOverProjectList(Project project);

	void calculateProject(Integer projectId);

	void setProjectStatusAsEnd(Integer projectId);

	List<GetResult> getProjectResult(Integer projectId);

	void insertCalcResult(GetResult getResult);

	List<ProjectExe> selectResultListByProjectId(Integer projectId);

	void resetResult(Map map);

}