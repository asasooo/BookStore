package cn.lxa.category.service;

import java.sql.SQLException;
import java.util.List;

import cn.lxa.category.Dao.categoryDao;
import cn.lxa.category.domain.category;

public class categoryService {
	categoryDao cd = new categoryDao();
	public List<category> findAll() throws SQLException{
		return cd.findAll();
	}
	public void addACategory(String cname, String desc) throws SQLException {
		int locid = cd.getLastACategoryId();
		int cid = locid+1;
	}
	public void addBCategory(String pid, String cname, String desc) throws SQLException {
		cd.addBCategory(pid,cname,desc);
	}
	public void deleteCategory(String cid) throws SQLException {
		cd.deleteCategory(cid);
	}
	public category findByCid(String cid) throws SQLException {
		return cd.findByCid(cid);
	}
	public void updateCategoryInf(String cid, String cname, String desc) throws SQLException {
		cd.updateCategoryInf(cid,cname,desc);
	}
	public List<category> findByPid(String pid) throws SQLException {
		return cd.findByPid(pid);
	}
}
