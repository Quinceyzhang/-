package com.luckyframe.project.testmanagmt.projectExe.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 测试项目管理表 sys_project
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
public class GetResult
{
	
	/** 项目ID */
	private Integer projectId;
	/** 方案ID */
	private Integer planId;
	/** 用例ID */
	private Integer testCaseId;
	/** 动作ID */
	private Integer actionId;
	/** 参数ID */
	private Integer paramsId;
	/** 参数类型 */
	private String type;
	/** 参数值 */
	private String result;
	/** 最大值 */
	private String maxValue;
	/** 最小值 */
	private String minValue;
	/** 计算值 */
	private String calcResult;
	/** 是否达标 */
	private String isQualified;



	public Integer getProjectId() {
		return projectId;
	}



	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getResult() {
		return result;
	}



	public void setResult(String result) {
		this.result = result;
	}



	public String getMaxValue() {
		return maxValue;
	}



	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}



	public String getMinValue() {
		return minValue;
	}



	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}



	public String getCalcResult() {
		return calcResult;
	}



	public void setCalcResult(String calcResult) {
		this.calcResult = calcResult;
	}



	public Integer getParamsId() {
		return paramsId;
	}



	public void setParamsId(Integer paramsId) {
		this.paramsId = paramsId;
	}



	public String getIsQualified() {
		return isQualified;
	}



	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}



	public Integer getPlanId() {
		return planId;
	}



	public void setPlanId(Integer planId) {
		this.planId = planId;
	}



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

}
