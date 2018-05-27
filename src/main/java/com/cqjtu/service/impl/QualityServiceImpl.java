package com.cqjtu.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqjtu.mapper.EvaluationMethodMapper;
import com.cqjtu.mapper.ItemTypeMapper;
import com.cqjtu.mapper.QualityItemAuditMapper;
import com.cqjtu.mapper.QualityItemMapper;
import com.cqjtu.mapper.QualityMapper;
import com.cqjtu.mapper.ScoreMapper;
import com.cqjtu.mapper.StudentMapper;
import com.cqjtu.model.EvaluationMethod;
import com.cqjtu.model.Quality;
import com.cqjtu.model.QualityItem;
import com.cqjtu.model.QualityItemAudit;
import com.cqjtu.model.Score;
import com.cqjtu.service.QualityService;
import com.cqjtu.util.ParamUtil;

@Service
public class QualityServiceImpl implements QualityService {
	@Autowired
	QualityMapper qualityMapper;
	@Autowired
	ItemTypeMapper itemTypeMapper;
	@Autowired
	QualityItemMapper qualityItemMapper;
	@Autowired
	QualityItemAuditMapper qualityItemAuditMapper;
	@Autowired
	ScoreMapper scoreMapper;
	@Autowired
	EvaluationMethodMapper evaluationMethodMapper;

	private static class EXCEL_TYPE {
		public static String QUALITY_SCORE = "qualityScore";
	};

	@Override
	public int insertQuality(Quality quality) {
		return qualityMapper.insert(quality);
	}

	@Override
	public List<Map<String, Object>> getTypeList() {
		return itemTypeMapper.getList();
	}

	@Override
	public void uploadQualityItem(Map<String, Object> param) {
		Quality quality = qualityMapper.selectByStudentId(param);
		QualityItem qualityItem = new QualityItem();
		qualityItem.setQualityId(quality.getQualityId());
		qualityItem.setItemName((String) param.get("itemName"));
		qualityItem.setQualityTypeId(Integer.parseInt((String) param.get("itemType")));
		qualityItem.setItemScore(Integer.parseInt((String) param.get("itemScore")));
		qualityItem.setItemEvidenceUrl((String) param.get("filepath"));
		qualityItemMapper.insert(qualityItem);
		QualityItemAudit audit = new QualityItemAudit();
		audit.setQualityItemId(qualityItem.getQualityTiemId());
		audit.setItemStatus("待审核");
		qualityItemAuditMapper.insert(audit);
	}

	@Override
	public void updateQualityItemById(Map<String, Object> param) {
		QualityItem qualityItem = qualityItemMapper
				.selectByPrimaryKey(Integer.parseInt((String) param.get("qualityItemId")));
		String filepath = qualityItem.getItemEvidenceUrl();
		qualityItem.setItemName((String) param.get("itemName"));
		qualityItem.setItemScore(Integer.parseInt((String) param.get("itemScore")));
		qualityItem.setQualityTypeId(Integer.parseInt((String) param.get("typeId")));
		String newFilepath = (String) param.get("filepath");
		if (newFilepath != null) {
			qualityItem.setItemEvidenceUrl(newFilepath);
		}
		qualityItemMapper.updateByPrimaryKey(qualityItem);
	}

	@Transactional
	@Override
	public boolean deleteQualityItem(Integer qualityItemId) {
		int result1 = qualityItemAuditMapper.deleteByQualityItemId(qualityItemId);
		int result2 = qualityItemMapper.deleteByPrimaryKey(qualityItemId);
		System.err.println(result1 + "\t" + result2);
		if (result1 == 1 && result2 == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> searchQualityScore(Map<String, Object> param) {
		return qualityMapper.searchQualityScore(param);
	}

	@Override
	public int getTotalQualityScore(Map<String, Object> param) {
		return qualityMapper.getTotalQualityScore(param);
	}

	@Override
	public byte[] exportExcel(Map<String, Object> param) throws FileNotFoundException, IOException {
		String type = (String) param.get("type");
		if (type.equals(EXCEL_TYPE.QUALITY_SCORE)) {
			return exportQualityScoreExcel(param);
		} else {
			return exportComprehensiveQualityScoreExcel(param);
		}
	}

	/**
	 * 输出素质操行分表 Excel二进制数据
	 * 
	 * @param param
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private final static class COLNAME {
		public static final String[] QUALITY_SCORE = { "序号", "学号", "姓名", "年级", "专业", "学院", "加分", "减分", "科技创新分", "文体分",
				"素质操行分总分" };
		public static final String[] COMPREHENSIVE_QUALITY_SCORE = { "序号", "学号", "姓名", "年级", "专业", "学院", "学年平均学分绩点",
				"学年成绩平均分", "素质操行总分", "综合素质测评总分", "综合素质排名" };

	}

	private byte[] exportQualityScoreExcel(Map<String, Object> param) throws FileNotFoundException, IOException {
		File file = new File((String) param.get("templateFilePath") + "szcxfdjb.xls");
		List<Map<String, Object>> result = qualityMapper.searchAllQualityScore(param);
		System.out.println("--------数据：\n" + result.toString());
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));// 获取工作薄
		HSSFSheet sheet = workbook.getSheetAt(0); // 获取工作表
		String sheetHeader = (String) param.get("fileName"); // 工作表头
		HSSFRow sheetNameRow = sheet.getRow(0); // 列名
		sheetNameRow.getCell(0).setCellValue(sheetHeader);
		// 列名
		for (int row = 4; row < result.size() + 4; row++) {// 前四列为表头及列名
			HSSFRow rows = sheet.createRow(row);
			for (int col = 0; col < COLNAME.QUALITY_SCORE.length; col++) {
				String key = COLNAME.QUALITY_SCORE[col];
				String strValue = null;
				switch (col) {
				case 0:// 序号
					rows.createCell(col).setCellValue(row - 3);
					break;
				case 1:// 学号
					Long longValue = (Long) result.get(row - 4).get(key);
					rows.createCell(col).setCellValue(longValue.toString());
					break;
				case 2:
				case 4:
				case 5:// 姓名,学院,专业
					strValue = (String) result.get(row - 4).get(key);
					rows.createCell(col).setCellValue(strValue);
					break;
				case 3:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:// 年级,加分,减分,科技创新分,文体分,素质操行分总分
					Integer intValue = (Integer) result.get(row - 4).get(key);
					rows.createCell(col).setCellValue(intValue == null ? 0 : intValue);
					break;
				default:
					break;
				}
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	private byte[] exportComprehensiveQualityScoreExcel(Map<String, Object> param)
			throws FileNotFoundException, IOException {
		File file = new File((String) param.get("templateFilePath") + "zhszcppmb.xls");
		List<Map<String, Object>> result = qualityMapper.searchAllComprehensiveQualityScore(param);
		System.out.println("--------数据：\n" + result.toString());
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));// 获取工作薄
		HSSFSheet sheet = workbook.getSheetAt(0); // 获取工作表
		String sheetHeader = (String) param.get("fileName"); // 工作表头
		HSSFRow sheetNameRow = sheet.getRow(0); // 列名
		sheetNameRow.getCell(0).setCellValue(sheetHeader);
		// 列名
		for (int row = 3; row < result.size() + 3; row++) {// 前三列为表头及列名
			HSSFRow rows = sheet.createRow(row);
			for (int col = 0; col < COLNAME.COMPREHENSIVE_QUALITY_SCORE.length; col++) {
				String key = COLNAME.COMPREHENSIVE_QUALITY_SCORE[col];
				String strValue = null;
				switch (col) {
				case 0:
				case 10:// 序号,排名
					rows.createCell(col).setCellValue(row - 2);
					break;
				case 1:// 学号
					Long longValue = (Long) result.get(row - 3).get(key);
					rows.createCell(col).setCellValue(longValue.toString());
					break;
				case 2:
				case 4:
				case 5:// 姓名,学院,专业
					strValue = (String) result.get(row - 3).get(key);
					rows.createCell(col).setCellValue(strValue);
					break;
				case 3:
				case 8:// 年级,素质操行总分
					Integer intValue = (Integer) result.get(row - 3).get(key);
					rows.createCell(col).setCellValue(intValue == null ? 0 : intValue);
					break;
				case 6:
				case 7:
				case 9:// 学年平均学分绩点,学年成绩平均分, 综合素质测评总分
					Double doubleValue = (Double) result.get(row - 3).get(key);
					rows.createCell(col).setCellValue(doubleValue == null ? 0 : doubleValue);
					break;
				default:
					break;
				}
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	@Override
	public void updateQualityScore(Integer qualityId) {
		Quality quality = qualityMapper.selectByPrimaryKey(qualityId);// 获取quality实体对象
		Integer qualityScore = qualityItemMapper.calQualityScore(qualityId);// 操行总分
		quality.setQualitySumScore(qualityScore);// 设置操行总分
		String academicYear = quality.getAcademicYear();// 学年
		// 获取每种类型的操行总分
		List<Map<String, Object>> sumTypeQualityScore = qualityItemMapper.calTypeQualityScore(qualityId);
		// 获取该学年的测评方法
		List<EvaluationMethod> evaluationMethodList = evaluationMethodMapper.selectByAcademicYear(academicYear);
		// 测评方法Map
		Map<Integer, Double> emMap = new HashMap<>();
		for (EvaluationMethod em : evaluationMethodList) {
			emMap.put(em.getTypeId(), em.getPercentage());
		}
		// 记录综合素质测评总分
		Double comprehensiveQualityScore = 0.0;
		// 计算综合素质测评总分
		for (int i = 0; i < sumTypeQualityScore.size(); i++) {
			Integer typeId = (Integer) sumTypeQualityScore.get(i).get("qualityTypeId");
			Integer typeScore = (Integer) sumTypeQualityScore.get(i).get("qualityScore");
			Double percentage = emMap.get(typeId);
			percentage = typeId == 1 ? percentage * 0.1 : percentage;
			comprehensiveQualityScore += typeScore * percentage;
		}
		quality.setComprehensiveQualityScore(comprehensiveQualityScore);
		// 更新数据库信息
		qualityMapper.updateByPrimaryKeySelective(quality);
	}

	@Override
	public List<Map<String, Object>> searchComprehensiveQualityScore(Map<String, Object> param) {
		return qualityMapper.searchComprehensiveQualityScore(param);
	}
}
