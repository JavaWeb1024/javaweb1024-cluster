package com.dinghao.exception;

/**
  * @ClassName: DHBizException
  * @Description: TODO  业务异常类
  * @author helong 
  * @date 2016年1月5日 下午4:11:21
  * @version V1.0
  *
 */
public class DHBizException extends RuntimeException{

	/**
	  * @Fields serialVersionUID : TODO（运行时异常）
	  */
	private static final long serialVersionUID = 6686232091776535886L;

	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public DHBizException(Throwable throwable) {
		super(throwable);
	}
	
	public DHBizException(Throwable throwable,String message) {
		super(message,throwable);
		setErrorMsg(message);
	}
	
	public DHBizException(String message) {
		super(message);
		setErrorMsg(message);
	}
	
}
