package org.com.cay.entity;

import java.util.Date;

/**
 * @Description:	PackingListService接口
 * @Author:			传智播客 java学院	传智宋江
 * @Company:		http://java.itcast.cn
 * @CreateDate:		2016-8-15 10:45:57
 */

public class PackingList extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String seller;			
	private String buyer;			
	private String invoiceNo;			//选择
	private Date invoiceDate;			
	private String marks;			
	private String descriptions;			
	private String exportIds;			//报运ID集合
	private String exportNos;			//报运NO集合x,y|z,h
	private Double state;			//0草稿 1已上报
	private String createBy;			
	private String createDept;			
	private Date createTime;			

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getSeller() {
		return this.seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}	
	
	public String getBuyer() {
		return this.buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}	
	
	public String getInvoiceNo() {
		return this.invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}	
	
	public Date getInvoiceDate() {
		return this.invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}	
	
	public String getMarks() {
		return this.marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}	
	
	public String getDescriptions() {
		return this.descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}	
	
	public String getExportIds() {
		return this.exportIds;
	}
	public void setExportIds(String exportIds) {
		this.exportIds = exportIds;
	}	
	
	public String getExportNos() {
		return this.exportNos;
	}
	public void setExportNos(String exportNos) {
		this.exportNos = exportNos;
	}	
	
	public Double getState() {
		return this.state;
	}
	public void setState(Double state) {
		this.state = state;
	}	
	
	
	
}
