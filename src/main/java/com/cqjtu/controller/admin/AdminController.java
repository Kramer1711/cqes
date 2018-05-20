package com.cqjtu.controller.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.mapper.AccountMapper;
import com.cqjtu.model.Account;
import com.cqjtu.service.AccountService;
import com.cqjtu.util.KnowExcel;
import com.cqjtu.util.TimeUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountMapper AccountMapper;
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		try {
			this.request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/assignRolePage")
	public String intoAssignRolePage() {
		return "/admin/assignRole";
	}
	
	@RequestMapping("/assignUserPage")
	public String intoAssignUserPage() {
		return "/admin/assignUser";
	}
	
	@RequestMapping("/insertFunctionPage")
	public String intoInsertFunctionPage() {
		return "/admin/insertFunction";
	}
	
	@RequestMapping("/insertUserPage")
	public String intoInsertUserPage() {
		return "/admin/insertUser";
	}
	
	@RequestMapping("/insertRolePage")
	public String intoInsertRolePage() {
		return "/admin/insertRole";
	}
	
	@RequestMapping("/assignFunction")
	public String assignFunction() {
		return "/admin/assignFunction";
	}
	
	@ResponseBody
	@RequestMapping("/insertAccount")
	public String insertAccount(@RequestBody Account account){
		JSONObject json=new JSONObject();
		if(accountService.insertSelective(account)>0){
			json.put("result", "success");
		}else{
			json.put("result", "false");
		}
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping("/getAccounts")
	public String getAccounts(@RequestParam("page") int page,@RequestParam("rows") int rows,
		@RequestParam("accountName") String accountName,@RequestParam("realName") String realName,
		@RequestParam("roleId") Integer roleId,@RequestParam("status") String status){
		//数据处理
		if(accountName!=null&&accountName.length()==0){
			accountName=null;
		}
		if(realName!=null&&realName.length()==0){
			realName=null;
		}
		if(roleId!=null&&roleId==-1){
			roleId=null;
		}
		if(status!=null&&status.equals("-1")){
			status=null;
		}
		
		//处理完毕，进行初始化
		JSONObject json=new JSONObject();
		Account search=new Account();
		search.setAccountName(accountName);
		search.setRealName(realName);
		search.setRoleId(roleId);
		search.setAccountStatus(status);
		int startNum=(page-1)*rows;
		int endNum=page*rows+1;
		search.setStartNum(startNum);
		search.setEndNum(endNum);
		//查询结果
		List<Account> list= accountService.getAccounts(search);
		//List<Owner> list = odao.limitOwner_info((Integer.parseInt(page)-1)*3, Integer.parseInt(rows),name,id,phone,state);
		//JSONObject json = new JSONObject();
		json.put("total", accountService.countAccounts(search));
		json.put("rows", list);
		return json.toString();
	}
	@ResponseBody
	@RequestMapping("/insertAccountsOfXML")
	public String insertAccountsOfXML(MultipartFile file,@RequestParam("import_roleId")int roleId){
		System.out.println("roleId="+roleId);
        // uploads文件夹位置
        String rootPath = request.getSession().getServletContext().getRealPath("resource/uploads/");
        // 原始名称
        String originalFileName = file.getOriginalFilename();
        Account user=(Account) session.getAttribute("account");
        String userName="";
        if(user==null){
        	userName="NULL";
        }else if(user.getAccountName()==null){
        	userName="NULL";
        }else{
        	userName=user.getAccountName();
        }
        // 新文件名
        StringBuffer newFileName=new StringBuffer();
        newFileName.append(userName);
        newFileName.append('-');
        newFileName.append(TimeUtil.getNowTime());
        newFileName.append('-');
        newFileName.append(originalFileName);
        Calendar date = Calendar.getInstance();
        StringBuffer fileDirs=new StringBuffer();
        fileDirs.append(rootPath).append(File.separator);
        fileDirs.append(date.get(Calendar.YEAR)).append(File.separator);
        fileDirs.append(date.get(Calendar.MONTH)+1).append(File.separator);
        String filePath=fileDirs.toString()+newFileName.toString();
        File newFile = new File(filePath);
        if( !newFile.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        System.out.println(newFile);
        // 将内存中的数据写入磁盘
        try {
			file.transferTo(newFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        List<Account> list=KnowExcel.getAccountsDataFromExcel(filePath,"111111",roleId,"1");
        JSONObject json=new JSONObject();
        if(list==null){
        	json.put("status", "false");
        }else{
        	int insert=0;
        	int have=0;
        	for(Account account:list){
        		if(accountService.hasAccount(account.getAccountName())==false){
        			insert+=accountService.insertSelective(account);
        		}else{
        			have++;
        		}
        	}
			json.put("status", "true");
			json.put("insert", insert);
			json.put("real", list.size());
			json.put("have", have);
		}
        return json.toJSONString();
	}
	/**
	 * 插入单条数据
	 * @param accountName
	 * @param realName
	 * @param roleId
	 * @param statusName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insertAccountOfParam")
	public String insertAccountOfParam(@RequestParam("add_accountName") String accountName,@RequestParam("add_realName") String realName,
			@RequestParam("add_roleId") Integer roleId,@RequestParam("add_status") String statusName){
		List<Account> list=new ArrayList<Account>();
		Account account = new Account();
		account.setAccountName(accountName);
		account.setRealName(realName);
		account.setPassword("111111");
		account.setStatusName(statusName);
		account.setRoleId(roleId);
		list.add(account);
		JSONObject json=new JSONObject();
		if(accountService.insertAccountsOfList(list)==1){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
        return json.toJSONString();
	}
	/**
	 * 下载用户格式表格
	 * @param accountName
	 * @param realName
	 * @param roleId
	 * @param statusName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/downloadXML")
	public ResponseEntity<byte[]> downloadXML(@RequestParam("fileName") String fileName){
		String filePath=request.getServletContext().getRealPath("")+"\\WEB-INF\\classes\\"+fileName;
        File file=new File(filePath);  
        HttpHeaders headers = new HttpHeaders();    
        //String fileName=new String("你好.xlsx".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
        String downloadFileName;
		try {
			downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
        headers.setContentDispositionFormData("attachment", downloadFileName);   
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        try {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}    
	}//updateAccounts

	
	@ResponseBody
	@RequestMapping("/updateAccounts")
	public String updateAccounts(@RequestBody List<Account> accountList){
		JSONObject json=new JSONObject();
		if(accountList==null){
			json.put("status", "false");
		}else if(accountService.updateAccounts(accountList)==true){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
		return json.toString();
	}
	@ResponseBody
	@RequestMapping("/resetPassword")
	public String resetPassword(@RequestBody List<Account> accountList){
		JSONObject json=new JSONObject();
		if(accountList==null){
			json.put("status", "false");
		}else if(accountService.resetPassword(accountList)==true){
			json.put("status", "true");
		}else{
			json.put("status", "false");
		}
		return json.toString();
	}
}
