package com.luckyframe.project.testmanagmt.testCase.service;

import java.util.List;
import com.luckyframe.project.system.post.domain.Post;
import com.luckyframe.project.system.post.domain.PostParams;
import com.luckyframe.project.testmanagmt.action.domain.Action;
import com.luckyframe.project.testmanagmt.action.domain.ActionTime;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCase;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCaseAction;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 用例信息 服务层
 * 
 * @author ruoyi
 */
@Component
@Primary
public interface ITestCaseService
{
    /**
     * 查询用例信息集合
     * 
     * @param testCase 用例信息
     * @return 用例信息集合
     */
    List<TestCase> selectTestCaseList(TestCase testCase);

    /**
     * 查询所有用例
     * 
     * @return 用例列表
     */
    List<TestCase> selectTestCaseAll();


    /**
     * 通过用例ID查询用例信息
     * 
     * @param postId 用例ID
     * @return 角色对象信息
     */
    TestCase selectTestCaseById(Long testCaseId);

    /**
     * 批量删除用例信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    int deleteTestCaseByIds(String ids) throws Exception;

    /**
     * 新增保存用例信息
     * 
     * @param post 用例信息
     * @return 结果
     */
    int insertTestCase(TestCase testCase);

    /**
     * 修改保存用例信息
     * 
     * @param post 用例信息
     * @return 结果
     */
    int updateTestCase(TestCase testCase);


    /**
     * 校验用例名称
     * 
     * @param post 用例信息
     * @return 结果
     */
    String checkTestCaseNameUnique(TestCase testCase);
    
    /**
     * 查询用例参数
     * 
     * @param postId 用例id
     * @return 结果
     */
    List<ActionTime> selectActionsList(Integer testCaseId);

	void deleteActions(Integer testCaseId);

	int insertActions(TestCaseAction actions);

	List<Action> selectActionListForTestCase(Long testCaseId);

	int isOccupy(String ids);
	
}
