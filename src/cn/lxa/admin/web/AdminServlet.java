package cn.lxa.admin.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lxa.admin.domain.Admin;
import cn.lxa.admin.service.AdminService;

public class AdminServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Class c = this.getClass();
		String method = request.getParameter("method");
		Method m = null;
		try {
			m = c.getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
		try {
			m.invoke(this,request,response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Class c = this.getClass();
		String method = request.getParameter("method");
		Method m = null;
		try {
			m = c.getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
		try {
			m.invoke(this,request,response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}	
	AdminService as = new AdminService();
	public void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		String adminname = request.getParameter("adminname");
		String adminpass = request.getParameter("adminpwd");
		if(as.login(adminname,adminpass)){
			request.getSession().setAttribute("admin", adminname);
			request.getRequestDispatcher("/adminjsps/admin/index.jsp").forward(request, response);
		}else{
			request.setAttribute("msg", "您的用戶名或密码错误！");
			request.getRequestDispatcher("/adminjsps/msg.jsp").forward(request, response);
		}
	}

}
