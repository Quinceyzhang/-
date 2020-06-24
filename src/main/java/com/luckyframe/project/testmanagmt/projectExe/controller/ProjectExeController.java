package com.luckyframe.project.testmanagmt.projectExe.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.post.controller.FileController;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.domain.ProjectEquipments;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testmanagmt.action.domain.ActionParams;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectExe.domain.ProjectExe;
import com.luckyframe.project.testmanagmt.projectExe.service.IProjectExeService;

import cn.hutool.core.util.StrUtil;

/**
 * 测试项目管理 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Controller
@RequestMapping("/testmanagmt/projectExe")
public class ProjectExeController extends BaseController
{
	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private IProjectExeService projectExeService;
	
	@RequiresPermissions("testmanagmt:projectExe:view")
	@GetMapping()
	public String projectExe()
	{
	    return "testmanagmt/projectExe/projectExe";
	}
	
	/**
	 * 查询测试项目管理列表
	 */
	@RequiresPermissions("testmanagmt:projectExe:view")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Project project)
	{
		startPage();
		
		System.out.print(project);
		
        //List<Project> list = projectService.selectProjectList(project);
        List<Project> list = projectExeService.selectTestProjectList(project);
		return getDataTable(list);
	}
	
	/**
	 * 项目执行
	 */
	@GetMapping("/projectExe/{projectId}")
	public String projectExe(@PathVariable("projectId") Integer projectId, ModelMap mmap)
	{
		Project project=projectService.selectProjectById(projectId);
		//List<ProjectEquipments> equipmentsList = projectService.selectEquipmentsByProjectId(projectId);
		
		
		List<ProjectExe> dataList = projectExeService.selectDataListByProjectId(projectId);
		mmap.put("dataList", dataList);
		//mmap.put("equipmentsList", equipmentsList);
		mmap.put("project", project);
	    return "testmanagmt/projectExe/dataEntry";
	}
	
	
	/**
	 * 保存项目测试结果
	 */
	//@RequiresPermissions("testmanagmt:projectExe:resultSave")
	@Log(title = "项目测试结果", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/resultSave",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult resultSave(@RequestBody List<ProjectExe> resultList)
	{
		int result=0;
		Integer projectId = 0;
		projectId = resultList.get(0).getProjectId();

		
		for(ProjectExe projectExe:resultList){
			result=result+projectExeService.insertProjectresult(projectExe);
		}

		projectExeService.setProjectStatusAs(projectId);
		return toAjax(result);
	}
	
	
	/**
	 * 项目执行
	 * @param projectId
	 * @author lyb
	 * @date 2020年5月8日
	 */
	@RequiresPermissions("testmanagmt:projectExe:edit")
	@Log(title = "项目执行", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/calculate",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult calculate(@RequestBody Map map)
	{
		int resultCount=0;
		//Integer projectId = Integer.parseInt((String) map.get("projectId")) ;
		
		Integer projectId = (Integer)map.get("projectId");
		resultCount = projectExeService.calculateProject(projectId);
		if(resultCount>0){
			projectExeService.setProjectStatusAsEnd(projectId);
		}
		return toAjax(resultCount);
	}
	
	
	
	/**
	 * 项目执行结果
	 */
	@GetMapping("/testResult")
	public String testResult()
	{
	    return "testmanagmt/testResult/testResult";
	}
	
	/**
	 * 查询测试完成项目列表
	 */
	@RequiresPermissions("testmanagmt:testResult:view")
	@PostMapping("/testResultList")
	@ResponseBody
	public TableDataInfo testResultList(Project project)
	{
		startPage();
		
		System.out.print(project);
		
        List<Project> list = projectExeService.selectOverProjectList(project);
		return getDataTable(list);
	}
	
	/**
	 * 项目计算详情
	 */
	@GetMapping("/testResultDetail/{projectId}")
	public String testResultDetail(@PathVariable("projectId") Integer projectId, ModelMap mmap)
	{
		Project project=projectService.selectProjectById(projectId);
		List<ProjectEquipments> equipmentlist = projectService.selectEquipmentsByProjectId(projectId);
		
		List<ProjectExe> resultList = projectExeService.selectResultListByProjectId(projectId);
		
		
		for(ProjectExe projectExe:resultList){
			if("1".equals(projectExe.getIsQualified())){
				projectExe.setIsQualified("合格");
			}else{
				projectExe.setIsQualified("不合格");
			}
		}
		
		
		mmap.put("project", project);
		mmap.put("equipmentlist", equipmentlist);
		mmap.put("dataList", resultList);
	    return "testmanagmt/testResult/testResultDetail";
	} 
	
	
	/**
	 * 修改检测结果
	 */
	//@RequiresPermissions("testmanagmt:projectExe:edit")
	@PostMapping("/resetResult")
	@ResponseBody
	public AjaxResult resetResult(@RequestBody Map map)
	{
		
		
		System.out.print(map);
		
        int resultCount = projectExeService.resetResult(map);
        if(resultCount != 0){
        	Integer projectId = Integer.parseInt((String) map.get("projectId"));
        	System.out.print(projectId);
        	
        	resultCount = projectExeService.calculateProject(projectId);
        	projectService.saveResult(projectId);
        }
        return toAjax(resultCount);
	}
}
