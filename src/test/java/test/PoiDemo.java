package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.model.WorkbookRecordList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.WorkbookDependentFormula;
import org.apache.poi.ss.formula.WorkbookEvaluator;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.mapper.AcademicYearMapper;
import com.cqjtu.mapper.QualityMapper;
import com.cqjtu.util.ParamUtil;

/**
 * Poi写Excel
 * 
 * @author jianggujin
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PoiDemo {
	private static final String xlspath = "E:\\poi.xls";

	@Autowired
	QualityMapper qualityMapper;
	@Autowired
	AcademicYearMapper academicYearMapper;

	@Test
	public void writeDemo() throws IOException {
		// 参数
		Map<String, Object> param = ParamUtil.getParamMap();
		param.put("key", "");
		param.put("collegeId", null);
		param.put("majorId", null);
		param.put("status", null);
		param.put("page", 1);
		param.put("rows", 100);
		param.put("sort", "studentId");
		param.put("order", "asc");
		param.put("sortOrder", "studentId asc");
		param.put("status", "全部");
		// 获取数据
		List<Map<String, Object>> result = qualityMapper.searchQualityScore(param);
		System.out.println("--------数据：\n" + result.toString());
		// 获取工作薄
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:\\szcxfdjb.xls")));
		// 获取工作表
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 工作表头
		String sheetHeader = academicYearMapper.selectStatusIsDoing() + "学年" + param.get("collegeId") + "学院xx级"
				+ param.get("majorId") + "专业学生素质操行分登记汇总表";
		// 列名
		HSSFRow sheetNameRow = sheet.getRow(0);
		sheetNameRow.getCell(0).setCellValue(sheetHeader);
		String[] keys = {"序号","学号","姓名","年级","专业","学院","加分","减分","科技创新分","文体分"};
		for (int row = 4; row < result.size() + 4; row++) {
			HSSFRow rows = sheet.createRow(row);
			for (int col = 0; col < keys.length; col++) {
				String key = null;
				String strValue = null;
				HSSFCell cell = null;
				switch (col) {
				case 0:
					rows.createCell(col).setCellValue(row - 3);
					break;
				case 1:
					key = keys[col];
					Long longValue = (Long) result.get(row - 4).get(key);
					cell = rows.createCell(col);
					cell.setCellValue(longValue.toString());
					break;
				case 2:
				case 4:
				case 5:
					key = keys[col];
					strValue = (String) result.get(row - 4).get(key);
					cell = rows.createCell(col);
					cell.setCellValue(strValue);
					break;
				case 3:	case 6:	case 7:	case 8:	case 9:
					key = keys[col];
					Integer intValue = (Integer) result.get(row - 4).get(key);
					cell = rows.createCell(col);
					if (intValue == null)
						intValue = 0;
					cell.setCellValue(intValue);
				default:
					break;
				}
			}
		}
		File xlsFile = new File("E:\\" + sheetHeader + ".xls");
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