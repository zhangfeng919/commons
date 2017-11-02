package com.zhangf.base.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 自定义log打印 便于控制
 * 
 * @author Administrator
 *
 */
public class LogUtil {

	static Logger log = LogManager.getLogger(LogUtil.class);
	
	static final boolean PRINT = true;//控制打印
	static final boolean PRINTSTACK = true;//控制是否打印方法调用栈信息
	static List<String> classNameFilterList = new ArrayList<String>();//打印包列表
	static final String SPACE = " ";//打印栈信息时，信息分隔符空格
	static final String ENTER = "/n";
	static{
		classNameFilterList.add("com.zhangf.base");
		classNameFilterList = Collections.unmodifiableList(classNameFilterList);
	}

	public static void info(String msg) {
		if(PRINT){
			log.info(msg);
			printStack();
		}
	}

	public static void debug(String msg) {
		if(PRINT){
			log.debug(msg);
		}
	}

	public static void error(String msg) {
		if(PRINT){
			log.error(msg);
			printStack();
		}
	}

	/**
	 * 打印栈信息
	 */
	private static void printStack() {
		if(!PRINTSTACK){
			return;
		}
		Throwable ex = new Throwable();

		StackTraceElement[] stackElements = ex.getStackTrace();

		if (stackElements != null) {
			String className = null;
			String fileName = null;
			int lineNumber = 0;
			String methodName = null;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < stackElements.length; i++) {
				className = stackElements[i].getClassName();
				if(checkPrint(className)){
					fileName = stackElements[i].getFileName();
					lineNumber = stackElements[i].getLineNumber();
					methodName = stackElements[i].getMethodName();
					sb.append(className);
					sb.append(SPACE);
					sb.append(fileName);
					sb.append(SPACE);
					sb.append(lineNumber);
					sb.append(SPACE);
					sb.append(methodName);
					log.info(sb.toString());
					sb.delete(0, sb.length());
				}
			}
		}
	}
	
	/**
	 * 检测是否可以被打印 在不在classNameFilterList列表
	 * @param className
	 * @return
	 */
	private static boolean checkPrint(String className){
		
		for (String filterName : classNameFilterList) {
			if(StringUtils.startsWith(className, filterName)){
				return true;
			}
		}
		
		return false;
	}

}
