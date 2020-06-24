package com.luckyframe.project.system.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.dept.domain.Dept;

/**
 * 测试项目管理表 sys_project
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
public class Project extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 项目ID */
	private Integer projectId;
	/** 项目名称 */
	private String projectName;
	/** 项目备注 */
	private String remark;
	/** 项目状态*/
	private Integer Status;
	
	/** 合格否 */
	private String isQualified;
	
	private Boolean flag;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setProjectId(Integer projectId) 
	{
		this.projectId = projectId;
	}

	public Integer getProjectId() 
	{
		return projectId;
	}
	public void setProjectName(String projectName) 
	{
		this.projectName = projectName;
	}

	public String getProjectName() 
	{
		return projectName;
	}
	
	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}
	
	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("isQualified", getIsQualified())
            .toString();
    }



}
