package cn.lxa.admin.Dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;

public class AdminDao {
	QueryRunner qr = new TxQueryRunner();
	public boolean login(String adminname, String adminpass) throws SQLException{
		String sql = "select adminpwd from t_admin where adminname=?";
		String realpwd = (String) qr.query(sql, new ScalarHandler(),adminname);
		if(realpwd == null){
			return false ; 
		}else{
			if(adminpass.equals(realpwd)){
				return true ;
			}else{
				return false ;
			}
		}
	}
}
