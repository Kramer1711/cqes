package com.cqjtu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleConnectUtil {
	private final static String DBURL="jdbc:sqlserver://localhost:1433;DatabaseName=cqes";
	private final static String USERNAME="sa";
	private final static String PASSWORD="123";
	private final static String DBDRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static Connection connection=null;
	private SingleConnectUtil(){};
	public static Connection getConnection(){
		if(connection!=null){
			return connection;
		}else {
			try{
				Class.forName(DBDRIVER);
				Connection con=DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
				return con;
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}

	}
	public static void close(ResultSet rs,PreparedStatement pst){
		try {
			if(rs!=null&&rs.isClosed()!=false){
				rs.close();
			}
			if(pst!=null&&pst.isClosed()!=false){
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
