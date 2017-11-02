package com.zhangf.base.service.db;

import java.util.HashMap;

import com.zhangf.base.data.ResultBean;
import com.zhangf.base.model.DbBean;

public interface DbService {

	/**
	 * 检测数据信息是否是可用的
	 * @param db
	 * @return
	 */
	public ResultBean<Object> testConnection(DbBean db);
	
	/**
	 * 保存db信息
	 * @param db
	 * @return
	 */
	public ResultBean<Object> saveDbInfo(DbBean db);
	
	/**
	 * 获取db信息
	 * @return
	 */
	public ResultBean<HashMap<String,DbBean>> queryDb();
}
