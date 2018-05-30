package cn.lxa.order.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.jdbc.JdbcUtils;
import cn.lxa.PageBean.PageBean;
import cn.lxa.PageBean.PageContents;
import cn.lxa.order.Dao.OrderDao;
import cn.lxa.order.domain.Order;
import cn.lxa.order.domain.OrderItem;

public class OrderService {
	OrderDao od = new OrderDao();
	public PageBean<Order> findOrderByUid(String uid, int pc ,String url) throws SQLException {
		PageBean<Order> pb = new PageBean<Order>();
		try{ // 开始事物 结束事物
			JdbcUtils.beginTransaction();
			int ps = PageContents.OrderSize;
			List<Order> list = od.findOrderByUid(uid,pc,ps);
			int tr = od.getTr(uid);
			JdbcUtils.commitTransaction();
			pb.setTr(tr);
			pb.setBeanList(list);
			pb.setPc(pc);
			pb.setPs(ps);
			pb.setTp();
			pb.setUrl(url);
		}catch(Exception e){
			JdbcUtils.rollbackTransaction();
			e.printStackTrace();
			return pb ;
		}
		return pb;
	}
	
	public List<Order> fingByOid(String oid) throws SQLException{
		Map oneOid = new HashMap();
		List<Map<String, Object>> oneOidList = new ArrayList();
		oneOid.put("oid", oid);
		oneOidList.add(oneOid);
		return od.toOrderList(oneOidList);
	}

	public void createOrder(Order order) throws SQLException {
		od.createOrder(order);
	}

	public void updateStatus(int status, String oid) throws SQLException {
		od.updateStatus(status,oid);
	}

	public Order payFindByOid(String oid) throws SQLException {
		return od.payFindByOid(oid);
	}

}
