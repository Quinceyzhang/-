package com.luckyframe.project.system.project.mapper;

import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.domain.ProjectEquipments;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试项目管理 数据层
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Component
public interface ProjectMapper 
{
	/**
     * 查询测试项目管理信息
     * 
     * @param projectId 测试项目管理ID
     * @return 测试项目管理信息
     */
	Project selectProjectById(Integer projectId);
	
	/**
     * 查询测试项目管理列表
     * 
     * @param project 测试项目管理信息
     * @return 测试项目管理集合
     */
	List<Project> selectProjectList(Project project);
	
	/**
     * 新增测试项目管理
     * 
     * @param project 测试项目管理信息
     * @return 结果
     */
	int insertProject(Project project);
	
	/**
     * 修改测试项目管理
     * 
     * @param project 测试项目管理信息
     * @return 结果
     */
	int updateProject(Project project);
	
	/**
     * 删除测试项目管理
     * 
     * @param projectId 测试项目管理ID
     * @return 结果
     */
	int deleteProjectById(Integer projectId);
	
	/**
     * 批量删除测试项目管理
     * 
     * @param projectIds 需要删除的数据ID
     * @return 结果
     */
	int deleteProjectByIds(String[] projectIds);
	
	/**
	 * 校验项目名称唯一性
	 * @param projectName 项目名称
	 * @return 返回项目对象
	 * @author Seagull
	 * @date 2019年2月27日
	 */
	Project checkProjectNameUnique(String projectName);

	List<ProjectEquipments> selectEquipmentsByProjectId(int projectId);

	int deleteEquipmentsByProjectId(int projectId);

	int insertProjectEquipments(ProjectEquipments equipments);

	List<ProjectPlan> selectProjectPlanList(Integer projectId);

	void deletePlanByProjectId(Integer projectId);

	void insertPlan(Map<String, Integer> plan);

	List<ProjectPlan> selectPlanList(Integer projectId);
	
	List<Map> selectProjectByMonth();
	
	List<Map> selectSuccessProjectByMonth();
	
	List<Map> selectFailProjectByMonth();
	
	List<Map> selectPostByName();

	List<Map> selectResult(Integer projectId);
	void saveResult(Map map);

	Map<String,Integer> searchNewId();

	void updateProjectEquipments(ProjectEquipments equipment);

	List<Map<String, Integer>> selectPlanIdByProjectId(Integer projectId);

}