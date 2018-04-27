package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.model.WorkbookRecordList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.WorkbookDependentFormula;
import org.apache.poi.ss.formula.WorkbookEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Poi写Excel
 * 
 * @author jianggujin
 * 
 */
public class PoiDemo {
	private static final String xlspath = "E:\\poi.xls";

	@Test
	public void writeDemo() throws IOException {
		// 创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建工作表
		HSSFSheet sheet = workbook.createSheet("sheet1");

		for (int row = 0; row < 10; row++) {
			HSSFRow rows = sheet.createRow(row);
			for (int col = 0; col < 10; col++) {
				if (row == 0)
					rows.createCell(col).setCellValue("列" + col);
				else
					// 向工作表中添加数据
					rows.createCell(col).setCellValue(col + "-" + row);
			}
		}
		File xlsFile = new File(xlspath);
		FileOutputStream xlsStream = new FileOutputStream(xlsFile);
		workbook.write(xlsStream);
	}

	@Test
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

	@Test
	public void getDataToJSON() throws FileNotFoundException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(xlspath)));
		HSSFSheet sheet = null;
		JSONArray xlsJSONArray = new JSONArray();// 存表
		JSONObject rowName = new JSONObject();// 存储列名

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
			sheet = workbook.getSheetAt(i);
			for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
				HSSFRow row = sheet.getRow(j);
				JSONObject cellJSON = new JSONObject();
				if (row != null) {
					for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
						if (j == 0) {// 列名
							rowName.put("列" + k, row.getCell(k));
						} else if (row.getCell(k) != null) { // getCell 获取单元格数据
							String cellContent = row.getCell(k).toString();
							cellJSON.put(rowName.get("列" + k).toString(), cellContent);
							System.out.print(cellContent + "\t");
						} else {
							System.out.print("\t");
						}
					}
				}
				if (j != 0)
					xlsJSONArray.add(cellJSON);
				System.out.println(""); // 读完一行后换行
			}
			System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
		}
		System.out.println("-------------------------");
		System.out.println(xlsJSONArray.toJSONString());
	}
}