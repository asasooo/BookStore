package cn.lxa.order.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.lxa.PageBean.PageContents;
import cn.lxa.order.domain.Order;
import cn.lxa.order.domain.OrderItem;

public class OrderDao {
	QueryRunner qr = new TxQueryRunner();
	public List<Order> findOrderByUid(String uid, int pc, int ps) throws SQLException {
		String sql = "select oid from t_order where uid=? order by ordertime desc limit?,?";
		List<Map<String,Object>> list = qr.query(sql, new MapListHandler(), uid,(pc-1)*ps,ps);
		return toOrderList(list);
	}
	
	public List<Order> toOrderList(List<Map<String,Object>> oidList) throws SQLException {
		String sql = "select * from t_order where oid=?";
		List<Order> orderList = new ArrayList<Order>();
		for (Map map : oidList) {
			String oid = (String)map.get("oid");
			List<OrderItem> orderItemList = getOrderItemList(oid);
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
			order.setOrderItemList(orderItemList);
			orderList.add(order);
		}
		return orderList;
	}

	public List<OrderItem> getOrderItemList(String oid) throws SQLException {
		String sql = "select * from t_orderitem where oid=?";
		List<OrderItem> itemList = qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), oid);
		return itemList;
	}

	public int getTr(String uid) throws SQLException {
		String sql = "select count(*) from t_order where uid=?";
		Number num = (Number)qr.query(sql, new ScalarHandler(), uid);
		return num.intValue();
	}

	public void createOrder(Order order) throws SQLException {
		String sql1 = "insert into t_order values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getStatus(),order.getAddress(),order.getUid()};
		qr.update(sql1, params);
		String sql2 = "insert into t_orderitem values(?,?,?,?,?,?,?,?)";
		for (OrderItem orderitem : order.getOrderItemList()) {
			Object[] itemparams = {orderitem.getOrderItemId(),orderitem.getQuantity(),orderitem.getSubtotal(),orderitem.getBid(),
					orderitem.getBname(),orderitem.getCurrPrice(),orderitem.getImage_b(),order.getOid()};
			qr.update(sql2, itemparams);
		}
	}

	public void updateStatus(int status, String oid) throws SQLException {
		String sql = "update t_order set status=? where oid=?";
		qr.update(sql, status,oid);
		
	}

	public Order payFindByOid(String oid) throws SQLException {
		String sql = "select * from t_order where oid=?";
		Order order = qr.query(sql,new BeanHandler<Order>(Order.class), oid);
		return order;
	}

	public List<Order> findAll(int pc) throws SQLException {
		String sql1 = "select * from t_order order by ordertime desc limit?,?";
		int ps = PageContents.OrderSize;
		List<Order> list = qr.query(sql1, new BeanListHandler<Order>(Order.class),(pc-1)*ps,ps);
		for (Order order : list) {
			String oid = order.getOid();
			String sql2 = "select * from t_orderitem where oid=?";
			List<OrderItem> itemList = qr.query(sql2, new BeanListHandler<OrderItem>(OrderItem.class),oid);
			order.setOrderItemList(itemList);
		}
		return list;
	}

	public int getAllTr() throws SQLException {
		String sql = "select count(*) from t_order";
		Number num = (Number)qr.query(sql, new ScalarHandler());
		return num.intValue();
	}

	public List<Order> findByStatus(int status,int pc ) throws SQLException {
		String sql = "select * from t_order where status=? order by ordertime desc limit?,?";
		int ps = PageContents.OrderSize;
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),status,(pc-1)*ps,ps);
		for (Order order : list) {
			String oid = order.getOid();
			String sql2 = "select * from t_orderitem where oid=?";
			List<OrderItem> itemList = qr.query(sql2, new BeanListHandler<OrderItem>(OrderItem.class),oid);
			order.setOrderItemList(itemList);
		}
		return list;
	}

	public int getTrByStatus(int status) throws SQLException {
		String sql = "select count(*) from t_order where status=?";
		Number num = (Number)qr.query(sql, new ScalarHandler(),status);
		return num.intValue();
	}
	
	}
