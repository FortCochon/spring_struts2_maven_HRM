package com.company.hrm.common;

import java.io.Serializable;

public class ResResult<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  //json传值 需要序列化
	private Integer code;//integer返回值可为空
	private String msg;
	private T data;
	
	public ResResult() {//有类要继承这个类的时候 ，一定要由无参构造 
		super();
	}

	public ResResult(Integer code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ResResult(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResResult(Integer code) {
		super();
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	//静态工具方法
	public static ResResult success(){
		return new ResResult(200);
	}
	
	public static ResResult success(String msg){
		return new ResResult(200,msg);
	}
	
	public static<T> ResResult<T> success(String msg,T data){
		return new ResResult(200,msg,data);
	}
	
	
	public static ResResult error(Integer code,String msg){
		return new ResResult(code,msg);
	}
	

}
