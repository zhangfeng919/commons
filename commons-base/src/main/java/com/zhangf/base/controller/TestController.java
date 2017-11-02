package com.zhangf.base.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zhangf.base.constants.ApplicationConstants;
import com.zhangf.base.data.ResultBean;
import com.zhangf.base.model.DbBean;
import com.zhangf.base.model.DbBean.DbType;
import com.zhangf.base.service.db.DbService;
import com.zhangf.base.util.LogUtil;

@RestController
public class TestController {


	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	@Autowired
	DbService dbService;
	
	@RequestMapping("/test")
	public String test(){
		LogUtil.info("this is a message1!");
		String string = request.getParameter("params");
		
		Gson gson = new Gson();
		
		DbBean jo = gson.fromJson(string, DbBean.class);
		
		System.out.println(jo.toString());
		ResultBean<Object>  testConnResult = dbService.testConnection(jo);
		
		
		return getDbMap();
		
	}
	
	@RequestMapping("/dbMap")
	public String getDbMap(){
		LogUtil.info("this is a message run method dbmap!");
		Gson gson = new Gson();
		HashMap<String, DbBean> dbMap = null;
		//获取到数据库列表信息
		ResultBean<HashMap<String, DbBean>> queryDbResult = dbService.queryDb();
		if(queryDbResult.isSuccess()){
			dbMap = queryDbResult.getObject();
		}
		
		return gson.toJson(dbMap);
	}
	
	@RequestMapping("/testJo")
	public String testJo(){
		LogUtil.info("this is a message!");
		String string = request.getParameter("params");
		
		Gson gson = new Gson();
		
		HashMap jo = gson.fromJson(string, HashMap.class);
		
		System.out.println(jo.toString());
		DbBean db = gson.fromJson(string, DbBean.class);
		testConnectionJo(db);
		
		
		//获取到数据库列表信息
		
		
		return getDbMapJo();
		
	}
	
	@RequestMapping("/dbMapJo")
	public String getDbMapJo(){
		LogUtil.info("this is a message run method dbmap!");
		Gson gson = new Gson();
		//获取到数据库列表信息
		HashMap<String, HashMap> dbMap = new HashMap<String, HashMap>();
		File file = new File(ApplicationConstants.DBLISTFILENAMEJO);
		if(file.exists()){
			
			try {
				dbMap = SerializationUtils.deserialize(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return gson.toJson(dbMap);
	}
	
	/**
	 * 测试下数据库连接
	 * @return
	 */
	private JsonObject testConnection(DbBean db){
		JsonObject jo = new JsonObject();
		
		jo.addProperty("success", false);
		
		String url = initUrl(db);
		
		String user = db.getDbUserName();
		String password = db.getDbPassword();
		
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			if(conn != null){
				//存储链接详情
				saveDbInfo(db);
				
				
				jo.addProperty("success", true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return jo;
	}
	
	/**
	 * 测试下数据库连接
	 * @return
	 */
	private JsonObject testConnectionJo(DbBean db){
		JsonObject jo = new JsonObject();
		
		jo.addProperty("success", false);
		
		String url = initUrl(db);
		
		String user = db.getDbUserName();
		String password = db.getDbPassword();
		
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			if(conn != null){
				//存储链接详情
				saveDbInfoJo(db);
				
				
				jo.addProperty("success", true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return jo;
	}
	
	private void saveDbInfo(DbBean db){
		File dbFile = new File(ApplicationConstants.DBLISTFILENAME);
		if(!dbFile.exists()){
			try {
				dbFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		HashMap<String, DbBean> dbMap = null;
		try {
			dbMap = SerializationUtils.deserialize(new FileInputStream(dbFile));
			dbMap.put(db.getDbName(), db);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			dbMap = new HashMap<String, DbBean>();
			dbMap.put(db.getDbName(), db);
		}
		
		
		try {
			SerializationUtils.serialize(dbMap, new FileOutputStream(dbFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void saveDbInfoJo(DbBean db){
		File dbFile = new File(ApplicationConstants.DBLISTFILENAMEJO);
		if(!dbFile.exists()){
			try {
				dbFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		HashMap<String, HashMap> dbMap = null;
		Gson gson = new Gson();
		try {
			dbMap = SerializationUtils.deserialize(new FileInputStream(dbFile));
			dbMap.put(db.getDbName(), gson.fromJson(gson.toJson(db), HashMap.class));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			dbMap = new HashMap<String, HashMap>();
			dbMap.put(db.getDbName(), gson.fromJson(gson.toJson(db), HashMap.class));
		}
		
		
		try {
			SerializationUtils.serialize(dbMap, new FileOutputStream(dbFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String initUrl(DbBean db){
		StringBuffer url = new StringBuffer();
		String className;
		StringBuffer ipAndPort = new StringBuffer();
		ipAndPort.append(db.getDbIp());
		ipAndPort.append(":");
		ipAndPort.append(db.getDbPort());
		if(StringUtils.equals(db.getDbType(), String.valueOf(DbType.mysql.getValue()))){
			url.append("jdbc:mysql://");
			url.append(ipAndPort);
			url.append("/");
			url.append(db.getDbName());
			url.append("?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
			className = "com.mysql.jdbc.Driver";
			className = "com.mysql.cj.jdbc.Driver";
		}else if (StringUtils.equals(db.getDbType(), String.valueOf(DbType.sqlserver.getValue()))){
			url.append("jdbc:sqlserver://");
			url.append(ipAndPort);
			url.append(";DatabaseName=");
			url.append(db.getDbName());
			className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		}else {
			url.append("jdbc:oracle:thin:@");
			url.append(ipAndPort);
			url.append(":");
			url.append(db.getDbName());
			className = "oracle.jdbc.driver.OracleDriver";
		}
		
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		
		return url.toString();
	}
}
