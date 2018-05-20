package com.cqjtu.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cqjtu.model.Account;


public class KnowExcel {
	public static List<Account> getAccountsDataFromExcel(String filePath,String password,int roleId,String status) {
		if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
			System.out.println("文件不是excel类型");
			return null;
		}
		FileInputStream fis = null; 
		Workbook wookbook = null;
		try {
			fis = new FileInputStream(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try{
			if(filePath.endsWith(".xls")){
				wookbook = new HSSFWorkbook(fis);
			}else if(filePath.endsWith(".xlsx")){
				wookbook = new XSSFWorkbook(filePath);
			}else{
				fis.close();
				return null;
			}
			//获得工作表
			Sheet sheet = wookbook.getSheetAt(0);
			// 获得数据的总行数
			int totalRowNum = sheet.getLastRowNum();
			List<Account> list = new ArrayList<Account>();
			// 要获得属性
			String accountName = null;
			String realName = null;
			// 获得所有数据
			for (int i = 1;i <= totalRowNum;i++) {
				Account tempAccount = new Account();
				// 获得第i行对象
				Row tempRow = sheet.getRow(i);
				// 获得相应数据（列从0开始）
				//获得帐号
				Cell cell = tempRow.getCell(1);
				if("STRING".equals(cell.getCellTypeEnum().toString())){
					accountName=cell.getStringCellValue();
				}else{
					Integer integer=new Integer((int)cell.getNumericCellValue());
					accountName=String.valueOf(integer.toString());
				}
				
				//获得姓名
				Cell cell2 = tempRow.getCell(2);
				realName=cell2.getStringCellValue();
				//封装为用户对象
				tempAccount.setAccountName(accountName);
				tempAccount.setRealName(realName);
				tempAccount.setPassword(password);
				tempAccount.setRoleId(roleId);
				tempAccount.setAccountStatus(status);
				//添加至列表中
				list.add(tempAccount);
			}
			wookbook.close();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}
}
