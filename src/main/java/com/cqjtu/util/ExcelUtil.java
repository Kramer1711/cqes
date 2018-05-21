package com.cqjtu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

public class ExcelUtil {
	/**
	 * 导出excel表
	 * 
	 * @param savepath
	 *            保存路径
	 * @param data
	 *            数据，json格式
	 * @throws IOException
	 */
	public static void writeExcelFile(String savepath, JSONObject data) throws IOException {
		// 创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建工作表
		HSSFSheet sheet = workbook.createSheet("sheet1");
		for (int row = 0; row < 10; row++) {
			HSSFRow rows = sheet.createRow(row);
			for (int col = 0; col < 10; col++) {
				// 向工作表中添加数据
				rows.createCell(col).setCellValue("data" + row + col);
			}
		}
		File xlsFile = new File(savepath);
		FileOutputStream xlsStream = new FileOutputStream(xlsFile);
		workbook.write(xlsStream);
	}

	public void readDemo() throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:\\电子专业成绩.xls")));
		HSSFSheet sheet = null;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
			sheet = workbook.getSheetAt(i);
			for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
				HSSFRow row = sheet.getRow(j);
				if (row != null) {
					for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
						if (row.getCell(k) != null) { // getCell 获取单元格数据
							System.out.print(row.getCell(k) + "\t");
						} else {
							System.out.print("\t");
						}
					}
				}
				System.out.println(""); // 读完一行后换行
			}
			System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
		}
	}
}
