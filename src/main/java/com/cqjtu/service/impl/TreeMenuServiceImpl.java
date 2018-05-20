package com.cqjtu.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import com.cqjtu.util.FunctionCheckedTreeUtil;
import com.cqjtu.util.FunctionTreeUtil;
import com.cqjtu.util.SingleConnectUtil;

public class TreeMenuServiceImpl extends HttpServlet{
	public void init(){
		System.out.println("====start init tree====");
		FunctionTreeUtil.init();
		FunctionCheckedTreeUtil.init();
		System.out.println("====init tree completed====");
	}
}
