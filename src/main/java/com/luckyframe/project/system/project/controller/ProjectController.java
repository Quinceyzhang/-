package com.luckyframe.project.system.project.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
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
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.luckyframe.common.constant.ProjectConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.DateUtils;
import com.luckyframe.common.utils.ObjectUtil;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.file.FileUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.post.domain.ReportIndex;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.domain.ProjectEquipments;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectExe.domain.ProjectExe;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanService;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCase;

import cn.hutool.core.util.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 测试项目管理 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Controller
@RequestMapping("/system/project")
public class ProjectController extends BaseController
{
	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private IProjectPlanService projectPlanService;
	
	@RequiresPermissions("system:project:view")
	@GetMapping()
	public String project()
	{
	    return "system/project/project";
	}
	
	/**
	 * 查询测试项目管理列表
	 */
	@RequiresPermissions("system:project:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Project project)
	{
		startPage();
		
		System.out.print(project);
		
        List<Project> list = projectService.selectProjectList(project);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出测试项目管理列表
	 */
	@RequiresPermissions("system:project:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Project project)
    {
    	List<Project> list = projectService.selectProjectList(project);
        ExcelUtil<Project> util = new ExcelUtil<>(Project.class);
        return util.exportExcel(list, "project");
    }
	
	/**
	 * 新增测试项目管理
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		//List<ProjectPlan> list = projectPlanService.selectProjectPlanList(new ProjectPlan());
		//mmap.put("planList", list);
	    return "system/project/add";
	}
	  
	/**
	 * 修改方案列表
	 */
	@GetMapping("/planList/{projectId}")
	public String projectPlan(@PathVariable("projectId") Integer projectId, ModelMap mmap)
	{
		Project project=projectService.selectProjectById(projectId);
		
		/*List<ProjectPlan> planList = projectPlanService.selectProjectPlanList(new ProjectPlan());

		mmap.put("planList", planList);*/
		mmap.put("project", project);
	    return "system/project/projectPlans";
	}
	
	/**
	 * 查询方案列表
	 */
	@RequiresPermissions("system:project:list")
	@PostMapping("/projectPlanList")
	@ResponseBody
	public TableDataInfo planList(Project project)
	{
		startPage();
		
		System.out.print(project.getProjectId());
		
		List<ProjectPlan> list = projectService.selectProjectPlanList(project.getProjectId());
		return getDataTable(list);
	}
    /**
	 * 保存方案到项目
	 * @param testCases 用例对象集
	 * @author lyb
	 * @date 2020年4月27日
	 */
	@RequiresPermissions("system:project:edit")
	@Log(title = "检测项目管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/addPlans",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult addPlans(@RequestBody Map map)
	{
		int resultCount=0;
		Integer projectId = Integer.parseInt((String) map.get("projectId")) ;
		List<Map> maps = (List<Map>)map.get("row");
		
		System.out.println(map);
		System.out.println(projectId);
	
		projectService.deletePlanByProjectId(projectId);
		
		if(maps.size()==0){
			return toAjax(1);
		}
		
		for(Map mapa:maps){
			Map<String,Integer> plan = new HashMap<String,Integer>();
			plan.put("projectId", projectId);
			plan.put("planId", Integer.parseInt((mapa.get("planId")).toString()));
			resultCount = resultCount + projectService.insertPlan(plan);
		}
		
		return toAjax(resultCount);
	}
	
	/**
	 * 新增保存项目
	 */
	@RequiresPermissions("system:project:add")
	@Log(title = "检测项目管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Project project)
	{		
		System.out.print(project);
		int result = projectService.insertProject(project);
		
		Integer projectId = projectService.searchNewId();
		ProjectEquipments equipment = new ProjectEquipments();
		equipment.setProjectId(projectId);
		
		projectService.insertProjectEquipments(equipment);
		return toAjax(result+projectId);
	}

	/**
	 * 修改项目
	 */
	@GetMapping("/edit/{projectId}")
	public String edit(@PathVariable("projectId") Integer projectId, ModelMap mmap)
	{
		Project project = projectService.selectProjectById(projectId);
		mmap.put("project", project);
	    return "system/project/edit";
	}
	
	/**
	 * 修改保存项目
	 */
	@RequiresPermissions("system:project:edit")
	@Log(title = "检测项目管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Project project)
	{	
		if(!PermissionUtils.isProjectPermsPassByProjectId(project.getProjectId())){
			return error("没有此项目保存权限");
		}
		
		return toAjax(projectService.updateProject(project));
	}
	
	/**
	 * 删除项目
	 */
	@RequiresPermissions("system:project:remove")
	@Log(title = "检测项目管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
        try
        {
    		return toAjax(projectService.deleteProjectByIds(ids));
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
	}
	
	
	/**
	 * 复制测试项目管理
	 */
	@RequiresPermissions("system:project:add")
	@Log(title = "检测项目管理", businessType = BusinessType.INSERT)
	@PostMapping( "/copyProject")
	@ResponseBody
	public AjaxResult copyProject(String ids)
	{
        try
        {   
        	//接收要复制项目ID
        	String[] projectIds=Convert.toStrArray(ids);
        	Integer projectId = Integer.parseInt(projectIds[0]);
        	
        	//查询项目信息
        	Project project = projectService.selectProjectById(projectId);
        	//构造新项目
        	Project newProject = new Project();
        	newProject.setProjectName(project.getProjectName()+"(复制)");
        	newProject.setRemark(project.getRemark());
        	//插入新项目并取出ID
        	int result = projectService.insertProject(newProject);
        	Integer newProjectId = projectService.searchNewId();
        	//复制项目设备并插入设备表
        	List<ProjectEquipments> equipmentList = projectService.selectEquipmentsByProjectId(projectId);
        	ProjectEquipments equipment = equipmentList.get(0);
    		equipment.setProjectId(newProjectId);
    		result = result + projectService.insertProjectEquipments(equipment);
    		//查询项目方案ID
    		List<Map<String,Integer>> maps = projectService.selectPlanIdByProjectId(projectId);
    		
    		if(maps.size()==0){
    			return toAjax(result);
    		}
    		for(Map<String,Integer> mapa:maps){
    			Map<String,Integer> plan = new HashMap<String,Integer>();
    			plan.put("projectId", newProjectId);
    			plan.put("planId", mapa.get("planId"));
    			result = result + projectService.insertPlan(plan);
    		}
        	
    		return toAjax(result);
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
	}
	
	
    /**
     * 校验项目名称唯一性
     */
    @PostMapping("/checkProjectNameUnique")
    @ResponseBody
    public String checkProjectNameUnique(Project project)
    {
        return projectService.checkProjectNameUnique(project);
    }
    
    
    
	/**
	 * 查询项目设备子表
	 */
	@RequiresPermissions("system:project:list")
	@PostMapping("/equipmentList")
	@RequestMapping(value = "/equipmentList")
	public void list(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String projectIdStr = request.getParameter("projectId");
		int projectId = 0;
		// 得到客户端传递的查询参数
		if (StringUtils.isNotEmpty(projectIdStr)) {
			projectId = Integer.parseInt(projectIdStr);
		}
		List<ProjectEquipments> list = projectService.selectEquipmentsByProjectId(projectId);
		
		
		// 转换成json字符串
		JSONArray recordJson= JSONArray.parseArray(JSON.toJSONString(list,SerializerFeature.WriteNullStringAsEmpty));
		pw.print(recordJson);
	}
	
	
	
	/**
	 * 修改项目设备
	 */
	@GetMapping("/editEquipment/{projectId}")
	public String editEquipment(@PathVariable("projectId") Integer projectId, ModelMap mmap)
	{
		Project project=projectService.selectProjectById(projectId);
		
		ProjectEquipments projectEquipments = new ProjectEquipments();
		projectEquipments.setProjectId(projectId);
		
		List<ProjectEquipments> equipmentsList = projectService.selectEquipmentsByProjectId(projectId);
		
		if(equipmentsList.size()==0){
			projectEquipments.setEquipment("");
			projectEquipments.setModel("");
			projectEquipments.setProductor("");
			projectEquipments.setCount(0);
			projectEquipments.setTestBy("");
			projectEquipments.setTestTime("");
			projectEquipments.setRemark("");
			equipmentsList.add(projectEquipments);
		}

		mmap.put("equipmentsList", equipmentsList);
		mmap.put("project", project);
	    return "system/project/projectEquipments";
	}
	
	/**
	 * 修改保存项目设备管理
	 */
	@RequiresPermissions("system:project:edit")
	@Log(title = "检测项目管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/editEquipmentSave",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult editEquipmentSave(@RequestBody ProjectEquipments equipment)
	{
		
		int result=0;
		//projectService.deleteProjectEquipmentsByIds(equipment);

		//result=result+projectService.insertProjectEquipments(equipment);
		result=result+projectService.updateProjectEquipments(equipment);
		return toAjax(result);
	}
	
	
	/**
	 * 项目详情
	 */
	@GetMapping("/detail/{projectId}")
	public String detail(@PathVariable("projectId") Integer projectId, ModelMap mmap)
	{
		Project project=projectService.selectProjectById(projectId);
		
		List<ProjectPlan> planList = projectPlanService.selectProjectPlanList(new ProjectPlan());
 		List<ProjectEquipments> equipmentsList = projectService.selectEquipmentsByProjectId(projectId);
		mmap.put("planList", planList);
		mmap.put("equipmentsList", equipmentsList);
		mmap.put("project", project);
	    return "system/project/projectDetail";
	}
	
	/**
	 * 查询测试项目方案列表
	 */
	@RequiresPermissions("system:project:list")
	@PostMapping("/planListForProject")
	@ResponseBody
	public TableDataInfo planListForProject(Project project)
	{
		startPage();
		
		System.out.print(project);
		
		List<ProjectPlan> list = projectService.selectPlanList(project.getProjectId());
		return getDataTable(list);
	}
	
	
	@PostMapping("/selectProjectByMonth")
	@ResponseBody
	public Map selectProjectByMonth()
	{
		Map result = new HashMap();
		List<String> monthLst = DateUtils.getMonthLst(LocalDate.now().toString().substring(0,4));
		result.put("month", monthLst);
		List valueLst = new ArrayList();
		List<Map> list = projectService.selectProjectByMonth();
		if(list != null && list.size() >0){
			for(String m : monthLst){
				boolean flag = false;
				for(Map x : list){
					if(m.equals(x.get("months"))){
						valueLst.add(x.get("count"));
						flag = true;
						break;
					}
				}
				if(!flag){
					valueLst.add("0");
				}
			}
		}
		result.put("value", valueLst);
		return result;
	}
	
	@PostMapping("/selectIsSuccessProjectByMonth")
	@ResponseBody
	public Map selectIsSuccessProjectByMonth()
	{
		Map result = new HashMap();
		List<String> monthLst = DateUtils.getMonthLst(LocalDate.now().toString().substring(0,4));
		result.put("month", monthLst);
		List successValueLst = new ArrayList();
		List failValueLst = new ArrayList();
		
		List<Map> successList = projectService.selectSuccessProjectByMonth();
		List<Map> failList = projectService.selectFailProjectByMonth();
		
		if(successList != null && successList.size() >0){
			for(String m : monthLst){
				boolean flag = false;
				for(Map x : successList){
					if(m.equals(x.get("months"))){
						successValueLst.add(x.get("count"));
						flag = true;
						break;
					}
				}
				if(!flag){
					successValueLst.add("0");
				}
			}
		}
		
		if(failList != null && failList.size() >0){
			for(String m : monthLst){
				boolean flag = false;
				for(Map x : failList){
					if(m.equals(x.get("months"))){
						failValueLst.add(x.get("count"));
						flag = true;
						break;
					}
				}
				if(!flag){
					failValueLst.add("0");
				}
			}
		}
		
		result.put("successValue", successValueLst);
		result.put("failValue", failValueLst);
		return result;
	}
	
	@PostMapping("/selectPostByName")
	@ResponseBody
	public List<Map> selectPostByName()
	{
		List<Map> result = projectService.selectPostByName();
		return result;
	}
	
	/**
	 * 保存项目结果
	 * @throws Exception 
	 */
	@GetMapping("/saveResult/{id}")
	@ResponseBody
	public AjaxResult saveResult(@PathVariable("id")Integer projectId){
		
		try {
			projectService.saveResult(projectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxResult.success("成功");
	}
	
	
	@GetMapping("/empty")
	public String addEmpty()
	{
		//List<ProjectPlan> list = projectPlanService.selectProjectPlanList(new ProjectPlan());
		//mmap.put("planList", list);
	    return "system/empty/empty";
	}
	
	@GetMapping("/interface")
	public String interfaceView()
	{
	    return "testmanagmt/interface/interface";
	}
	
}
