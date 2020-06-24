package com.luckyframe.project.testmanagmt.action.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 用例表 test_case
 * 
 * @author lyb
 */
public class Action extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 
     * 动作序号 */
    @Excel(name = "动作序号")
    private Integer actionId;


    /** 动作名称 */
    @Excel(name = "动作名称")
    private String actionName;
    
    /** 标志 */
    private Boolean flag;


    public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
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
            .append("actionId", getActionId())
            .append("actionName", getActionName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .append("flag", getFlag())
            .toString();
    }


}
