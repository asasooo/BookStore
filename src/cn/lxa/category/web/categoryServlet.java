package cn.lxa.category.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lxa.category.domain.category;
import cn.lxa.category.service.categoryService;

public class categoryServlet extends HttpServlet {
	// 映射执行方法BasicServlet
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Class c = this.getClass();
		String method = request.getParameter("method");
		try {
			Method m = c.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			try {
				m.invoke(this, request,response);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Class c = this.getClass();
		String method = request.getParameter("method");
		try {
			Method m = c.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			try {
				m.invoke(this, request,response);
				System.out.println(method);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		categoryService cs = new categoryService();
		List<category> list = cs.findAll();
		req.setAttribute("parents", list);
		req.getRequestDispatcher("/jsps/left.jsp").forward(req, resp);
	}
	
	public void findAllToEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		categoryService cs = new categoryService();
		List<category> list = cs.findAll();
		req.setAttribute("parents", list);
		req.getRequestDispatcher("/adminjsps/admin/book/left.jsp").forward(req, resp);
	}
	
	public void findAlltoList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		categoryService cs = new categoryService();
		List<category> list = cs.findAll();
		req.setAttribute("category", list);
		req.getRequestDispatcher("/adminjsps/admin/category/list.jsp").forward(req, resp);
	}
	
	public void findByPid(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException{
		categoryService cs = new categoryService();
		String pid = req.getParameter("pid");
		List<category> list = cs.findByPid(pid);
		String json = toJson(list);
		resp.getWriter().print(json);
	}
	
	private String toJson(category category) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	private String toJson(List<category> categoryList) {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < categoryList.size(); i++) {
			sb.append(toJson(categoryList.get(i)));
			if(i < categoryList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public void findAllToAddBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		categoryService cs = new categoryService();
		List<category> list = cs.findAll();
		req.setAttribute("parents", list);
		req.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(req, resp);
	}
	
	public void addACategory(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException{
		categoryService cs = new categoryService();
		String cname = req.getParameter("cname");
		String desc = req.getParameter("desc");
		cs.addACategory(cname,desc);
		req.getRequestDispatcher("/categoryServlet?method=findAlltoList").forward(req, resp);
	}
	
	public void addBCategory(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException{
		categoryService cs = new categoryService();
		String cname = req.getParameter("cname");
		String pid = req.getParameter("pid");
		String desc = req.getParameter("desc");
		cs.addBCategory(pid,cname,desc);
		req.getRequestDispatcher("/categoryServlet?method=findAlltoList").forward(req, resp);
	}
	
	public void deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException{
		categoryService cs = new categoryService();
		String cid = req.getParameter("cid");
		cs.deleteCategory(cid);
		req.getRequestDispatcher("/categoryServlet?method=findAlltoList").forward(req, resp);
	}
	
	public void navigation(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException{
		categoryService cs = new categoryService();
		List<category> list = cs.findAll();
		req.setAttribute("parents", list);
		req.getRequestDispatcher("/adminjsps/admin/category/add2.jsp").forward(req, resp);
	}
	
	public void navigationToEdit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException{
		categoryService cs = new categoryService();
		String cid = req.getParameter("cid");
		category c = cs.findByCid(cid);
		req.setAttribute("c", c);
		req.getRequestDispatcher("/adminjsps/admin/category/edit.jsp").forward(req, resp);
	}
	
	public void navigationToEdit2(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException{
		categoryService cs = new categoryService();
		String cid = req.getParameter("cid");
		category c = cs.findByCid(cid);
		req.setAttribute("c", c);
		req.getRequestDispatcher("/adminjsps/admin/category/edit2.jsp").forward(req, resp);
	}
	
	public void updateCategoryInf(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException{
		categoryService cs = new categoryService();
		String cid = req.getParameter("cid");
		String cname = req.getParameter("cname");
		String desc = req.getParameter("desc");
		cs.updateCategoryInf(cid,cname,desc);
		req.getRequestDispatcher("/categoryServlet?method=findAlltoList").forward(req, resp);
	}
	
}
