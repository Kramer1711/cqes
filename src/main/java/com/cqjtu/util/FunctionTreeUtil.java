package com.cqjtu.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

/**
 * 
 * @author     杨煌
 * @lastUpdate 2018-05-07
 *
 */
public class FunctionTreeUtil{
	
	/**
	 * 用于存放权限树
	 */
	private static HashMap<Integer, String> treeMap=new HashMap<Integer,String>();
	
	/**
	 * 用户功能树初始化
	 */
	public static void init(){
		Connection con=SingleConnectUtil.getConnection();
		StringBuffer sql=new StringBuffer();
		//获得所有的角色id和其所拥有的权限
		sql.append("select role_id rid,role_name rname,function_ids fids ");
		sql.append("from role ");
		PreparedStatement pst=null;
		ResultSet rs=null;
		try{
			pst=con.prepareStatement(sql.toString());
			rs=pst.executeQuery();
			while(rs.next()){
				treeMap.put(rs.getInt("rid"),getRoleFunctions(-1, rs.getString("fids")).toJSONString());
				System.out.println(completedString(rs.getInt("rid"), rs.getString("rname")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//获得完整的权限树(以“-1”作为其key值)
		treeMap.put(-1, getAllFunctionsTree().toJSONString());
		return;
	}
	public static boolean updateAllFunctionsTree(){
		treeMap.put(-1, getAllFunctionsTree().toJSONString());
		return true;
	}
	/**
	 * 更新指定用户权限树
	 */
	public static boolean updateRoleTree(int rid){
		Connection con=SingleConnectUtil.getConnection();
		StringBuffer sql=new StringBuffer();
		//获得所有的角色id和其所拥有的权限
		sql.append("select role_id rid,role_name rname,function_ids fids ");
		sql.append("from role ");
		sql.append("where role_id=? ");
		PreparedStatement pst=null;
		ResultSet rs=null;
		try{
			pst=con.prepareStatement(sql.toString());
			pst.setInt(1, rid);
			rs=pst.executeQuery();
			if(rs.next()){
				treeMap.put(rid,getRoleFunctions(-1, rs.getString("fids")).toJSONString());
				System.out.println("Update "+completedString(rs.getInt("rid"), rs.getString("rname")));
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static String getRoleTree(int rid){
		return treeMap.get(rid);
	}
	
	/**
	 * 获得完整的权限树
	 * @return  完整的权限树
	 */
	public static JSONArray getAllFunctionsTree(){
		Connection con=SingleConnectUtil.getConnection();
		StringBuffer sql=new StringBuffer();
		sql.append("select distinct fids = ");
		sql.append("stuff((select ',' + CONVERT(varchar(10),function_id) from [function] for xml path('')),1,1,'')");
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst=con.prepareStatement(sql.toString());
			rs=pst.executeQuery();
			if(rs.next()){
				String fids=rs.getString("fids");
				if(fids==null||"NULL".equals(fids)){
					return new JSONArray();
				}else{
					return getRoleFunctions(-1, rs.getString("fids"));
				}
			}else{
				return new JSONArray();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new JSONArray();
		}
	}
	/**
	 * 递归获得相应角色的权限树
	 * @param parentId 父节点
	 * @param fids     角色权限
	 * @return         权限树
	 */
	public static JSONArray getRoleFunctions(int parentId, String fids) {
		JSONArray userFuncs = getFuncsByParentId(parentId, fids);
		//遍历parentId的子节点
		for (int i = 0; i < userFuncs.size(); i++) {
			String state = userFuncs.getJSONObject(i).getString("state");
			//如果是末尾节点，跳过；如果非末尾节点，继续获得其子节点
			if ("open".equals(state)) {
				continue;
			} else {
				int thisId = userFuncs.getJSONObject(i).getInteger("id");
				JSONArray chidFuncs = getRoleFunctions(thisId, fids);
				userFuncs.getJSONObject(i).put("children", chidFuncs);
			}
		}
		return userFuncs;
	}
	
	/**
	 * 获得相应父节点的在fids中的所有子节点
	 * @param parentId 父节点id
	 * @param fids     拥有权限的功能id组
	 * @return         子节点组
	 */
	public static JSONArray getFuncsByParentId(int parentId, String fids) {
		JSONArray array = new JSONArray();
		Connection con = SingleConnectUtil.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select function_id f_id,function_name f_name,");
		sql.append("function_pid f_pid,function_state f_state,function_path f_path ");
		sql.append("from [function] ");
		sql.append("where function_id in( ");
		sql.append(fids);
		sql.append(") ");
		sql.append("and function_pid=? ");
		try {
			PreparedStatement pst = con.prepareStatement(sql.toString());
			pst.setInt(1, parentId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("id", rs.getInt("f_id"));
				obj.put("text", rs.getString("f_name"));
				obj.put("state", rs.getString("f_state"));
				obj.put("fpath", rs.getString("f_path"));
				JSONObject attr = new JSONObject();
				attr.put("fpath", rs.getString("f_path"));
				obj.put("attributes", attr);
				array.add(obj);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	/**
	 * 加载完相应角色树后的输出语句
	 * @param rid   角色id
	 * @param rname 角色名
	 * @return      待输出语句
	 */
	public static String completedString(int rid,String rname){
		StringBuffer temp=new StringBuffer();
		temp.append("RoleTree Completed:{rid=");
		temp.append(rid);
		temp.append(";rname=");
		temp.append(rname);
		temp.append("}");
		return temp.toString();
	}
	public static void main(String args[]){
		FunctionTreeUtil.getAllFunctionsTree();
	}
}
