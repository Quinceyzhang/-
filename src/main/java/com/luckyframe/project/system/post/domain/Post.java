package com.luckyframe.project.system.post.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 仪器表 sys_post
 * 
 * @author ruoyi
 */
public class Post extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仪器序号 */
    @Excel(name = "仪器序号")
    private Long postId;

    /** 仪器编码 */
    @Excel(name = "仪器编码")
    private String postCode;

    /** 仪器名称 */
    @Excel(name = "仪器名称")
    private String postName;
    
    /** 仪器型号 */
    @Excel(name = "仪器型号")
    private String postModel;

    /** 仪器厂家 */
    @Excel(name = "生产厂家")
    private String postFactory;

    /** 仪器排序 */
    @Excel(name = "仪器排序")
    private String postSort;
    
    /** 附件名称 */
    private String fileName;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 用户是否存在此仪器标识 默认不存在 */
    private boolean flag = false;

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    public String getPostCode()
    {
        return postCode;
    }

    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }

    public String getPostName()
    {
        return postName;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }
    
    public String getPostModel()
    {
        return postModel;
    }

    public void setPostModel(String postModel)
    {
        this.postModel = postModel;
    }
    
    public String getPostFactory()
    {
        return postFactory;
    }

    public void setPostFactory(String postFactory)
    {
        this.postFactory = postFactory;
    }

    public String getPostSort()
    {
        return postSort;
    }

    public void setPostSort(String postSort)
    {
        this.postSort = postSort;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("postId", getPostId())
            .append("postCode", getPostCode())
            .append("postName", getPostName())
            .append("postModel", getPostModel())
            .append("postFactory", getPostFactory())
            .append("postSort", getPostSort())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }


}
