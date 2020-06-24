package com.luckyframe.project.testmanagmt.action.mapper;

import java.util.List;
import java.util.Map;

import com.luckyframe.project.testmanagmt.action.domain.Action;
import com.luckyframe.project.testmanagmt.action.domain.ActionParams;

import org.springframework.stereotype.Component;

/**
 * 动作信息 数据层
 * 
 * @author ruoyi
 */
@Component
public interface ActionMapper
{
    /**
     * 查询动作数据集合
     * 
     * @param TestCase 动作信息
     * @return 动作数据集合
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
     * @param testCaseId 动作ID
     * @return 角色对象信息
     */
    Action selectActionById(Integer actionId);

    /**
     * 批量删除动作信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteActionByIds(String[] ids);

    /**
     * 修改动作信息
     * 
     * @param testCase 动作信息
     * @return 结果
     */
    int updateAction(Action action);

    /**
     * 新增动作信息
     * 
     * @param post 动作信息
     * @return 结果
     */
    int insertAction(Action action);

    /**
     * 校验动作名称
     * 
     * @param postName 动作名称
     * @return 结果
     */
    Action checkActionNameUnique(String actionName);

    /**
     * 查询动作参数
     * 
     * @param postId 动作编码
     * @return 结果
     */
	List<ActionParams> selectActionParamsList(Integer actionId);

	void deleteActionParams(Integer actionId);

	void insertActionParams(ActionParams params);

	Map<String,String> actionIsOccupy(String actionId);

	void deletePostParams(String actionId);
}
