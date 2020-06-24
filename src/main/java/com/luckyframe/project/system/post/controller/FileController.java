package com.luckyframe.project.system.post.controller;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luckyframe.common.constant.ProjectCaseParamsConstants;
import com.luckyframe.common.constant.ProjectConstants;
import com.luckyframe.common.utils.ObjectUtil;
import com.luckyframe.common.utils.file.FileUtils;
import com.luckyframe.framework.config.LuckyFrameConfig;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.project.system.post.domain.FileIndex;
import com.luckyframe.project.system.post.domain.ReportIndex;
import com.luckyframe.project.system.post.service.FileIndexService;
import com.luckyframe.project.system.post.service.ReportIndexService;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.domain.ProjectEquipments;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testmanagmt.projectCaseParams.domain.ProjectCaseParams;
import com.luckyframe.project.testmanagmt.projectCaseParams.service.IProjectCaseParamsService;
import com.luckyframe.project.testmanagmt.projectExe.domain.ProjectExe;
import com.luckyframe.project.testmanagmt.projectExe.service.IProjectExeService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

@RestController
@RequestMapping(value="/system/file",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FileController {

	@Autowired
    private LuckyFrameConfig luckyFrameConfig;
    
    @Autowired
    private FileIndexService fileIndexService;
    
    @Autowired
    private ReportIndexService reportIndexService;
    
    @Autowired
    private IProjectExeService iProjectExeService;
    
    @Autowired
    private IProjectService iProjectService;
    
    @Autowired
    private IProjectCaseParamsService iProjectCaseParamsService;
    
    /**
	 * 上传文件
	 */
	@PostMapping("/upLoad")
	public AjaxResult upLoad(MultipartFile file){
		
		FileIndex fileIndex = new FileIndex();
		
		if(file!=null){
			FileUtils.judeDirExists(new File(luckyFrameConfig.getUploadPath()));
			String fileName = file.getOriginalFilename();
			try {
				String filePath = luckyFrameConfig.getUploadPath() + fileName;
				file.transferTo(new File(filePath));
				
				fileIndex.setFileName(fileName);
				fileIndex.setFilePath(filePath);
				fileIndexService.insert(fileIndex);
				System.out.println(">>>Return file id = "+fileIndex.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return AjaxResult.success(String.valueOf(fileIndex.getId()));
	}
	
	/**
	 * 下载文件
	 * @throws IOException 
	 */
	@GetMapping(value="/downLoad/{id}")
	public void downLoad(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException{
		
		FileInputStream fis = null; // 文件输入流
		BufferedInputStream bis = null;
		
		try {
			FileIndex fileIndex = this.fileIndexService.selectByPrimaryKey(id);
			if(fileIndex != null){
				File file = new File(fileIndex.getFilePath());
				if(file.exists()){
					response.setContentType("application/x-download;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Content-Disposition",
							"attachment;fileName=" + java.net.URLEncoder.encode(fileIndex.getFileName(), "UTF-8"));
					
					OutputStream os = null; // 输出流
					byte[] buffer = new byte[1024];
					os = response.getOutputStream();
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						os.flush();
						i = bis.read(buffer);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis != null){
				fis.close();
			}
			if(bis != null){
				bis.close();
			}
		}
		
	}
	
	/**
	 * 生成doc报告
	 * @throws Exception 
	 */
	@GetMapping("/generateReport/{id}")
	public AjaxResult generateReport(@PathVariable("id")Integer projectId){
		
		try {
			Project project = this.iProjectService.selectProjectById(projectId);
			//判断项目状态
			if(ProjectConstants.PROJECT_DONE.equals(String.valueOf(project.getStatus()))){
				//获取项目测试结果数据
				List<ProjectExe> resultLst = this.iProjectExeService.selectResultListByProjectId(projectId);
				//如果结果不为空
				if(resultLst != null && resultLst.size() > 0){
					//查询项目设备信息
					List<ProjectEquipments> equipments = this.iProjectService.selectEquipmentsByProjectId(projectId);
					//如果设备信息不为空
					if(equipments != null && equipments.size() > 0){
						//开始生成word文件
						Map dataMap = new HashMap();
						
						setReportPropertise(equipments,dataMap);
						setTestResult(resultLst, dataMap);
						ObjectUtil.fillNullValue(dataMap);
						
						Configuration configuration = new Configuration();
						configuration.setDefaultEncoding("UTF-8");
						
						String dlPath = this.luckyFrameConfig.getDownloadPath();
						String ulPath = this.luckyFrameConfig.getUploadPath();
						File dlFile = new File(dlPath);
						FileUtils.judeDirExists(dlFile);
						FileUtils.judeDirExists(new File(ulPath));
						
						configuration.setDirectoryForTemplateLoading(dlFile);
						Template template = configuration.getTemplate("ReportTemplate.ftl");
						String xmlPath = dlPath + dataMap.get("serial") + ".xml";
						File outFile = new File(xmlPath);
						Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
						template.process(dataMap, out);
						out.flush();
						out.close();
						
						InputStream is = new FileInputStream(new File(dlPath+"license.xml"));
						License aposeLic = new License();
						aposeLic.setLicense(is);
						 
						FileInputStream fis = new FileInputStream(outFile);
						 
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						byte[] inFile = new byte[fis.available()];
						fis.read(inFile);
						Document doc = new Document(new ByteArrayInputStream(inFile));
						doc.save(byteArrayOutputStream,SaveFormat.DOCX);
						 
						FileOutputStream fos = new FileOutputStream(ulPath + "检测报告" + dataMap.get("serial") + ".docx");
						fos.write(byteArrayOutputStream.toByteArray());
						fos.flush();
						fos.close();
						ReportIndex report = new ReportIndex();
						report.setProjectId(projectId);
						report.setFileName("检测报告" + dataMap.get("serial") + ".docx");
						report.setFilePath(ulPath + "检测报告" + dataMap.get("serial") + ".docx");
						this.reportIndexService.deleteReport(projectId);
						this.reportIndexService.insert(report);
						is.close();
						fis.close();
						File xmlFile = new File(xmlPath);
						if(xmlFile.exists()){
							xmlFile.delete();
						}
					}else{
						throw new Exception(this.getClass().getName()+">>No equipments data.");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxResult.success("成功");
	}
	
	private void setReportPropertise(List<ProjectEquipments> lst,Map dataMap){
		String date = LocalDate.now().toString();
		String time = LocalTime.now().toString().substring(0,8).replaceAll(":", "");
		String[] d = date.split("-");
		ProjectEquipments obj = lst.get(0);
		dataMap.put("serial", date.replaceAll("-", "") + time);
		dataMap.put("equipment", obj.getEquipment());
		dataMap.put("model", obj.getModel());
		dataMap.put("trustCompany", obj.getTrustCompany());
		dataMap.put("productor", obj.getProductor());
		dataMap.put("testProperty", obj.getTestProperty());
		dataMap.put("year", d[0]);
		dataMap.put("month", d[1]);
		dataMap.put("day", d[2]);
		dataMap.put("logo", obj.getLogo());
		dataMap.put("sampleGrade", obj.getSampleGrade());
		dataMap.put("count", obj.getCount());
		dataMap.put("sampleNum", obj.getSampleNum());
		dataMap.put("trustCompany", obj.getTrustCompany());
		dataMap.put("trustCompanyAdress", obj.getTrustCompanyAdress());
		dataMap.put("inspectedCompany", obj.getInspectedCompany());
		dataMap.put("inspectedCompanyAdress", obj.getInspectedCompanyAdress());
		dataMap.put("productAdress", obj.getProductAdress());
		dataMap.put("sampleData", obj.getSampleData());
		dataMap.put("testBy", obj.getTestBy());
		dataMap.put("testLocation", obj.getTestLocation());
		dataMap.put("productDate", obj.getProductDate());
		dataMap.put("sampleBasicNum", obj.getSampleBasicNum());
		dataMap.put("testTime", obj.getTestTime());
		dataMap.put("temperature", obj.getTemperature());
		dataMap.put("humidity", obj.getHumidity());
		dataMap.put("sampleDescrip", obj.getSampleDescrip());
		dataMap.put("testBasis", obj.getTestBasis());
		dataMap.put("conclusion", "合格/不合格");
	}
	
	private void setTestResult(List<ProjectExe> lst,Map dataMap){
		boolean flag = true;
		if(lst != null && lst.size() > 0){
			List datalst = new ArrayList();
			for(int i=0;i<lst.size();i++){
				ProjectExe tmp = lst.get(i);
				Map map = new HashMap();
				map.put("resultid", i+1);
				ProjectCaseParams param = this.iProjectCaseParamsService.selectProjectCaseParamsById(tmp.getParamsId());
				if(param != null){
					if(ProjectCaseParamsConstants.RANGE_VALUE.equals(param.getType())){
						String remark = "样品"+param.getParamsName()+"应在"+param.getParamsMinValue()+"~"
					+param.getParamsMaxValue() + param.getParamsUnit() +"范围内";
						map.put("remark", remark);
					}else if(ProjectCaseParamsConstants.VALUE.equals(param.getType())){
						String remark = "样品"+param.getParamsName()+"应为"+param.getParamsMinValue();
						map.put("remark", remark);
					}
				}
				map.put("result", tmp.getResult());
				
				if(ProjectCaseParamsConstants.TEST_FAILED.equals(tmp.getIsQualified())){
					flag = false;
					map.put("final", "不合格");
				}else if(ProjectCaseParamsConstants.TEST_PASSED.equals(tmp.getIsQualified())){
					map.put("final", "合格");
				}
				datalst.add(map);
			}
			dataMap.put("datalst", datalst);
			if(flag){
				dataMap.put("conclusion", "合格");
			}else{
				dataMap.put("conclusion", "不合格");
			}
		}
	}
	
	/**
	 * 下载报告
	 */
	@GetMapping("/downloadReport/{id}")
	public void downloadReport(@PathVariable("id")Integer projectId, HttpServletResponse response){
		
		FileInputStream fis = null; // 文件输入流
		BufferedInputStream bis = null;
		
		try {
			ReportIndex index = this.reportIndexService.selectByPrimaryKey(projectId);
			if(index == null){
				index = this.reportIndexService.selectByProjectId(projectId);
			}
			if(index != null){
				File file = new File(index.getFilePath());
				if(file.exists()){
					response.setContentType("application/x-download;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Content-Disposition",
							"attachment;fileName=" + java.net.URLEncoder.encode(index.getFileName(), "UTF-8"));
					
					OutputStream os = null; // 输出流
					byte[] buffer = new byte[1024];
					os = response.getOutputStream();
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						os.flush();
						i = bis.read(buffer);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
