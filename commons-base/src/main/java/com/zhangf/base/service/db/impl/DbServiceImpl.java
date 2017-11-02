package com.zhangf.base.service.db.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.zhangf.base.constants.ApplicationConstants;
import com.zhangf.base.data.ResultBean;
import com.zhangf.base.model.DbBean;
import com.zhangf.base.model.DbBean.DbType;
import com.zhangf.base.service.db.DbService;

@Service
public class DbServiceImpl implements DbService{

	@Override
	public ResultBean<Object> testConnection(DbBean db) {
		ResultBean<Object> resultBean = new ResultBean<Object>();
		
		
		String url = initUrl(db);
		
		String user = db.getDbUserName();
		String password = db.getDbPassword();
		
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			if(conn != null){
				//存储链接详情
				saveDbInfo(db);
				
				resultBean.setSuccess(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return resultBean;
	}

	@Override
	public ResultBean<Object> saveDbInfo(DbBean db) {
		ResultBean<Object> resultBean = new ResultBean<Object>();
		
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
			
			resultBean.setSuccess(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return resultBean;
	}

	@Override
	public ResultBean<HashMap<String, DbBean>> queryDb() {
		
		ResultBean<HashMap<String, DbBean>> resultBean = new ResultBean<HashMap<String,DbBean>>();
		//获取到数据库列表信息
		HashMap<String, DbBean> dbMap = new HashMap<String, DbBean>();
		File file = new File(ApplicationConstants.DBLISTFILENAME);
		if(file.exists()){
			try {
				dbMap = SerializationUtils.deserialize(new FileInputStream(file));
				
				resultBean.setSuccess(true);
				resultBean.setObject(dbMap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return resultBean;
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
