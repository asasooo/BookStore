package cn.lxa.user.Dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.JdbcUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.lxa.user.domain.User;

public class UserDao {
	QueryRunner qr = new TxQueryRunner();
	
	public User addUser(String code){
		String sql = "select count(*) from t_user where activationCode=?";
		try {
			return qr.query( sql,new BeanHandler<User>(User.class),code);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	public void updateStatus(String uid,int i){
		String sql = "update t_user set status=? where uid=?";
		Object[] params = {uid,i};
		try {
			qr.update( sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//检测用户是否注册
	public boolean checkLoginName(String loginName) throws SQLException{
		String sql = "select count(*) from t_user where loginname=?";
		Number num = (Number)qr.query( sql, new ScalarHandler(),loginName);
		if(num.intValue()==0){
			return true ;
		}
		return false ;
	}
	
	
	public boolean checkEmail(String email) throws SQLException{
		String sql = "select count(*) from t_user where email=?";
		Number num = (Number)qr.query(sql, new ScalarHandler(),email);
		if(num.intValue()==0){
			return true ;
		}
		return false ;
	}
	
	public void add(User u) throws SQLException{
		String sql = "insert into t_user values(?,?,?,?,?,?)";
		System.out.println("adduser");
		Object[] params = {u.getUid(),u.getLoginname(),u.getLoginpass(),u.getEmail(),""+u.getStatus(),u.getActivationCode()};
		qr.update( sql,params);
	}

	public String cheakLogin(String username, String password) throws SQLException {
		String sql = "select loginpass from t_user where loginname=?";
		String realpass = (String) qr.query( sql, new ScalarHandler(),username);
		if(realpass.equals(password)){
			String sql2 = "select uid from t_user where loginname=?";
			String uid = (String) qr.query( sql2, new ScalarHandler(),username);
			System.out.println("uid=!"+uid);
			return uid; 
		}else {
			return null ;
		}
	}
	
	public boolean findByNamePass(String username ,String password) throws SQLException{
		String sql = "select count(*) from t_user where loginname=? and loginpass=?";
		Number n = (Number)qr.query( sql, new ScalarHandler(),username,password);
		if(n.intValue()==1){
			return true ; 
		}else{
			return false ;
		}
	}
	
	public boolean updatePass(String username,String pass){
		String sql = "update t_user set loginpass=? where loginname=?";
		try {
			qr.update(sql,pass,username);
		} catch (SQLException e) {
			e.printStackTrace();
			return false ;
		}
		return true ;
	}

	public boolean userExist(String username) throws SQLException {
		String sql = "select count(*) from t_user where loginname=?";
		Number num = (Number)qr.query(sql, new ScalarHandler(),username);
		if(num.intValue()==0){
			return false ;
		}else{
			return true ;
		}
	}

	public boolean validateLoginpass(String pass, String username) throws SQLException {
		String sql = "select count(1) from t_user where loginname=? and loginpass=?";
		Number num = (Number)qr.query(sql, new ScalarHandler(),username,pass);
		if(num.intValue()==1){
			return true ;
		}else {
			return false ;
		}
	}
}
