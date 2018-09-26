package com.oper.util;
/**
 * 命名规范   err+_+ 错误英文
 * @author 李书根
 *
 */
public  enum ErrEnumConfig {
	
	ret_0("请求成功"),
	
	ret_1("系统错误"),
	/**
	 * 成功
	 */
	success("请求成功"),
	/**
	 * 失败
	 */
	fail("请求失败");

	
	private String msg;

	  
    private ErrEnumConfig( String msg) {
        this.msg = msg;
        
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    

}
