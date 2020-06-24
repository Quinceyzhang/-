package com.luckyframe.project.testmanagmt.testCase.mapper;

import java.util.List;
import java.util.Map;

import com.luckyframe.project.testmanagmt.action.domain.Action;
import com.luckyframe.project.testmanagmt.action.domain.ActionTime;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCase;
import com.luckyframe.project.testmanagmt.testCase.domain.TestCaseAction;

import org.springframework.stereotype.Component;

/**
 * 用例信息 数据层
 * 
 * @author ruoyi
 */
@Component
public interface TestCaseMapper
{
    /**
     * 查询用例数据集合
     * 
     * @param TestCase 用例信息
     * @return 用例数据集合
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
     * @param testCaseId 用例ID
     * @return 角色对象信息
     */
    TestCase selectTestCaseById(Long testCaseId);

    /**
     * 批量删除用例信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTestCaseByIds(Long[] ids);

    /**
     * 修改用例信息
     * 
     * @param testCase 用例信息
     * @return 结果
     */
    int updateTestCase(TestCase testCase);

    /**
     * 新增用例信息
     * 
     * @param post 用例信息
     * @return 结果
     */
    int insertTestCase(TestCase testCase);

    /**
     * 校验用例名称
     * 
     * @param postName 用例名称
     * @return 结果
     */
    TestCase checkTestCaseNameUnique(String testCaseName);

    /**
     * 查询用例参数
     * 
     * @param postId 用例编码
     * @return 结果
     */

	List<ActionTime> selectActionsList(Integer testCaseId);

	void deleteActions(Integer testCaseId);

	void insertActions(TestCaseAction actions);

	List<Action> selectActionListForTestCase(Long testCaseId);

	Map<String,String> testCaseIsOccupy(String testCaseId);
}
