package cn.lxa.admin.service;

import java.sql.SQLException;

import cn.lxa.admin.Dao.AdminDao;
import cn.lxa.admin.domain.Admin;

public class AdminService {
	AdminDao ad = new AdminDao();
	public boolean login(String adminname,String adminpass) throws SQLException{
		return ad.login(adminname,adminpass);
	}
}
