package cn.lxa.user.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.commons.CommonUtils;
import cn.lxa.user.domain.User;
import cn.lxa.user.service.UserService;

public class UserServlet extends HttpServlet {

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
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
//			m.invoke(this,request,response);
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
//			m.invoke(this,request,response);
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
	
	public void login(HttpServletRequest req,HttpServletResponse resp) throws SQLException, ServletException, IOException{
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		UserService us = new UserService();
		boolean userexist = us.userExist(username);
		if(!userexist){
			req.setAttribute("code", "error");
			req.setAttribute("msg", "这个用户名不存在哦！");
			try {
				req.getRequestDispatcher("jsps/msg.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		String uid = us.cheakLogin(username,password);
		if(uid!=null){
			//登陆成功转跳
			req.getSession().setAttribute("username", username);
			req.getSession().setAttribute("password", password);
			req.getSession().setAttribute("uid", uid);
			Cookie c = new Cookie("loginname",username);
			resp.addCookie(c);
			System.out.println("登陆成功");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);;
		}else{
			req.setAttribute("code", "error");
			req.setAttribute("msg", "您的密码有误 请重试！");
			try {
				req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updatePass(HttpServletRequest req,HttpServletResponse resp) throws SQLException, ServletException, IOException{
		UserService us = new UserService();
		String username = (String) req.getSession().getAttribute("username");
		String oldPass = req.getParameter("loginpass");
		String newPass = req.getParameter("newpass");
		if(username==null){
			req.setAttribute("msg", "您还没有登录哦！");
			req.setAttribute("code", "error");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
		}
		String status = us.updatePass(username, oldPass, newPass);
		if(status.equalsIgnoreCase("success")){
			req.setAttribute("msg", "修改成功！");
			req.setAttribute("code", "success");
			req.setAttribute("next", "Redirect");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
		}else{
			req.setAttribute("msg", status);
			req.setAttribute("code", "error");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
		}
	}
	
	public void activation(HttpServletRequest req,HttpServletResponse resp){
		String activationCode = req.getParameter("activationCode");
		UserService us = new UserService();
		try{
			us.activation(activationCode);
			req.setAttribute("msg", "激活成功");
			req.setAttribute("code", "success");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
		}catch(Exception e){
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");
			try {
				req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	//注销session
	public void invalidate(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException{
		req.getSession().invalidate();
		resp.sendRedirect("/goods/index.jsp");
	}
	
	public void validateLoginpass(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException{
		String pass = req.getParameter("loginpass");
		UserService us = new UserService();
		String username = (String)req.getSession().getAttribute("username");
		boolean b = us.validateLoginpass(pass,username);
		resp.getWriter().print(b);
	}
	
	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		String loginname = req.getParameter("loginname");
		UserService us = new UserService();
		boolean b = us.ajaxValidateLoginname(loginname);
		resp.getWriter().print(b);
		return null ;
	}
	
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		String email = req.getParameter("email");
		System.out.println("email");
		UserService us = new UserService();
		boolean b = us.ajaxValidateEmail(email);
		resp.getWriter().print(b);
		return null ;
	}
	
	public String ajaxValidateCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		String code = req.getParameter("verifyCode");
		System.out.println("use");
		int this_result = Integer.parseInt(code);
		int result = (int)req.getSession().getAttribute("result"); 
		if(this_result == result ){//比较验证码是否正确
			boolean b = true ;
			resp.getWriter().print(b);
		}else{
			resp.getWriter().print(false);
		}
		return null ;
	}
	
	public String regist(HttpServletRequest req, HttpServletResponse resp)  {
		User u = CommonUtils.toBean(req.getParameterMap(), User.class);
		System.out.println("toBean");
		UserService us = new UserService();
		Map<String, String> errors = null;
		try {
			errors = validateRegist(u,req.getSession());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("adderror");
		if(errors.size()>0){
			System.out.println("error");
			req.setAttribute("form", u);
			req.setAttribute("errors", errors);
			System.out.println(errors);
			try {
				req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
				return null ;
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		System.out.println("started us");
		us.regist(u);
		System.out.println("us completed");
		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册成功，请登录邮箱进行激活!");
		try {
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
			return null ;
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	private Map<String,String> validateRegist(User u,HttpSession session) throws SQLException{
		Map<String,String> error = new HashMap<String,String>();//将错误信息封装
		String loginname = u.getLoginname();
		if(loginname==null||loginname.trim().equals("")){
			error.put("loginname", "用户名不能为空！");
		}else if(!new UserService().ajaxValidateLoginname(loginname)){
			error.put("loginname", "用户名已被注册！");
		}else if(loginname.length()<3||loginname.length()>20){
			error.put("loginname", "用户名长度应在3~20字符之间！");
		}
		
		String password = u.getLoginpass();
		if(password==null||password.trim().equals("")){
			error.put("password", "密码不能为空！");
		}else if(password.length()<3||password.length()>20){
			error.put("password", "密码长度应在3~20字符之间！");
		}
		
		String repassword = u.getLoginpass();
		if(repassword==null||repassword.trim().equals("")){
			error.put("repassword", "密码不能为空！");
		}else if(repassword.length()<3||repassword.length()>20){
			error.put("repassword", "密码长度应在3~20字符之间！");
		}else if(repassword!=password){
			error.put("repassword", "两次输入的密码不一致！");
		}
		
		String email = u.getEmail();
		if(email==null||email.trim().equals("")){
			error.put("email", "邮箱不能为空！");
		}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			error.put("email", "邮箱地址格式不正确！");
		}else if(!new UserService().ajaxValidateEmail(email)){
			error.put("email", "邮箱已经被注册！");
		}
		
		String verifyCode = u.getVerifyCode();
		int code = Integer.parseInt(verifyCode);
		int resultint = (int)session.getAttribute("result");
		if(verifyCode==null||verifyCode.trim().equals("")){
			error.put("verifyCode", "验证码不能为空！");
		}else if(code!=resultint){
			error.put("verifyCode", "验证码不正确!");
		}
		return error;
	}
	

}
