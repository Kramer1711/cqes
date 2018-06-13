package com.cqjtu.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.fastjson.JSONObject;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 *                 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket/{accountName}/{roleId}")
public class WebSocket {
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount =0 ;
	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<String, WebSocket>();
	private static ConcurrentHashMap<String, String> accountRoleSet = new ConcurrentHashMap<String, String>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private String accountName;
	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam(value = "accountName") String accountName,
			@PathParam(value = "roleId") String roleId,Session session) {
		this.accountName=accountName;
		this.session = session;
		webSocketSet.put(accountName, this); // 加入set中
		accountRoleSet.put(accountName, roleId);
		addOnlineCount();// 在线数加
		System.out.println("有新连接加入！帐号为:"+accountName+"角色id为"+roleId+"当前在线人数为" + getOnlineCount());
		
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this.accountName); // 从set中删除
		subOnlineCount(); // 在线数减
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		JSONParser jsonParser=new JSONParser(message);
		LinkedHashMap<String, String> jsonMap=(LinkedHashMap<String, String>)jsonParser.parse();
		String roleId=jsonMap.get("targetRoleId");
		if("-1".equals(roleId)){
	        Iterator<Entry<String, WebSocket>> entries = webSocketSet.entrySet().iterator();    
	        while (entries.hasNext()) {    
	            Entry<String, WebSocket> entry = entries.next();
	            try{
                	entry.getValue().sendMessage(message);
	            }catch(IOException e){
	            	System.out.println("帐号为"+entry.getKey()+"的用户已关闭浏览器，无法传递信息");
	            	continue;
	            }
	        }			
		}else{
	        Iterator<Entry<String, WebSocket>> entries = webSocketSet.entrySet().iterator();    
	        while (entries.hasNext()) {    
	            Entry<String, WebSocket> entry = entries.next();
	            try{
	            	if(roleId.equals(accountRoleSet.get(entry.getKey()))){
	            		entry.getValue().sendMessage(message);
	            	}
	            }catch(IOException e){
	            	System.out.println("帐号为"+entry.getKey()+"的用户已关闭浏览器，无法传递信息");
	            	continue;
	            }
	        }					
		}

	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
//		System.out.println("发生错误");
//		error.printStackTrace();
	}

	/**
	 * 向所属客户端传送数据
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocket.onlineCount--;
	}
}
