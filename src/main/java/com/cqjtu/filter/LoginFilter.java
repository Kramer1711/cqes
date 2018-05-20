package com.cqjtu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cqjtu.util.AccessControlUtil;

public class LoginFilter implements Filter {

    
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		HttpSession httpSession=httpReq.getSession();
		String uri = ((HttpServletRequest) req).getServletPath();
		//获得xml文件
		String filePath=req.getServletContext().getRealPath("")+"\\WEB-INF\\classes\\ignoredUri.xml";
		if(AccessControlUtil.isIgnoreUri(filePath,uri)==true){
			chain.doFilter(req, resp);
		}else{
			if(httpSession.getAttribute("account")==null){
				httpResp.sendRedirect("/cqes/loginPage");
			}else{
				chain.doFilter(req, resp);
			}
		}
	}
	/**
	 * 暂无用方法
	 */
	public void init(FilterConfig arg0) throws ServletException {
	}
	/**
	 * 暂无用方法
	 */
	public void destroy() {
	}
}