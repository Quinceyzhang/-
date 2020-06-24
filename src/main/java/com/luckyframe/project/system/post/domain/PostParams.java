package com.luckyframe.project.system.post.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 仪器参数表 post_params
 * 
 * @author lyb
 */
public class PostParams
{
    /** 仪器序号 */
    @Excel(name = "仪器Id")
    private Long postId;

    /** 仪器编码 */
    @Excel(name = "参数类型编码")
    private String paramsId;

    /** 参数名称 */
    @Excel(name = "参数名称")
    private String paramsName;
    
    /** 参数最小值 */
    @Excel(name = "参数最小值")
    private String paramsMinValue;
    
    /** 参数 最大值*/
    @Excel(name = "参数最大值")
    private String paramsMaxValue;
    
    /** 参数 单位*/
    @Excel(name = "参数单位")
    private String paramsUnit;
    
    /** 备注*/
    @Excel(name = "备注")
    private String remark;

    
    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    public String getParamsId()
    {
        return paramsId;
    }

    public void setParamsId(String paramsId)
    {
        this.paramsId = paramsId;
    }

    public String getParamsName()
    {
        return paramsName;
    }

    public void setParamsName(String paramsName)
    {
        this.paramsName = paramsName;
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

    public void setParamsMinValue(String paramsMinValue)
    {
        this.paramsMinValue = paramsMinValue;
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
            .append("postId", getPostId())
            .append("paramsId", getParamsId())
            .append("paramsName", getParamsName())
            .append("paramsMaxValue", getParamsMaxValue())
            .append("paramsMinValue", getParamsMinValue())
            .append("paramsUnit", getParamsUnit())
            .append("remark", getRemark())
            .toString();
    }
}
