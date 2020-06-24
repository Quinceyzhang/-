package com.luckyframe.project.testmanagmt.projectExe.domain;

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
public class ProjectExe extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 项目ID */
	private Integer projectId;
	/** 项目名称 */
	private String projectName;
	/** 方案ID */
	private Integer planId;
	/** 方案名称 */
	private String planName;
	/** 用例ID */
	private Integer testCaseId;
	/** 用例名称 */
	private String testCaseName;
	/** 动作ID */
	private Integer actionId;
	/** 动作名称 */
	private String actionName;
	/** 仪器ID */
	private Integer postId;
	/** 仪器名称 */
	private String postName;
	/** 参数ID */
	private Integer paramsId;
	/** 参数名称 */
	private String paramsName;
	/** 数据来源  1：手动 0：自动 */
	private Integer source;
	/** 测试结果 */
	private String result;
	/** 计算结果 */
	private String calcResult;
	/** 是否达标 */
	private String isQualified;
	/** 是否达标 */
	private String normalValue;
	
	
	public Integer getProjectId() {
		return projectId;
	}



	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}



	public String getProjectName() {
		return projectName;
	}



	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}



	public Integer getPlanId() {
		return planId;
	}



	public void setPlanId(Integer planId) {
		this.planId = planId;
	}



	public String getPlanName() {
		return planName;
	}



	public void setPlanName(String planName) {
		this.planName = planName;
	}



	public Integer getTestCaseId() {
		return testCaseId;
	}



	public void setTestCaseId(Integer testCaseId) {
		this.testCaseId = testCaseId;
	}
	
	
	public String getTestCaseName() {
		return testCaseName;
	}



	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}



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



	public Integer getPostId() {
		return postId;
	}



	public void setPostId(Integer postId) {
		this.postId = postId;
	}



	public String getPostName() {
		return postName;
	}



	public void setPostName(String postName) {
		this.postName = postName;
	}



	public Integer getParamsId() {
		return paramsId;
	}



	public void setParamsId(Integer paramsId) {
		this.paramsId = paramsId;
	}



	public String getParamsName() {
		return paramsName;
	}



	public void setParamsName(String paramsName) {
		this.paramsName = paramsName;
	}



	public Integer getSource() {
		return source;
	}



	public void setSource(Integer source) {
		this.source = source;
	}



	public String getResult() {
		return result;
	}



	public void setResult(String result) {
		this.result = result;
	}

	public String getCalcResult() {
		return calcResult;
	}
	public void setCalcResult(String calcResult) {
		this.calcResult = calcResult;
	}

	public String getIsQualified() {
		return isQualified;
	}



	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}
	public String getNormalValue() {
		return normalValue;
	}
	public void setNormalValue(String normalValue) {
		this.normalValue = normalValue;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            
            .append("planId", getPlanId())
            .append("planName", getPlanName())
            
            .append("testCaseId", getTestCaseId())
            .append("testCaseName", getTestCaseName())
            
            .append("actionId", getActionId())
            .append("actionName", getActionName())
            
            .append("postId", getPostId())
            .append("postName", getPostName())
            
            .append("paramsId", getParamsId())
            .append("paramsName", getParamsName())
            
            .append("source", getSource())
            .append("result", getResult())

            .append("calcResult", getCalcResult())
            .append("isQualified", getIsQualified())
            .append("normalValue", getNormalValue())
            .toString();
    }








}
