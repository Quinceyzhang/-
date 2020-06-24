package com.luckyframe.project.testmanagmt.testCase.domain;

/**
 * 用例表 test_case
 * 
 * @author lyb
 */
public class TestCaseTime
{
    /** 
     * 用例序号 */
    private Long testCaseId;
    
    /** 用例名称 */
    private String testCaseName;

    /** 项目名称 */
    private String createBy;
    
    public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/** 项目名称 */
    private String createTime;
    
    /** 标志 */
    private String remark;

	public Long getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(Long testCaseId) {
		this.testCaseId = testCaseId;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}
