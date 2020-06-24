package com.luckyframe.project.testmanagmt.action.service;

import java.util.List;
import com.luckyframe.project.system.post.domain.Post;
import com.luckyframe.project.system.post.domain.PostParams;
import com.luckyframe.project.testmanagmt.action.domain.Action;
import com.luckyframe.project.testmanagmt.action.domain.ActionParams;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCase;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 动作信息 服务层
 * 
 * @author ruoyi
 */
@Component
@Primary
public interface IActionService
{
    /**
     * 查询动作信息集合
     * 
     * @param action 动作信息
     * @return 动作信息集合
     */
    List<Action> selectActionList(Action action);

    /**
     * 查询所有动作
     * 
     * @return 动作列表
     */
    List<Action> selectActionAll();


    /**
     * 通过动作ID查询动作信息
     * 
     * @param postId 动作ID
     * @return 角色对象信息
     */
    Action selectActionById(Integer actionId);

    /**
     * 批量删除动作信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    int deleteActionByIds(String ids) throws Exception;

    /**
     * 新增保存动作信息
     * 
     * @param post 动作信息
     * @return 结果
     */
    int insertAction(Action action);

    /**
     * 修改保存动作信息
     * 
     * @param post 动作信息
     * @return 结果
     */
    int updateAction(Action action);


    /**
     * 校验动作名称
     * 
     * @param post 动作信息
     * @return 结果
     */
    String checkActionNameUnique(Action action);
    
    /**
     * 查询动作参数
     * 
     * @param postId 动作id
     * @return 结果
     */
    List<ActionParams> selectActionParamsList(Integer actionId);

	void deleteActionParams(Integer actionId);

	int insertActionParams(ActionParams params);

	int isOccupy(String ids);
	
}
