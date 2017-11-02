package com.zhangf.base.data;

/**
 * 业务执行结果
 * 
 * 
 * 
 * @author Administrator
 *
 * @param <T> 返回类型 如果想返回指定类型值，传入指定类型即可
 */
public class ResultBean<T> {

	/**
	 * 执行结果
	 */
	private boolean success;
	/**
	 * 结果描述
	 */
	private String msg;
	
	private T object;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "ResultBean [success=" + success + ", msg=" + msg + ", object=" + object + "]";
	}
	
	
	
}
