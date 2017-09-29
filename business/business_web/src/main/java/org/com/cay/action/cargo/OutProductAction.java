package org.com.cay.action.cargo;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.com.cay.action.BaseAction;
import org.com.cay.entity.ContractProduct;
import org.com.cay.service.cargo.IContractProductService;
import org.com.cay.utils.DownloadUtil;
import org.com.cay.utils.UtilFuns;

public class OutProductAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String inputDate;
	private boolean useTemplate;

	public void setUseTemplate(boolean useTemplate) {
		this.useTemplate = useTemplate;
	}

	private IContractProductService contractProductService;

	public void setContractProductService(IContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String toedit() {
		return "toedit";
	}

	public String print() throws IOException, ParseException {
		int rowNo = 0, cellNo = 0;
		Row row = null;
		Cell cell = null;

		if (!useTemplate) {// 非模版打印
			// 1、创建工作簿
			//Workbook wb = new HSSFWorkbook();//只支持Excel2003版本，单sheet最多65536行，256列，xls扩展名
			//Workbook wb = new XSSFWorkbook();//支持Excel2007以上的版本，单sheet支持1048576行，16384列，xlsx扩展名
			
			//SXSSFWorkbook可以将产生的一部分对象从内存中转移到磁盘
			//默认转移对象的个数为100
			//如果SXSSFWorkbook(int num)指定参数时，则表示当内存中对象的个数达到num个时就转移到磁盘上
			Workbook wb = new SXSSFWorkbook();//支持Excel2007以上的版本，可以解决百万数据的poi，但不支持模版，要求poi的相关jar在3.0以上 

			// 2、创建工作表
			Sheet sheet = wb.createSheet();

			// 设置列宽
			sheet.setColumnWidth(cellNo + 0, 26 * 256);
			sheet.setColumnWidth(cellNo + 1, 11 * 256);
			sheet.setColumnWidth(cellNo + 2, 29 * 256);
			sheet.setColumnWidth(cellNo + 3, 12 * 256);
			sheet.setColumnWidth(cellNo + 4, 15 * 256);
			sheet.setColumnWidth(cellNo + 5, 10 * 256);
			sheet.setColumnWidth(cellNo + 6, 10 * 256);
			sheet.setColumnWidth(cellNo + 7, 8 * 256);

			// 3、创建行对象
			// 大标题
			row = sheet.createRow(rowNo++);
			row.setHeightInPoints(36);// 设置行高

			cell = row.createCell(cellNo);// 创建单元格

			// 合并单元格
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

			// 设置单元格内容
			cell.setCellValue(inputDate.replace("-0", "-").replace("-", "年") + "月份出货表");
			cell.setCellStyle(bigTitle(wb));// 设置大标题样式

			// 小标题
			String[] titles = { "客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款" };

			// 创建小标题的行对象
			row = sheet.createRow(rowNo++);
			row.setHeightInPoints(26.5f);// 设置行高

			for (String title : titles) {
				cell = row.createCell(cellNo++);
				cell.setCellValue(title);
				cell.setCellStyle(title(wb));
			}

			cellNo = 0;

			// 数据输出
			// 查询出指定月份的船期的数据
			List<ContractProduct> list = contractProductService.find(
					"from ContractProduct cp where DATE_FORMAT(cp.contract.shipTime, '%Y-%m') = ?",
					ContractProduct.class, new Object[] { inputDate });
			for (ContractProduct cp : list) {
				row = sheet.createRow(rowNo++);
				row.setHeightInPoints(24);

				// 客户
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getContract().getCustomName());
				cell.setCellStyle(text(wb));// 设置单元格样式

				// 订单号
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getContract().getContractNo());
				cell.setCellStyle(text(wb));// 设置单元格样式

				// 货号
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getProductNo());
				cell.setCellStyle(text(wb));// 设置单元格样式

				// 数量
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getCnumber());
				cell.setCellStyle(text(wb));// 设置单元格样式

				// 工厂
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getFactoryName());
				cell.setCellStyle(text(wb));// 设置单元格样式

				// 工厂交期
				cell = row.createCell(cellNo++);
				cell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
				cell.setCellStyle(text(wb));// 设置单元格样式

				// 船期
				cell = row.createCell(cellNo++);
				cell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
				cell.setCellStyle(text(wb));// 设置单元格样式

				// 贸易条款
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getContract().getTradeTerms());
				cell.setCellStyle(text(wb));// 设置单元格样式

				cellNo = 0;
			}

			// 导出下载
			DownloadUtil downloadUtil = new DownloadUtil();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			wb.write(bos);// 将excel表格中的内容先保存到缓存中
			bos.close();

			HttpServletResponse response = ServletActionContext.getResponse();
			downloadUtil.download(bos, response, "出货表.xls");
		} else {
			// 模版打印

			// 1、读取工作簿
			String path = ServletActionContext.getServletContext().getRealPath("/") + "make/xlsprint/tOUTPRODUCT.xls";
			InputStream is = new FileInputStream(path);
			Workbook wb = new HSSFWorkbook(is);

			// 2、读取工作表
			Sheet sheet = wb.getSheetAt(0);

			// 3、读取行对象
			// 大标题
			row = sheet.getRow(rowNo++);
			cell = row.getCell(0);

			// 设置单元格内容
			cell.setCellValue(inputDate.replace("-0", "-").replace("-", "年") + "月份出货表");

			cellNo = 0;
			// 小标题
			rowNo++;

			// 数据输出
			// 读取行对象
			row = sheet.getRow(rowNo);
			CellStyle[] cellStyles = new CellStyle[8];
			for (int i = 0; i < 7; ++i) {
				cellStyles[i] = row.getCell(i).getCellStyle();
			}

			// 查询出指定月份的船期的数据
			List<ContractProduct> list = contractProductService.find(
					"from ContractProduct cp where DATE_FORMAT(cp.contract.shipTime, '%Y-%m') = ?",
					ContractProduct.class, new Object[] { inputDate });
			for (ContractProduct cp : list) {
				row = sheet.createRow(rowNo++);
				row.setHeightInPoints(24);

				// 客户
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getContract().getCustomName());
				cell.setCellStyle(cellStyles[0]);// 设置单元格样式

				// 订单号
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getContract().getContractNo());
				cell.setCellStyle(cellStyles[1]);// 设置单元格样式

				// 货号
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getProductNo());
				cell.setCellStyle(cellStyles[2]);// 设置单元格样式

				// 数量
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getCnumber());
				cell.setCellStyle(cellStyles[3]);// 设置单元格样式

				// 工厂
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getFactoryName());
				cell.setCellStyle(cellStyles[4]);// 设置单元格样式

				// 工厂交期
				cell = row.createCell(cellNo++);
				cell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
				cell.setCellStyle(cellStyles[5]);// 设置单元格样式

				// 船期
				cell = row.createCell(cellNo++);
				cell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
				cell.setCellStyle(cellStyles[6]);// 设置单元格样式

				// 贸易条款
				cell = row.createCell(cellNo++);
				cell.setCellValue(cp.getContract().getTradeTerms());
				cell.setCellStyle(cellStyles[7]);// 设置单元格样式

				cellNo = 0;
			}

			// 导出下载
			DownloadUtil downloadUtil = new DownloadUtil();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			wb.write(bos);// 将excel表格中的内容先保存到缓存中
			bos.close();

			HttpServletResponse response = ServletActionContext.getResponse();
			downloadUtil.download(bos, response, "出货表.xls");
		}
		return NONE;
	}

	// 大标题的样式
	public CellStyle bigTitle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true); // 字体加粗

		style.setFont(font);

		style.setAlignment(HorizontalAlignment.CENTER); // 横向居中
		style.setVerticalAlignment(VerticalAlignment.CENTER); // 纵向居中

		return style;
	}

	// 小标题的样式
	public CellStyle title(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 12);

		style.setFont(font);

		style.setAlignment(HorizontalAlignment.CENTER); // 横向居中
		style.setVerticalAlignment(VerticalAlignment.CENTER); // 纵向居中

		style.setBorderTop(BorderStyle.THIN); // 上细线
		style.setBorderBottom(BorderStyle.THIN); // 下细线
		style.setBorderLeft(BorderStyle.THIN); // 左细线
		style.setBorderRight(BorderStyle.THIN); // 右细线

		return style;
	}

	// 文字样式
	public CellStyle text(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 10);

		style.setFont(font);

		style.setAlignment(HorizontalAlignment.CENTER); // 横向居左
		style.setVerticalAlignment(VerticalAlignment.CENTER); // 纵向居中

		style.setBorderTop(BorderStyle.THIN); // 上细线
		style.setBorderBottom(BorderStyle.THIN); // 下细线
		style.setBorderLeft(BorderStyle.THIN); // 左细线
		style.setBorderRight(BorderStyle.THIN); // 右细线

		return style;
	}
}
