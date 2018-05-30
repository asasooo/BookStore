package cn.lxa.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.lxa.user.Dao.UserDao;
import cn.lxa.user.domain.User;

public class UserService {
	UserDao ud = new UserDao();
	public boolean ajaxValidateLoginname (String loginname) throws SQLException{
		return ud.checkLoginName(loginname);
	}
	
	public boolean ajaxValidateEmail (String email) throws SQLException{
		return ud.checkEmail(email);
	}
	
	public String updatePass(String username,String oldPass,String newPass) throws SQLException{
		boolean b = ud.findByNamePass(username, oldPass);
		if(!b){
			return "旧密码错误！";
		}
		ud.updatePass(username, newPass);
		return "success";
	}
	
	public void activation(String code){
		User u = ud.addUser(code);
		if(u==null){
			try {
				throw new Exception("无效的激活码");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int status = u.getStatus();
		if(status == 1){
			try {
				throw new Exception("您已经激活过了！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ud.updateStatus(u.getUid(), 1);
	}
	
	public void regist(User u) {
		String uuid = ""+UUID.randomUUID();
		uuid = uuid.replace("-", "");
		u.setUid(uuid);
		u.setStatus(0);
		u.setActivationCode(uuid+uuid);
		try {
			ud.add(u);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("service");
		//发送邮件  密码一定要用授权密码
		Properties p = new Properties();
		try {
			p.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e) {
			new RuntimeException(e);
		}
		String host = p.getProperty("host");
		String username = p.getProperty("username");
		String password = p.getProperty("password");
		System.out.println(password);
		Session session = MailUtils.createSession(host, username, password);
		String from = p.getProperty("from");
		String to = u.getEmail();
		String subject = p.getProperty("subject");
		String content = MessageFormat.format(p.getProperty("content"), u.getLoginname(),u.getActivationCode());
		Mail mail = new Mail(from,to,subject,content);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("send");
		}

	public String cheakLogin(String username, String password) throws SQLException {
		return ud.cheakLogin(username,password);
	}

	public boolean userExist(String username) throws SQLException {
		return ud.userExist(username);
	}

	public boolean validateLoginpass(String pass, String username) throws SQLException {
		return ud.validateLoginpass(pass, username);
	}
}
