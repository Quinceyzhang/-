package com.luckyframe.project.testmanagmt.action.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 动作参数表 test_case_params
 * 
 * @author lyb
 */
public class ActionParams
{
    /** 动作序号 */
    @Excel(name = "动作序号")
    private Integer actionId;

    /** 参数编码 */
    @Excel(name = "参数编码")
    private String paramsId;

    /** 参数名称 */
    @Excel(name = "参数名称")
    private String paramsName;
    
    /** 参数值*/
    @Excel(name = "参数值")
    private String paramsValue;
    
    /** 参数 单位*/
    @Excel(name = "参数单位")
    private String paramsUnit;
    
    /** 仪器编码 */
    @Excel(name = "仪器编码")
    private String postId;

    /** 仪器名称 */
    @Excel(name = "仪器名称")
    private String postName;
    
    /** 备注*/
    @Excel(name = "备注")
    private String remark;

    
    public Integer getActionId()
    {
        return actionId;
    }

    public void setActionId(Integer actionId)
    {
        this.actionId = actionId;
    }

    public String getParamsId()
    {
        return paramsId;
    }

    public void setParamsId(String paramsId)
    {
        this.paramsId = paramsId;
    }

    public String getPostId()
    {
        return postId;
    }

    public void setPostId(String postId)
    {
        this.postId = postId;
    }
    
    public String getPostName()
    {
        return postName;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }
    
    public String getParamsName()
    {
        return paramsName;
    }

    public void setParamsName(String paramsName)
    {
        this.paramsName = paramsName;
    }

    public String getParamsValue()
    {
        return paramsValue;
    }

    public void setParamsValue(String paramsValue)
    {
        this.paramsValue = paramsValue;
    }
    
    public String getParamsUnit()
    {
        return paramsUnit;
    }

    public void setParamsUnit(String paramsUnit)
    {
        this.paramsUnit = paramsUnit;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("actionId", getActionId())
            .append("paramsId", getParamsId())
            .append("paramsName", getParamsName())
            .append("paramsValue", getParamsValue())
            .append("paramsUnit", getParamsUnit())
            .append("postId", getPostId())
            .append("postName", getPostName())
            .append("remark", getRemark())
            .toString();
    }
}
