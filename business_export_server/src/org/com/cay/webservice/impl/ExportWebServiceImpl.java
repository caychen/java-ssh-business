package org.com.cay.webservice.impl;

import java.util.Set;

import javax.jws.WebService;

import org.com.cay.entity.Export;
import org.com.cay.entity.ExportProduct;
import org.com.cay.service.IExportService;
import org.com.cay.webservice.IExportWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.alibaba.fastjson.JSON;

//继承SpringBeanAutowiringSupport可以实现自动spring注入
@WebService
public class ExportWebServiceImpl extends SpringBeanAutowiringSupport implements IExportWebService {

	public ExportWebServiceImpl() {
	}

	@Autowired
	private IExportService exportService;

	/*
	 * public void setExportService(IExportService exportService) {
	 * this.exportService = exportService; }
	 */
	// @WebMethod
	@Override
	public String exportE(String jsonStr) throws Exception {
		System.out.println(jsonStr);
		System.out.println("exportService: " + exportService);

		Export export = JSON.parseObject(jsonStr, Export.class);

		// System.out.println(export.getDestinationPort() + "," +
		// export.getProducts().iterator().next().getCnumber());
		// System.out.println(export.getDestinationPort());

		exportService.saveOrUpdate(export);

		// 海关人员根据自己的审核标准，决定是否通过，最终以json数据作为响应的结果
		/**
		 * { exportId:"", state:"", remark:"", products:[ { exportProductId:"",
		 * tax:"" }, { exportProductId:"", tax:"" } ] }
		 * 
		 */

		StringBuffer sb = new StringBuffer();
		sb.append("{exportId:\"").append(export.getExportId()).append("\",");
		sb.append("state:\"").append(2).append("\",");
		sb.append("remark:\"").append("申报成功").append("\",");
		sb.append("products:[");

		Set<ExportProduct> exportSet = export.getProducts();
		double i = 1;
		for (ExportProduct ep : exportSet) {
			sb.append("{exportProductId:\"").append(ep.getExportProductId()).append("\",");
			sb.append("tax:\"").append(10 + (i++) * 0.4).append("\"},");
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]}");

		System.out.println(sb.toString());

		return sb.toString();
	}
}
