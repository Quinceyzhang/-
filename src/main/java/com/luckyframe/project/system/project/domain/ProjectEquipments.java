package com.luckyframe.project.system.project.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;

/**
 * 项目测试用例管理表 project_case
 * 
 * @author lyb
 * @date 2019-02-26
 */
public class ProjectEquipments extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 设备ID */
	@Excel(name = "设备ID")
	private Integer id;
	/** 项目ID */
	@Excel(name = "项目ID")
	private Integer projectId;
	/** 设备 */
	@Excel(name = "设备")
	private String equipment;
	/** 设备型号 */
	@Excel(name = "设备型号")
	private String model;
	/** 生产厂家 */
	@Excel(name = "生产厂家")
	private String productor;
	/** 数量 */
	@Excel(name = "数量")
	private Integer count;
	/** 检测人员 */
	@Excel(name = "检测人员")
	private String testBy;
	/** 检测时间*/
	@Excel(name = "检测时间 ")
	private String testTime;
	/** 备注 */
	@Excel(name = "备注")
	private String remark;
	/** 商标 */
    private String logo;

    private String sampleGrade;

    private String sampleNum;

    private String inspectedCompany;

    private String trustCompany;

    private String testLocation;

    private String productDate;

    private Integer sampleBasicNum;

    private String sampleDescrip;

    private String testBasis;

    private String productAdress;

    private String trustCompanyAdress;

    private String inspectedCompanyAdress;

    private String testProperty;
	
    private String temperature;

    private String humidity;

    private String sampleData;
	
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProductor() {
		return productor;
	}

	public void setProductor(String productor) {
		this.productor = productor;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getTestBy() {
		return testBy;
	}

	public void setTestBy(String testBy) {
		this.testBy = testBy;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getSampleGrade() {
        return sampleGrade;
    }

    public void setSampleGrade(String sampleGrade) {
        this.sampleGrade = sampleGrade == null ? null : sampleGrade.trim();
    }

    public String getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(String sampleNum) {
        this.sampleNum = sampleNum == null ? null : sampleNum.trim();
    }

    public String getInspectedCompany() {
        return inspectedCompany;
    }

    public void setInspectedCompany(String inspectedCompany) {
        this.inspectedCompany = inspectedCompany == null ? null : inspectedCompany.trim();
    }

    public String getTrustCompany() {
        return trustCompany;
    }

    public void setTrustCompany(String trustCompany) {
        this.trustCompany = trustCompany == null ? null : trustCompany.trim();
    }

    public String getTestLocation() {
        return testLocation;
    }

    public void setTestLocation(String testLocation) {
        this.testLocation = testLocation == null ? null : testLocation.trim();
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public Integer getSampleBasicNum() {
        return sampleBasicNum;
    }

    public void setSampleBasicNum(Integer sampleBasicNum) {
        this.sampleBasicNum = sampleBasicNum;
    }

    public String getSampleDescrip() {
        return sampleDescrip;
    }

    public void setSampleDescrip(String sampleDescrip) {
        this.sampleDescrip = sampleDescrip == null ? null : sampleDescrip.trim();
    }

    public String getTestBasis() {
        return testBasis;
    }

    public void setTestBasis(String testBasis) {
        this.testBasis = testBasis == null ? null : testBasis.trim();
    }

    public String getProductAdress() {
        return productAdress;
    }

    public void setProductAdress(String productAdress) {
        this.productAdress = productAdress == null ? null : productAdress.trim();
    }

    public String getTrustCompanyAdress() {
        return trustCompanyAdress;
    }

    public void setTrustCompanyAdress(String trustCompanyAdress) {
        this.trustCompanyAdress = trustCompanyAdress == null ? null : trustCompanyAdress.trim();
    }

    public String getInspectedCompanyAdress() {
        return inspectedCompanyAdress;
    }

    public void setInspectedCompanyAdress(String inspectedCompanyAdress) {
        this.inspectedCompanyAdress = inspectedCompanyAdress == null ? null : inspectedCompanyAdress.trim();
    }

    public String getTestProperty() {
        return testProperty;
    }

    public void setTestProperty(String testProperty) {
        this.testProperty = testProperty == null ? null : testProperty.trim();
    }
	

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getSampleData() {
		return sampleData;
	}

	public void setSampleData(String sampleData) {
		this.sampleData = sampleData;
	}
	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("equipment", getEquipment())
            .append("model", getModel())
            .append("productor", getProductor())
            .append("count", getCount())
            .append("testBy", getTestBy())
            .append("testTime", getTestTime())
            .append("remark", getRemark())  
            .toString();
    }

}
