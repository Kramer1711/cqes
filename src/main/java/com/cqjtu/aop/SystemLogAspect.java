package com.cqjtu.aop;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.cqjtu.annotation.SystemControllerLog;
import com.cqjtu.annotation.SystemServiceLog;
import com.cqjtu.model.Account;
import com.cqjtu.model.Log;
import com.cqjtu.model.LogWithBLOBs;
import com.cqjtu.service.LogService;

/**
 * 系统日志切面类
 * 
 *
 */
@Aspect
@Component
public class SystemLogAspect {
	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	private static final ThreadLocal<Date> timeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal time");
	
	private static final ThreadLocal<Log> logThreadLocal = new NamedThreadLocal<Log>("ThreadLocal log");

	private static final ThreadLocal<Account> currentOperator = new NamedThreadLocal<>("ThreadLocal Operator");

	@Autowired(required = false)
	private HttpServletRequest request;

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private LogService logService;

	/**
	 * Controller层切点 注解拦截
	 */
	@Pointcut("@annotation(com.cqjtu.annotation.SystemControllerLog)")
	public void controllerAspect() {
		System.err.println("controllerAspect");
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作的开始时间
	 * 
	 * @param joinPoint
	 *            切点
	 * @throws InterruptedException
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) throws InterruptedException {
		System.err.println("doBefore");

		Date time = new Date();
		timeThreadLocal.set(time);// 线程绑定变量（该数据只有当前请求的线程可见）
		if (logger.isDebugEnabled()) {// 这里日志级别为debug
			logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(time),
					request.getRequestURI());
		}
		// 读取session中的用户
		HttpSession session = request.getSession();
		Account operator = (Account) session.getAttribute("account");
		currentOperator.set(operator);

	}

	/**
	 * 后置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@SuppressWarnings("unchecked")
	@After("controllerAspect()")
	public void doAfter(JoinPoint joinPoint) {
		System.err.println("doAfter");

		Account operator = currentOperator.get();
		if (operator != null) {
			String title = "";
			String type = "info"; // 日志类型(info:入库,error:错误)
			String remoteAddr = request.getRemoteAddr();// 请求的IP
			String requestUri = request.getRequestURI();// 请求的Uri
			String method = request.getMethod(); // 请求的方法类型(post/get)
			Map<String, String[]> params = request.getParameterMap(); // 请求提交的参数

			try {
				title = getControllerMethodDescription2(joinPoint);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 打印JVM信息。
			long time = timeThreadLocal.get().getTime();// 得到线程绑定的局部变量（开始时间）
			Date endTime = new Date(); 
			long endTimeLong = endTime.getTime(); // 2、结束时间
			if (logger.isDebugEnabled()) {
				logger.debug("计时结束：{}  URI: {}  耗时： {}   最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime), request.getRequestURI(),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTimeLong - time),
						Runtime.getRuntime().maxMemory() / 1024 / 1024,
						Runtime.getRuntime().totalMemory() / 1024 / 1024,
						Runtime.getRuntime().freeMemory() / 1024 / 1024,
						(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()
								+ Runtime.getRuntime().freeMemory()) / 1024 / 1024);
			}
			LogWithBLOBs log = new LogWithBLOBs();
			log.setLogId(UUID.randomUUID().toString());
			log.setTitle(title);
			log.setType(type);
			log.setRemoteAddr(remoteAddr);
			log.setRequestUri(requestUri);
			log.setMethod(method);
			log.setMapToParams(params);
			log.setOperatorId(operator.getAccountId());
			Date operateDate = timeThreadLocal.get();
			log.setBeginDate(operateDate);
			log.setTimeout(endTime);

			// 通过线程池来执行日志保存
			threadPoolTaskExecutor.execute(new SaveLogThread(log, logService));
			logThreadLocal.set(log);
		}

	}

	/**
	 * 异常通知 记录操作报错日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		LogWithBLOBs log = (LogWithBLOBs) logThreadLocal.get();
		if (log != null) {
			log.setType("error");
			log.setException(e.toString());
			new UpdateLogThread(log, logService).start();
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint切点
	 * @return discription
	 */
	public static String getServiceMthodDescription2(JoinPoint joinPoint) {
		System.err.println("getServiceMthodDescription2");
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SystemServiceLog serviceLog = method.getAnnotation(SystemServiceLog.class);
		return serviceLog.description();
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return discription
	 */
	public static String getControllerMethodDescription2(JoinPoint joinPoint) {

		System.err.println("getControllerMethodDescription2");
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SystemControllerLog controllerLog = method.getAnnotation(SystemControllerLog.class);
		return controllerLog.description();
	}

	/**
	 * 保存日志线程
	 */
	private static class SaveLogThread implements Runnable {
		private LogWithBLOBs log;
		private LogService logService;

		public SaveLogThread(LogWithBLOBs log, LogService logService) {
			this.log = log;
			this.logService = logService;
		}

		@Override
		public void run() {
			logService.createLog(log);
		}
	}

	/**
	 * 日志更新线程
	 */
	private static class UpdateLogThread extends Thread {
		private LogWithBLOBs log;
		private LogService logService;

		public UpdateLogThread(LogWithBLOBs log, LogService logService) {
			super(UpdateLogThread.class.getSimpleName());
			this.log = log;
			this.logService = logService;
		}

		@Override
		public void run() {
			this.logService.updateLog(log);
		}
	}
}