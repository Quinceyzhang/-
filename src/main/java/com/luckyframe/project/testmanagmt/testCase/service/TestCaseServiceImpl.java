package com.luckyframe.project.testmanagmt.testCase.service;

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
import com.luckyframe.project.testmanagmt.action.domain.ActionTime;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCase;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCaseAction;
import com.luckyframe.project.testmanagmt.testCase.mapper.TestCaseMapper;

/**
 * 用例信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class TestCaseServiceImpl implements ITestCaseService
{
    @Autowired
    private TestCaseMapper testCaseMapper;
    /**
     * 查询用例信息集合
     * 
     * @param post 用例信息
     * @return 用例信息集合
     */
    @Override
    public List<TestCase> selectTestCaseList(TestCase testCase)
    {
        return testCaseMapper.selectTestCaseList(testCase);
    }

    /**
     * 查询所有用例
     * 
     * @return 用例列表
     */
    @Override
    public List<TestCase> selectTestCaseAll()
    {
        return testCaseMapper.selectTestCaseAll();
    }

    /**
     * 通过用例ID查询用例信息
     * 
     * @param postId 用例ID
     * @return 角色对象信息
     */
    @Override
    public TestCase selectTestCaseById(Long testCaseId)
    {
        return testCaseMapper.selectTestCaseById(testCaseId);
    }

    /**
     * 批量删除用例信息
     * 
     * @param ids 需要删除的数据ID
     */
    @Override
    public int deleteTestCaseByIds(String ids) throws BusinessException
    {
        Long[] testCaseIds = Convert.toLongArray(ids);
        for(Long testCaseId:testCaseIds){
        	testCaseMapper.deleteActions(Integer.parseInt(testCaseId.toString()));
        }
        return testCaseMapper.deleteTestCaseByIds(testCaseIds);
    }

    /**
     * 新增保存用例信息
     * 
     * @param post 用例信息
     * @return 结果
     */
    @Override
    public int insertTestCase(TestCase testCase)
    {
    	testCase.setCreateBy(ShiroUtils.getLoginName());
    	
        return testCaseMapper.insertTestCase(testCase);
    }

    /**
     * 修改保存用例信息
     * 
     * @param post 用例信息
     * @return 结果
     */
    @Override
    public int updateTestCase(TestCase testCase)
    {
    	testCase.setUpdateBy(ShiroUtils.getLoginName());
        return testCaseMapper.updateTestCase(testCase);
    }

    /**
     * 校验用例名称是否唯一
     * 
     * @param post 用例信息
     * @return 结果
     */
    @Override
    public String checkTestCaseNameUnique(TestCase testCase)
    {
        long testCaseId = StringUtils.isNull(testCase.getTestCaseId()) ? -1L : testCase.getTestCaseId();
        TestCase info = testCaseMapper.checkTestCaseNameUnique(testCase.getTestCaseName());
        if (StringUtils.isNotNull(info) && info.getTestCaseId() != testCaseId)
        {
            return UserConstants.POST_NAME_NOT_UNIQUE;
        }
        return UserConstants.POST_NAME_UNIQUE;
    }


	@Override
	public List<ActionTime> selectActionsList(Integer testCaseId) {
		return testCaseMapper.selectActionsList(testCaseId);
	}

	@Override
	public void deleteActions(Integer testCaseId) {
		testCaseMapper.deleteActions(testCaseId);
		
	}

	@Override
	public int insertActions(TestCaseAction actions) {
		testCaseMapper.insertActions(actions);
		return 1;
	}

	@Override
	public List<Action> selectActionListForTestCase(Long testCaseId) {
		return testCaseMapper.selectActionListForTestCase(testCaseId);
	}

	@Override
	public int isOccupy(String ids) {
		String[] testCaseIds=Convert.toStrArray(ids);
		int occupy =0;
		for(String testCaseId:testCaseIds){
			occupy = occupy + Integer.parseInt(testCaseMapper.testCaseIsOccupy(testCaseId).get("count"));
		}
		return occupy;
	}

}
