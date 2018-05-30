package cn.lxa.cartitem.domain;

import java.math.BigDecimal;

import cn.lxa.book.domain.Book;
import cn.lxa.user.domain.User;

public class CartItem {
	private String cartItemId;// 主键
	private int quantity;// 数量
	private Book book;// 条目对应的图书
	private User user;// 所属用户
	private double subtotal ; // 总价格
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getSubtotal() {
		/* 
		 * 必须存在Book对象 不然currPrice不存在
		 * 
		 * 使用BigDecimal不会有误差
		 * 要求必须使用String类型构造器
		 */
		BigDecimal b1 = new BigDecimal(book.getCurrPrice() + "");
		BigDecimal b2 = new BigDecimal(quantity + "");
		BigDecimal b3 = b1.multiply(b2);
		System.out.println(b3);
		return b3.doubleValue();
	}
	
	public void setSubtotal() {
		/* 
		 * 必须存在Book对象 不然currPrice不存在
		 * 
		 * 使用BigDecimal不会有误差
		 * 要求必须使用String类型构造器
		 */
		BigDecimal b1 = new BigDecimal(book.getCurrPrice() + "");
		BigDecimal b2 = new BigDecimal(quantity + "");
		BigDecimal b3 = b1.multiply(b2);
		subtotal = b3.doubleValue() ;
	}
	
}
