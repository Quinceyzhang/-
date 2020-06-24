package com.luckyframe.project.testmanagmt.testCase.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 用例参数表 test_case_params
 * 
 * @author lyb
 */
public class TestCaseAction extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 用例序号 */
    @Excel(name = "用例序号")
    private Integer testCaseId;

    /** 动作编码 */
    @Excel(name = "动作编码")
    private Integer actionId;

    /** 动作编码 */
    @Excel(name = "动作编码")
    private String action;


    public Integer getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(Integer testCaseId) {
		this.testCaseId = testCaseId;
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
    public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("testCaseId", getTestCaseId())
            .append("actionId", getActionId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
