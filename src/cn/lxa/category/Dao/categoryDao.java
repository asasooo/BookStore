package cn.lxa.category.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.lxa.category.domain.category;

/**
 * 分类持久层
 * @author qdmmy6
 *
 */
public class categoryDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/*
	 * 把一个Map中的数据映射到Category中
	 */
	private category toCategory(Map<String,Object> map) {
		/*
		 * map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		 * Category{cid:xx, cname:xx, parent:(cid=pid), desc:xx}
		 */
		category category = CommonUtils.toBean(map, category.class);
		String pid = (String)map.get("pid");// 如果是一级分类，那么pid是null
		if(pid != null) {//如果父分类ID不为空，
			/*
			 * 使用一个父分类对象来拦截pid
			 * 再把父分类设置给category
			 */
			category parent = new category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	
	/*
	 * 可以把多个Map(List<Map>)映射成多个Category(List<Category>)
	 */
	private List<category> toCategoryList(List<Map<String,Object>> mapList) {
		List<category> categoryList = new ArrayList<category>();//创建一个空集合
		for(Map<String,Object> map : mapList) {//循环遍历每个Map
			category c = toCategory(map);//把一个Map转换成一个Category
			categoryList.add(c);//添加到集合中
		}
		return categoryList;//返回集合
	}
	
	/**
	 * 返回所有分类
	 * @return
	 * @throws SQLException 
	 */
	public List<category> findAll() throws SQLException {
		/*
		 * 1. 查询出所有一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		
		List<category> parents = toCategoryList(mapList);
		
		/*
		 * 2. 循环遍历所有的一级分类，为每个一级分类加载它的二级分类 
		 */
		for(category parent : parents) {
			// 查询出当前父分类的所有子分类
			List<category> children = findByParent(parent.getCid());
			// 设置给父分类
			parent.setChildren(children);
		}
		return parents;
	}
	
	/**
	 * 通过父分类查询子分类
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public List<category> findByParent(String pid) throws SQLException {
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), pid);
		return toCategoryList(mapList);
	}
	
	/**
	 * 添加分类
	 * @param category
	 * @throws SQLException 
	 */
	public void add(category category) throws SQLException {
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		/*
		 * 因为一级分类，没有parent，而二级分类有！
		 * 我们这个方法，要兼容两次分类，所以需要判断
		 */
		String pid = null;//一级分类
		if(category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCid(), category.getCname(), pid, category.getDesc()};
		qr.update(sql, params);
	}
	
	/**
	 * 获取所有父分类，但不带子分类的！
	 * @return
	 * @throws SQLException
	 */
	public List<category> findParents() throws SQLException {
		/*
		 * 1. 查询出所有一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		
		return toCategoryList(mapList);
	}
	
	/**
	 * 加载分类
	 * 即可加载一级分类，也可加载二级分类
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(), cid));
	}
	
	/**
	 * 修改分类
	 * 即可修改一级分类，也可修改二级分类
	 * @param category
	 * @throws SQLException 
	 */
	public void edit(category category) throws SQLException {
		String sql = "update t_category set cname=?, pid=?, `desc`=? where cid=?";
		String pid = null;
		if(category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCname(), pid, category.getDesc(), category.getCid()};
		qr.update(sql, params);
	}
	
	/**
	 * 查询指定父分类下子分类的个数
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql = "select count(*) from t_category where pid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(), pid);
		return cnt == null ? 0 : cnt.intValue();
	}
	
	/**
	 * 删除分类
	 * @param cid
	 * @throws SQLException 
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql, cid);
	}

	public int getLastACategoryId() throws SQLException {
		String sql = "select max(cid) from t_category where pid is null";
		String num = (String)qr.query(sql, new ScalarHandler());
		return Integer.parseInt(num);
	}

	public void addACategory(int cid, String cname, String desc, int orderby) throws SQLException {
		String scid = ""+cid ;
		String sql = "insert into t_category(cid,cname,`desc`,orderBy) values(?,?,?,?)";
		qr.update(sql,scid,cname,desc,orderby);
		System.out.println("add"+cname);
	}

	public int getLastBCategoryId(String pid) throws SQLException {
		String sql = "select max(orderBy) from t_category where pid=?";
		int ipid = Integer.parseInt(pid);
		Number num = (Number)qr.query(sql, new ScalarHandler(),ipid);
		return num.intValue();
	}
	
	public void addBCategory(String pid, String cname, String desc) throws SQLException {
		int orderBy = getLastBCategoryId(pid);
		String cid = UUID.randomUUID().toString().replace("-", "");
		int ipid = Integer.parseInt(pid);
		String sql = "insert into t_category values(?,?,?,?,?)";
		qr.update(sql,cid,cname,pid,desc,orderBy);
	}

	public void deleteCategory(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql,cid);
	}

	public category findByCid(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		category c = qr.query(sql, new BeanHandler<category>(category.class),cid);
		return c;
	}

	public void updateCategoryInf(String cid, String cname, String desc) throws SQLException {
		String sql = "update t_category set cname=?,`desc`=? where cid=?";
		qr.update(sql,cname,desc,cid);
	}
}
