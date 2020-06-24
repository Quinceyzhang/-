package com.luckyframe.project.testmanagmt.projectExe.service;

import java.util.List;
import java.util.Map;

import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.domain.ProjectEquipments;
import com.luckyframe.project.testmanagmt.projectExe.domain.ProjectExe;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;

/**
 * 测试项目管理 服务层
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
public interface IProjectExeService 
{
	/**
     * 查询测试项目信息
     * 
     * @param projectId 
     * @return 
     */
	List<ProjectExe> selectDataListByProjectId(Integer projectId);

	/**
     * 保存测试结果
     * 
     * @param projectExe
     * @return 
     */
	int insertProjectresult(ProjectExe projectExe);
	/**
     * 设置项目状态
     * 
     * @param projectId 
     * @return 
     */
	void setProjectStatusAs(Integer projectId);

	List<Project> selectTestProjectList(Project project);

	List<Project> selectOverProjectList(Project project);

	int calculateProject(Integer projectId);

	void setProjectStatusAsEnd(Integer projectId);

	List<ProjectExe> selectResultListByProjectId(Integer projectId);

	int resetResult(Map map);
	
}
