package com.luckyframe.project.testmanagmt.testCase.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.post.domain.Post;
import com.luckyframe.project.system.post.domain.PostParams;
import com.luckyframe.project.system.post.domain.PostParamsList;
import com.luckyframe.project.system.post.service.IPostService;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.testmanagmt.action.domain.Action;
import com.luckyframe.project.testmanagmt.action.domain.ActionTime;
import com.luckyframe.project.testmanagmt.action.service.IActionService;
import com.luckyframe.project.testmanagmt.projectCaseParams.domain.ProjectCaseParams;
import com.luckyframe.project.testmanagmt.projectCaseParams.service.IProjectCaseParamsService;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanService;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCase;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCaseAction;
import com.luckyframe.project.testmanagmt.testCase.service.ITestCaseService;

import cn.hutool.core.util.StrUtil;

/**
 * 用例信息操作处理
 * 
 * @author lyb
 */
@Controller
@RequestMapping("/testmanagmt/testCase")
public class TestCaseController extends BaseController
{
    @Autowired
    private ITestCaseService testCaseService;
    
    @Autowired
    private IProjectPlanService projectPlanService;

    @RequiresPermissions("testmanagmt:testCase:view")
    @GetMapping()
    public String operlog(ModelMap mmap)
    {
        return "testmanagmt/testCase/testCase";
    }
    
    @RequiresPermissions("testmanagmt:testCase:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestCase testCase)
    {
        startPage();
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        return getDataTable(list);
    }
    
    @Log(title = "用例管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("testmanagmt:testCase:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestCase testCase)
    {
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        ExcelUtil<TestCase> util = new ExcelUtil<>(TestCase.class);
        return util.exportExcel(list, "用例数据");
    }
    
    /**
     * 删除用例
     */
    @RequiresPermissions("testmanagmt:testCase:remove")
    @Log(title = "用例管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
        	if(testCaseService.isOccupy(ids)==0){
        		return toAjax(testCaseService.deleteTestCaseByIds(ids));
	        }else{
	        	return AjaxResult.error("该用例已经被方案占用，不能删除！");
	        }
            
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }
    
    /**
     * 新增用例
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
    	
    	/*List<ProjectPlan> plans=projectPlanService.selectProjectPlanAll(0);
        mmap.put("plans", plans);
        mmap.put("defaultPlanId", plans.get(0).getPlanId());*/
        return "testmanagmt/testCase/add";
    }
    
    /**
     * 新增保存用例
     */
    @RequiresPermissions("testmanagmt:testCase:add")
    @Log(title = "用例管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestCase testCase)
    {
    	System.out.print(testCase);
        return toAjax(testCaseService.insertTestCase(testCase));
    }
    
    /**
     * 修改用例
     */
    @GetMapping("/edit/{testCaseId}")
    public String edit(@PathVariable("testCaseId") Long testCaseId, ModelMap mmap)
    {
        mmap.put("testCase", testCaseService.selectTestCaseById(testCaseId));
        return "testmanagmt/testCase/edit";
    }

    /**
     * 修改保存用例
     */
    @RequiresPermissions("testmanagmt:testCase:edit")
    @Log(title = "用例管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestCase testCase)
    {
        return toAjax(testCaseService.updateTestCase(testCase));
    }
    
    /**
     * 校验用例名称
     */
    @PostMapping("/checkTestCaseNameUnique")
    @ResponseBody
    public String checkTestCaseNameUnique(TestCase testCase)
    {
        return testCaseService.checkTestCaseNameUnique(testCase);
    }
    
	/**
	 * 查询测试用例子表
	 */
	//@RequiresPermissions("testmanagmt:testCase:actionList")
	@PostMapping("/actionList")
	@RequestMapping(value = "/actionList")
	public void list(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String planIdStr = request.getParameter("testCaseId");
		int planId = 0;
		// 得到客户端传递的查询参数
		if (StringUtils.isNotEmpty(planIdStr)) {
			planId = Integer.parseInt(planIdStr);
		}
		List<ActionTime> list = testCaseService.selectActionsList(planId);
		
		
		// 转换成json字符串
		JSONArray recordJson= JSONArray.parseArray(JSON.toJSONString(list,SerializerFeature.WriteNullStringAsEmpty));
		pw.print(recordJson);
	}
    
    
    /**
     * 新增动作
     */
    @GetMapping("/editActions/{testCaseId}")
    public String editActions(@PathVariable("testCaseId") Long testCaseId, ModelMap mmap)
	{
    	TestCase testCase = testCaseService.selectTestCaseById(testCaseId);
    	ActionTime action = new ActionTime();
    	
		
		List<ActionTime> actionsList=testCaseService.selectActionsList(Integer.parseInt(testCaseId.toString()));
		
		if(actionsList.size()==0){
			action.setActionId(0);;
			action.setActionName("");
			action.setRemark("");

			actionsList.add(action);
		}
		
		
		System.out.println(actionsList);
		mmap.put("testCaseActionsList", actionsList);
		mmap.put("testCase", testCase);
		
		return "testmanagmt/testCase/testCaseActions";
	}
    
    /**
	 * 查询所有动作
	 * @param
	 * @author lyb
	 * @date 2020年4月27日
	 */
    @RequiresPermissions("testmanagmt:testCase:edit")
    @PostMapping("/actionsList")
    @ResponseBody
    public TableDataInfo actionsList(TestCase testCase)
    {
        startPage();
        List<Action> list = testCaseService.selectActionListForTestCase(testCase.getTestCaseId());
        //List<Action> list = actionService.selectAllActionList();
        //System.out.println(list);
        
        return getDataTable(list);
    }
    
    
	/**
	 * 保存动作到用例
	 * @param params 参数对象集
	 * @author lyb
	 * @date 2020年4月27日
	 */
	@RequiresPermissions("testmanagmt:testCase:edit")
	@Log(title = "用例动作", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/addActions",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult addActions(@RequestBody Map map)
	{
		int resultCount=0;
		Integer testCaseId = Integer.parseInt((String) map.get("testCaseId")) ;
		List<Map> maps = (List<Map>)map.get("row");
		
		/*System.out.println(map);
		System.out.println(postId);
		System.out.println(postParams);*/
		testCaseService.deleteActions(testCaseId);
		
		if(maps.size()==0){
			return toAjax(1);
		}
		
		for(Map mapa:maps){
			TestCaseAction params = new TestCaseAction();
			params.setTestCaseId(testCaseId);
			params.setActionId(Integer.parseInt(String.valueOf(mapa.get("actionId"))));
			
			resultCount = resultCount + testCaseService.insertActions(params);
		}
		
		return toAjax(resultCount);
	}

}
