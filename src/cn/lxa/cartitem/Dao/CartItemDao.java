package cn.lxa.cartitem.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.lxa.book.domain.Book;
import cn.lxa.cartitem.domain.CartItem;
import cn.lxa.user.domain.User;

public class CartItemDao {
	QueryRunner qr = new TxQueryRunner();
	public List<CartItem> findByUsername(String loginname) throws SQLException {
		List<Map<String,Object>> aList = findAllByUsername(loginname);
		List<CartItem> cList = toCartItemList(aList);
		return cList;
	}

	private List<Map<String, Object>> findAllByUsername(String loginname) throws SQLException {
		String sql = "select * from t_book b ,t_cartitem c where b.bid=c.bid and c.uid=?";
		List<Map<String,Object>> aList = qr.query(sql, new MapListHandler(), loginname);
		return aList;
	}
	
	private List<CartItem> toCartItemList(List<Map<String,Object>> list){
		List<CartItem> cList = new ArrayList<CartItem>();  
		for (Map<String, Object> map : list) {
			CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
			Book book = CommonUtils.toBean(map, Book.class);
			User user = CommonUtils.toBean(map, User.class);
			cartItem.setBook(book);
			cartItem.setUser(user);
			cartItem.setSubtotal();
			cList.add(cartItem);
		}
		return cList ;
	}

	public List<CartItem> addBook(String bid,String num ,String uid) throws SQLException {
		int exist = existCart(bid,uid);
		if(exist!=0){
			updateBook(uid,bid,num,exist);
		}else if(exist==0){
			buyBook(uid,bid,num);
		}
		return findByUsername(uid);
	}

	private void buyBook(String uid, String bid, String num) throws SQLException {
		String sql = "insert into t_cartitem (cartItemId,quantity,bid,uid) values(?,?,?,?)";
		String cartItemId = UUID.randomUUID().toString().replace("-", "");
		qr.update(sql,cartItemId,num,bid,uid);
	}

	private void updateBook(String uid, String bid, String num,int exist) throws SQLException {
		int quantity = Integer.parseInt(num)+exist ;
		String sql = "update t_cartitem set quantity=? where uid=? and bid=?";
		qr.update(sql, quantity,uid,bid);
	}

	private int existCart(String bid, String uid) throws SQLException {
		String sql = "select quantity from t_cartitem where bid=? and uid=?";
		Number num = (Number)qr.query(sql, new ScalarHandler(),bid,uid);
		if(num==null){
			return 0 ;
		}else{
			return num.intValue() ;
		}
	}
	
	/*
	 * 开一次忘了 多表查询 ... 
	 */
	public CartItem updateQuantity(String cartItemId, String quantity) throws SQLException {
		String sql1 = "update t_cartitem set quantity=? where cartItemId=?";
		int num = Integer.parseInt(quantity);
		qr.update(sql1,num,cartItemId);
		String sql2 = "select * from t_cartitem c ,t_book b where c.bid=b.bid and c.cartItemId=?";
		Map<String,Object> map = qr.query(sql2, new MapHandler(),cartItemId);
		CartItem c = toCartItem(map);
		return c;
	}
	
	private CartItem toCartItem(Map<String,Object> map) {
		if(map == null || map.size() == 0) return null;
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		User user = CommonUtils.toBean(map, User.class);
		cartItem.setBook(book);
		cartItem.setUser(user);
		return cartItem;
	}

	public void batchDelete(String cartItemIdArray) throws SQLException {
		Object[] array = cartItemIdArray.split(",");
		int len = array.length;
		StringBuilder sb = new StringBuilder("cartItemId in(");
		for(int i = 0; i < len; i++) {
			sb.append("?");
			if(i < len - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		String sql = "delete from t_cartitem where "+sb.toString();
		qr.update(sql, array);
	}

	public List<CartItem> loadCartItems(String cartItemId) throws SQLException {
		StringBuilder sb = new StringBuilder("");
		Object[] list = cartItemId.split(",");
		for(int i=0;i<list.length;i++){
			sb.append("?");
			if(i!=list.length-1){
				sb.append(",");
			}
		}
		sb.append(")");
		System.out.println(sb);
		String sql = "select * from t_cartitem c , t_book b where b.bid=c.bid and c.cartItemId in("+sb.toString();
		List<Map<String,Object>> map = qr.query(sql, new MapListHandler(),list);
		List<CartItem> cartList = toCartItemList(map);
		return cartList;
	}

}
