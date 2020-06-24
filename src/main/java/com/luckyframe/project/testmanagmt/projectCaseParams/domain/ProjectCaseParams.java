package com.luckyframe.project.testmanagmt.projectCaseParams.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.project.domain.Project;

import java.util.Date;

/**
 * 用例公共参数表 project_case_params
 * 
 * @author luckyframe
 * @date 2019-03-21
 */
public class ProjectCaseParams extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 参数ID */
	@Excel(name = "参数ID")
	private Integer paramsId;
	/** 参数名称 */
	@Excel(name = "参数名称")
	private String paramsName;
	/** 类型 */
	@Excel(name = "类型")
	private String type;
	/** 参数值 */
	//@Excel(name = "参数值")
	private String paramsValue;
	/** 参数最小值 */
	@Excel(name = "参数最小值")
	private String paramsMinValue;
	/** 参数最大值 */
	@Excel(name = "参数最大值")
	private String paramsMaxValue;
	/** 参数单位 */
	@Excel(name = "参数单位")
	private String paramsUnit;
	/** 创建者 */
	@Excel(name = "创建者")
	private String createBy;
	/** 创建时间 */
	@Excel(name = "创建时间")
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	@Excel(name = "备注")
	private String remark;

	private Boolean flag;

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public void setParamsId(Integer paramsId) 
	{
		this.paramsId = paramsId;
	}

	public Integer getParamsId() 
	{
		return paramsId;
	}
	public void setParamsName(String paramsName) 
	{
		this.paramsName = paramsName;
	}

	public String getParamsName() 
	{
		return paramsName;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getParamsValue() {
		return paramsValue;
	}

	public void setParamsValue(String paramsValue) {
		this.paramsValue = paramsValue;
	}
	
	public void setParamsMinValue(String paramsMinValue) 
	{
		this.paramsMinValue = paramsMinValue;
	}

	public String getParamsMaxValue() 
	{
		return paramsMaxValue;
	}
	public void setParamsMaxValue(String paramsMaxValue) 
	{
		this.paramsMaxValue = paramsMaxValue;
	}

	public String getParamsMinValue() 
	{
		return paramsMinValue;
	}

	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}
	public String getParamsUnit() {
		return paramsUnit;
	}

	public void setParamsUnit(String paramsUnit) {
		this.paramsUnit = paramsUnit;
	}
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("paramsId", getParamsId())
            .append("paramsName", getParamsName())
            .append("paramsValue", getParamsValue())
            .append("paramsMinValue", getParamsMinValue())
            .append("paramsMaxValue", getParamsMaxValue())
            .append("paramsUnit", getParamsUnit())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("flag", getFlag())
            .append("type", getType())
            .toString();
    }
}
