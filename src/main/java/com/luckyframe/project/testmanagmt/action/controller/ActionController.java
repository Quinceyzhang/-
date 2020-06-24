package com.luckyframe.project.testmanagmt.action.controller;

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
import com.luckyframe.project.testmanagmt.action.domain.ActionParams;
import com.luckyframe.project.testmanagmt.action.service.IActionService;
import com.luckyframe.project.testmanagmt.projectCaseParams.domain.ProjectCaseParams;
import com.luckyframe.project.testmanagmt.projectCaseParams.service.IProjectCaseParamsService;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanService;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCase;
import com.luckyframe.project.testmanagmt.testCase.service.ITestCaseService;

import cn.hutool.core.util.StrUtil;

/**
 * 用例信息操作处理
 * 
 * @author lyb
 */
@Controller
@RequestMapping("/testmanagmt/action")
public class ActionController extends BaseController
{
	@Autowired
	private IProjectCaseParamsService projectCaseParamsService;
	
	@Autowired
    private IPostService postService;
	
    @Autowired
    private IActionService actionService;
    
    /*@Autowired
    private IProjectPlanService projectPlanService;*/

    @RequiresPermissions("testmanagmt:action:view")
    @GetMapping()
    public String operlog(ModelMap mmap)
    {
    	/*List<ProjectPlan> plans=projectPlanService.selectProjectPlanAll(0);
        mmap.put("plans", plans);
        mmap.put("defaultPlanId", plans.get(0).getPlanId());*/
        return "testmanagmt/action/action";
    }
    
    @RequiresPermissions("testmanagmt:action:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Action action)
    {
        startPage();
        List<Action> list = actionService.selectActionList(action);
        return getDataTable(list);
    }
    
    @Log(title = "动作管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("testmanagmt:action:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Action action)
    {
        List<Action> list = actionService.selectActionList(action);
        ExcelUtil<Action> util = new ExcelUtil<>(Action.class);
        return util.exportExcel(list, "动作数据");
    }
    
    @RequiresPermissions("testmanagmt:action:remove")
    @Log(title = "动作管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try{
	        if(actionService.isOccupy(ids)==0){
	        	return toAjax(actionService.deleteActionByIds(ids));
	        }else{
	        	return AjaxResult.error("该动作已经被用例占用，不能删除！");
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
        return "testmanagmt/action/add";
    }
    
    /**
     * 新增保存用例
     */
    @RequiresPermissions("testmanagmt:action:add")
    @Log(title = "动作管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Action action)
    {
    	//System.out.print(action);
        return toAjax(actionService.insertAction(action));
    }
    
    /**
     * 修改用例
     */
    @GetMapping("/edit/{actionId}")
    public String edit(@PathVariable("actionId") Integer actionId, ModelMap mmap)
    {
        mmap.put("action", actionService.selectActionById(actionId));
        return "testmanagmt/action/edit";
    }

    /**
     * 修改保存用例
     */
    @RequiresPermissions("testmanagmt:action:edit")
    @Log(title = "动作管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Action action)
    {
        return toAjax(actionService.updateAction(action));
    }
    
    /**
     * 校验用例名称
     */
    @PostMapping("/checkActionNameUnique")
    @ResponseBody
    public String checkActionNameUnique(Action action)
    {
        return actionService.checkActionNameUnique(action);
    }
    
    /**
     * 参数子表
     */
    //@RequiresPermissions("testmanagmt:action:paramsList")
    @RequestMapping("/paramsList")
    public void list(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        /*startPage();
        List<Post> list = postService.selectPostList(post);
        return getDataTable(list);*/
        
        response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String actionIdStr = request.getParameter("actionId");
		Integer actionId = 0;
		// 得到客户端传递的查询参数
		if (StringUtils.isNotEmpty(actionIdStr)) {
			actionId = Integer.parseInt(actionIdStr);
		}
		
		List<ActionParams> actionParamsList=actionService.selectActionParamsList(actionId);
		
		System.out.println("参数子表");
		System.out.println(actionParamsList);
		// 转换成json字符串
		JSONArray recordJson= JSONArray.parseArray(JSON.toJSONString(actionParamsList,SerializerFeature.WriteNullStringAsEmpty));
		pw.print(recordJson);
    }
    
    /**
     * 新增参数
     */
    @GetMapping("/editParams/{actionId}")
    public String editParams(@PathVariable("actionId") Integer actionId, ModelMap mmap)
	{
    	Action action = actionService.selectActionById(actionId);
    	ActionParams actionParams = new ActionParams();
    	actionParams.setActionId(actionId);
		
		List<ActionParams> actionParamsList=actionService.selectActionParamsList(actionId);
		
		if(actionParamsList.size()==0){
			actionParams.setActionId(actionId);
			actionParams.setParamsId("");
			actionParams.setParamsName("");

			actionParamsList.add(actionParams);
		}
		
		for(ActionParams params:actionParamsList){
			if(StrUtil.isBlank(params.getParamsName())){
				params.setParamsName("无");
			}
		}
		
		List<ProjectCaseParams> paramsList = projectCaseParamsService.selectProjectCaseParamsList(new ProjectCaseParams());
		//List<PostParamsList> postList = postService.selectAllPostList(actionId);
		
		System.out.println(actionParamsList);
		mmap.put("actionParamsList", actionParamsList);
		mmap.put("action", action);
		mmap.put("paramsList", paramsList);
		//mmap.put("postList", postList);
		
		return "testmanagmt/action/actionParams";
	}
    
    /**
	 * 查询所有仪器参数
	 * @param
	 * @author lyb
	 * @date 2020年4月27日
	 */
    //@RequiresPermissions("testmanagmt:testCase:postParamsList")
    @PostMapping("/postParamsList")
    @ResponseBody
    public TableDataInfo postParamsList(Action action)
    {
        startPage();
        List<PostParamsList> list = postService.selectAllPostList(action.getActionId());
        
        //System.out.println(list);
        
        return getDataTable(list);
    }
    
    
	/**
	 * 保存参数到用例
	 * @param params 参数对象集
	 * @author lyb
	 * @date 2020年4月27日
	 */
	@RequiresPermissions("testmanagmt:action:edit")
	@Log(title = "动作参数", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/addParams",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult addParams(@RequestBody Map map)
	{
		int resultCount=0;
		Integer actionId = Integer.parseInt((String) map.get("actionId")) ;
		List<Map> maps = (List<Map>)map.get("row");
		
		/*System.out.println(map);
		System.out.println(postId);
		System.out.println(postParams);*/
		actionService.deleteActionParams(actionId);
		
		if(maps.size()==0){
			return toAjax(1);
		}
		
		for(Map mapa:maps){
			ActionParams params = new ActionParams();
			params.setActionId(actionId);
			params.setParamsId(String.valueOf(mapa.get("paramsId")));
			params.setParamsName(String.valueOf(mapa.get("paramsName")));
			params.setPostId(String.valueOf(mapa.get("postId")));
			params.setPostName(String.valueOf(mapa.get("postName")));
			params.setParamsUnit(String.valueOf(mapa.get("paramsUnit")));
			resultCount = resultCount + actionService.insertActionParams(params);
		}
		
		return toAjax(resultCount);
	}

}
