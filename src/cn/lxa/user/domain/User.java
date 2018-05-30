package cn.lxa.user.domain;

/**
 * User实体模块
 * 属性来自数据库表对象 和注册表单上的内容（除验证码）
 * @author Liuxang
 */
public class User {
	private String uid ;//主键
	private String loginname ; //登录名
	private String loginpass ; //登录密码
	private String reloginpass ;
	private String email ; //注册时填写的email
	private String activationCode ; //激活码 感觉没什么用..
	private int status ; //状态码 
	private String reloginPass ;
	private String verifyCode ; 
	public String getReloginpass() {
		return reloginpass;
	}
	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}
	public String getReloginPass() {
		return reloginPass;
	}
	public void setReloginPass(String reloginPass) {
		this.reloginPass = reloginPass;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpass() {
		return loginpass;
	}
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int b) {
		this.status = b;
	}
}
