package com.luckyframe.project.testmanagmt.testCase.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 用例表 test_case
 * 
 * @author lyb
 */
public class TestCase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 
     * 用例序号 */
    @Excel(name = "用例序号")
    private Long testCaseId;


    /** 用例名称 */
    @Excel(name = "用例名称")
    private String testCaseName;

    /** 项目id */
    @Excel(name = "方案id")
    private Long planId;
    
    /** 项目名称 */
    @Excel(name = "方案名称")
    private String planName;
    /** 项目名称 */
    @Excel(name = "创建时间")
    private String createTime;
    
    /** 标志 */
    private Boolean flag;
    
    public Long getTestCaseId()
    {
        return testCaseId;
    }

    public void setTestCaseId(Long testCaseId)
    {
        this.testCaseId = testCaseId;
    }

    public String getTestCaseName()
    {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName)
    {
        this.testCaseName = testCaseName;
    }
    
    public Long getPlanId()
    {
        return planId;
    }

    public void setPlanId(Long planId)
    {
        this.planId = planId;
    }

    public String getPlanName()
    {
        return planName;
    }

    public void setPlanName(String planName)
    {
        this.planName = planName;
    }
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("testCaseId", getTestCaseId())
            .append("testCaseName", getTestCaseName())
            .append("planId", getPlanId())
            .append("planName", getPlanName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .append("flag", getFlag())
            .toString();
    }


}
