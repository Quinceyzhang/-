package com.luckyframe.project.testmanagmt.action.domain;
/**
 * 用例表 test_case
 * 
 * @author lyb
 */
public class ActionTime
{
    /** 
     * 动作序号 */
    private Integer actionId;

    /** 动作名称 */
    private String actionName;
    
    /** 动作名称 */
    private String createBy;
    
    /** 动作名称 */
    private String createTime;
    
    /** 备注 */
    private String remark;
    

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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
