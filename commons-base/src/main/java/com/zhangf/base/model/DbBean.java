package com.zhangf.base.model;

import java.io.Serializable;

public class DbBean implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	//db类型
	public enum DbType{
		
		mysql(1),sqlserver(2),oracle(3);
		
		private int value;
		
		DbType(int val){
			this.value = val;
		}
		
		public int getValue(){
			return this.value;
		}
	}
	
	
	/** 数据库自定义名称*/
	private String dbShortName;
	/** 数据库名称*/
	private String dbName;
	/** 数据库类型 mysql sqlservser oracle*/
	private String dbType;
	/** 数据库自定义ip*/
	private String dbIp;
	/** 数据库自定义端口*/
	private String dbPort;
	/** 数据库自定义用户名称*/
	private String dbUserName;
	/** 数据库自定义密码*/
	private String dbPassword;
	
	
	
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(DbType dbType) {
		this.dbType = String.valueOf(dbType.getValue());
	}
	public String getDbIp() {
		return dbIp;
	}
	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getDbUserName() {
		return dbUserName;
	}
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getDbShortName() {
		return dbShortName;
	}
	public void setDbShortName(String dbShortName) {
		this.dbShortName = dbShortName;
	}
	
	
	

}
