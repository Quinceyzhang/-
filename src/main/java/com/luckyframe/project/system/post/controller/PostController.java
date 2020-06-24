package com.luckyframe.project.system.post.controller;

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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.post.domain.Post;
import com.luckyframe.project.system.post.domain.PostParams;
import com.luckyframe.project.system.post.service.FileIndexService;
import com.luckyframe.project.system.post.service.IPostService;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCaseParams.domain.ProjectCaseParams;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlanCase;

import cn.hutool.core.util.StrUtil;

/**
 * 仪器信息操作处理
 * 
 * @author lyb
 */
@Controller
@RequestMapping("/system/post")
public class PostController extends BaseController
{
    @Autowired
    private IPostService postService;
    
    @Autowired
    private FileIndexService fileIndexService;

    @RequiresPermissions("system:post:view")
    @GetMapping()
    public String operlog()
    {
        return "system/post/post";
    }

    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Post post)
    {
        startPage();
        List<Post> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "仪器管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Post post)
    {
        List<Post> list = postService.selectPostList(post);
        ExcelUtil<Post> util = new ExcelUtil<>(Post.class);
        return util.exportExcel(list, "仪器数据");
    }

    @RequiresPermissions("system:post:remove")
    @Log(title = "仪器管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
        	if(postService.isOccupy(ids)==0){
        		return toAjax(postService.deletePostByIds(ids));
	        }else{
	        	return AjaxResult.error("该仪器已经被动作占用，不能删除！");
	        }
            
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            return error(e.getMessage());
        }
    }

    /**
     * 新增仪器
     */
    @GetMapping("/add")
    public String add()
    {
        return "system/post/add";
    }

    /**
     * 新增保存仪器
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "仪器管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Post post)
    {
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改仪器
     */
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap)
    {
        mmap.put("post", postService.selectPostById(postId));
        return "system/post/edit";
    }

    /**
     * 修改保存仪器
     */
    @RequiresPermissions("system:post:edit")
    @Log(title = "仪器管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Post post)
    {
        return toAjax(postService.updatePost(post));
    }

    /**
     * 校验仪器名称
     */
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public String checkPostNameUnique(Post post)
    {
        return postService.checkPostNameUnique(post);
    }

    /**
     * 校验仪器编码
     */
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public String checkPostCodeUnique(Post post)
    {
        return postService.checkPostCodeUnique(post);
    }
    
    /**
     * 仪器参数
     */
    @RequiresPermissions("system:post:list")
    @RequestMapping("/paramsList")
    public void list(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        /*startPage();
        List<Post> list = postService.selectPostList(post);
        return getDataTable(list);*/
        
        response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		String postIdStr = request.getParameter("postId");
		long postId = 0;
		// 得到客户端传递的查询参数
		if (StringUtils.isNotEmpty(postIdStr)) {
			postId = Integer.parseInt(postIdStr);
		}
		
		List<PostParams> postParamsList=postService.selectPostParamsList(postId);
		
		System.out.println("参数子表");
		System.out.println(postParamsList);
		// 转换成json字符串
		JSONArray recordJson= JSONArray.parseArray(JSON.toJSONString(postParamsList,SerializerFeature.WriteNullStringAsEmpty));
		pw.print(recordJson);
    }
    
    
    /**
     * 新增参数
     */
    @GetMapping("/editParams/{postId}")
    public String editParams(@PathVariable("postId") Long postId, ModelMap mmap)
	{
		Post post = postService.selectPostById(postId);
		PostParams postParams = new PostParams();
		postParams.setPostId(postId);
		
		List<PostParams> postParamsList=postService.selectPostParamsList(postId);
		
		if(postParamsList.size()==0){
			postParams.setPostId(postId);
			postParams.setParamsId("");
			postParams.setParamsName("");

			postParamsList.add(postParams);
		}
		
		/*for(PostParams params:postParamsList){
			if(StrUtil.isBlank(params.getParamsName())){
				params.setParamsName("无");
			}
		}*/
		
		System.out.println(postParamsList);
		mmap.put("postParamsList", postParamsList);
		mmap.put("post", post);
		
		return "system/post/postParams";
	}
    
    
	/**
	 * 查询仪器参数列表（带flag）
	 */
	//@RequiresPermissions("system:post:edit")
	@PostMapping("/paramsForPostlist")
	@ResponseBody
	public TableDataInfo paramsList(Post post)
	{
		startPage();
		//获取项目下的用例集合
		List<ProjectCaseParams> paramsList = postService.selectParamsListForPost(post.getPostId());
		
		return getDataTable(paramsList);
	}
    
    
	/**
	 * 保存参数到仪器
	 * @param params 参数对象集
	 * @author lyb
	 * @date 2020年4月27日
	 */
	@RequiresPermissions("system:post:edit")
	@Log(title = "仪器参数", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/addParams",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult addParams(@RequestBody Map map)
	{
		int resultCount=0;
		long postId = Integer.parseInt((String) map.get("postId")) ;
		List<Map> maps = (List<Map>)map.get("row");
		
		/*System.out.println(map);
		System.out.println(postId);
		System.out.println(postParams);*/
		postService.deletePostParams(postId);
		
		if(maps.size()==0){
			return toAjax(1);
		}
		
		for(Map mapa:maps){
			PostParams params = new PostParams();
			params.setPostId(postId);
			params.setParamsId(String.valueOf(mapa.get("paramsId")));
			params.setParamsName(String.valueOf(mapa.get("paramsName")));
			resultCount = resultCount + postService.insertPostParams(params);
		}
		
		return toAjax(resultCount);
	}
}
