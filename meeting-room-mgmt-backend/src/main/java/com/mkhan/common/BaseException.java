package com.mkhan.common;


public class BaseException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5669198938110295224L;
	private final String ERR_CODE;
	
	public BaseException(String errCode,String message){
		super(message);
		ERR_CODE = errCode;
    }
	
	public BaseException(String message){
        this("500",message); //최초 에러코드는 우선 500 
    }
	
	public String getErrCode(){
		return ERR_CODE; 
	}

}
