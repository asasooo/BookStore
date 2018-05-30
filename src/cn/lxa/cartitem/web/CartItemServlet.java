package cn.lxa.cartitem.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lxa.cartitem.domain.CartItem;
import cn.lxa.cartitem.service.CartItemService;

public class CartItemServlet extends HttpServlet {
	CartItemService cs = new CartItemService();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		System.out.println(method);
		try {
			Method m = this.getClass().getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			try {
				m.invoke(this.getClass().newInstance(), request,response);
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		System.out.println(method);
		try {
			Method m = this.getClass().getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			try {
				m.invoke(this.getClass().newInstance(), request,response);
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void findByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		String loginname = request.getParameter("uid");
		System.out.println(loginname);
		List<CartItem> list = cs.findByUsername(loginname);
		request.setAttribute("cartList", list);
		request.getRequestDispatcher("/jsps/cart/list.jsp").forward(request, response);
	}
	
	public void updateQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		String cartItemId = request.getParameter("cartItemId");
		String quantity = request.getParameter("quantity");
		CartItem cartItem = cs.updateQuantity(cartItemId,quantity);
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");
		response.getWriter().print(sb.toString());
	}
	
	public void batchDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		String cartItemIdArray = request.getParameter("cartItemIds");
		cs.batchDelete(cartItemIdArray);
		String uid = (String) request.getSession().getAttribute("uid");
		List<CartItem> list = cs.findByUsername(uid);
		request.setAttribute("cartList", list);
		request.getRequestDispatcher("/jsps/cart/list.jsp").forward(request, response);
	}
	
	public void buyBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		String bid = request.getParameter("bid");
		String uid = (String) request.getSession().getAttribute("uid");
		String num = request.getParameter("quantity");
		System.out.println("buy uid+"+uid);
		List<CartItem> list = cs.addBook(bid,num,uid);
		request.setAttribute("cartList", list);
		request.getRequestDispatcher("/jsps/cart/list.jsp").forward(request, response);
	}
	
	public void loadCartItems(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		double total = Double.parseDouble(request.getParameter("total"));
		String cartItemId = request.getParameter("cartItemIds");
		List<CartItem> list = cs.loadCartItems(cartItemId);
		request.setAttribute("total", total);
		request.setAttribute("cartItemList", list);
		request.setAttribute("cartItemIds", cartItemId);
		request.getRequestDispatcher("/jsps/cart/showitem.jsp").forward(request, response);
	}
	
}
