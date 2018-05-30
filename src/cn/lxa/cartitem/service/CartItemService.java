package cn.lxa.cartitem.service;

import java.sql.SQLException;
import java.util.List;

import cn.lxa.cartitem.Dao.CartItemDao;
import cn.lxa.cartitem.domain.CartItem;

public class CartItemService {
	CartItemDao cd = new CartItemDao();
	public List<CartItem> findByUsername(String loginname) throws SQLException {
		return cd.findByUsername(loginname);
	}

	public List<CartItem> addBook(String bid,String num, String uid) throws SQLException {
		return cd.addBook(bid,num,uid);
	}

	public CartItem updateQuantity(String cartItemId, String quantity) throws SQLException {
		return cd.updateQuantity(cartItemId,quantity);
	}

	public void batchDelete(String cartItemIdArray) throws SQLException {
		cd.batchDelete(cartItemIdArray);
	}

	public List<CartItem> loadCartItems(String cartItemId) throws SQLException {
		return cd.loadCartItems(cartItemId);
	}

}
