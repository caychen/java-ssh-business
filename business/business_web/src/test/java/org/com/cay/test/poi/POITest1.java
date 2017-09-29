package org.com.cay.test.poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class POITest1 {

	@Test
	public void testPOI() throws IOException{
		//1、创建工作簿
		Workbook wb = new HSSFWorkbook();
		
		//2、创建工作表
		Sheet sheet = wb.createSheet();
		
		//3、创建行对象
		Row row = sheet.createRow(3);//第四行,下标从0开始
		
		//4、创建单元格
		Cell cell = row.createCell(3);
		
		//5、设置单元格数据
		cell.setCellValue("Cay");
		
		//6、设置单元格样式
		CellStyle cellStyle = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("楷体");//字体名称
		font.setFontHeightInPoints((short)48);//字体大小
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		
		//7、关闭工作簿
		OutputStream os = new FileOutputStream("abc.xls");
		wb.write(os);
		
		os.close();
		wb.close();
	}
}
