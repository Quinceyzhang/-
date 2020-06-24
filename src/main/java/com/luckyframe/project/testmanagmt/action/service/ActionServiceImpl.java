package com.luckyframe.project.testmanagmt.action.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luckyframe.common.constant.UserConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.system.post.domain.Post;
import com.luckyframe.project.system.post.domain.PostParams;
import com.luckyframe.project.system.post.mapper.PostMapper;
import com.luckyframe.project.system.user.mapper.UserPostMapper;
import com.luckyframe.project.testmanagmt.action.domain.Action;
import com.luckyframe.project.testmanagmt.action.domain.ActionParams;
import com.luckyframe.project.testmanagmt.action.mapper.ActionMapper;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCase;


/**
 * 动作信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class ActionServiceImpl implements IActionService
{
    @Autowired
    private ActionMapper actionMapper;
    /**
     * 查询动作信息集合
     * 
     * @param post 动作信息
     * @return 动作信息集合
     */
    @Override
    public List<Action> selectActionList(Action action)
    {
        return actionMapper.selectActionList(action);
    }

    /**
     * 查询所有动作
     * 
     * @return 动作列表
     */
    @Override
    public List<Action> selectActionAll()
    {
        return actionMapper.selectActionAll();
    }

    /**
     * 通过动作ID查询动作信息
     * 
     * @param postId 动作ID
     * @return 角色对象信息
     */
    @Override
    public Action selectActionById(Integer actionId)
    {
        return actionMapper.selectActionById(actionId);
    }

    /**
     * 批量删除动作信息
     * 
     * @param ids 需要删除的数据ID
     */
    @Override
    public int deleteActionByIds(String ids) throws BusinessException
    {
    	String[] actionIds=Convert.toStrArray(ids);
        for(String actionId:actionIds){
        	actionMapper.deletePostParams(actionId);
        }
        return actionMapper.deleteActionByIds(actionIds);
    }

    /**
     * 新增保存动作信息
     * 
     * @param post 动作信息
     * @return 结果
     */
    @Override
    public int insertAction(Action action)
    {
    	action.setCreateBy(ShiroUtils.getLoginName());
    	
        return actionMapper.insertAction(action);
    }

    /**
     * 修改保存动作信息
     * 
     * @param post 动作信息
     * @return 结果
     */
    @Override
    public int updateAction(Action action)
    {
    	action.setUpdateBy(ShiroUtils.getLoginName());
        return actionMapper.updateAction(action);
    }

    /**
     * 校验动作名称是否唯一
     * 
     * @param post 动作信息
     * @return 结果
     */
    @Override
    public String checkActionNameUnique(Action action)
    {
        long actionId = StringUtils.isNull(action.getActionId()) ? -1L : action.getActionId();
        Action info = actionMapper.checkActionNameUnique(action.getActionName());
        if (StringUtils.isNotNull(info) && info.getActionId() != actionId)
        {
            return UserConstants.POST_NAME_NOT_UNIQUE;
        }
        return UserConstants.POST_NAME_UNIQUE;
    }

	@Override
	public List<ActionParams> selectActionParamsList(Integer actionId) {
		return actionMapper.selectActionParamsList(actionId);
	}

	@Override
	public void deleteActionParams(Integer actionId) {
		actionMapper.deleteActionParams(actionId);
	}

	@Override
	public int insertActionParams(ActionParams params) {
		actionMapper.insertActionParams(params);
		return 1;
	}

	@Override
	public int isOccupy(String ids) {
		String[] actionIds=Convert.toStrArray(ids);
		int occupy =0;
		for(String actionId:actionIds){
			occupy = occupy + Integer.parseInt(actionMapper.actionIsOccupy(actionId).get("count"));
		}
		return occupy;
	}

}
