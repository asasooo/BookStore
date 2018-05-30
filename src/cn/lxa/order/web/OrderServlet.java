package cn.lxa.order.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lxa.PageBean.PageBean;
import cn.lxa.cartitem.domain.CartItem;
import cn.lxa.cartitem.service.CartItemService;
import cn.lxa.order.domain.Order;
import cn.lxa.order.domain.OrderItem;
import cn.lxa.order.service.OrderService;
import cn.lxa.utils.PaymentUtil;

public class OrderServlet extends HttpServlet {
	OrderService os = new OrderService();
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
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	//获得pc方法
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if(param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch(RuntimeException e) {}
		}
		return pc;
	}
	
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
		int index = url.lastIndexOf("&pc=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}
	
	public void findOrderByUid(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		String uid = (String) request.getSession().getAttribute("uid");
		String url = getUrl(request);
		int pc = getPc(request);
		PageBean<Order> list = os.findOrderByUid(uid,pc,url);
		request.setAttribute("pb", list);
		request.getRequestDispatcher("/jsps/order/list.jsp").forward(request, response);
	}
	
	public void findByOid(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		String oid = request.getParameter("oid");
		List<Order> orderList = os.fingByOid(oid);
		Order order = orderList.get(0);
		request.setAttribute("orderdesc", order);
		request.getRequestDispatcher("/jsps/order/desc.jsp").forward(request, response);
	}
	
	public void addOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		String uid = (String)request.getSession().getAttribute("uid");
		String ids = request.getParameter("cartIds");
		String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "utf-8");  
		List<CartItem> list = new CartItemService().loadCartItems(ids);
		if(list.size() == 0) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "您没有选择要购买的图书，不能下单！");
			request.getRequestDispatcher("/jsps/msg2.jsp").forward(request, response);
			return ;
		}
		if(address==null||address.trim().equals("请输入地址信息")) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "您没有填写收货地址");
			request.getRequestDispatcher("/jsps/msg2.jsp").forward(request, response);
			return ;
		}
		Order order = new Order();
		order.setOid(UUID.randomUUID().toString().replace("-", ""));
		order.setOrdertime(String.format("%tF %<tT", new Date()));
		System.out.println(address);
		order.setAddress(address);
		order.setUid((String)request.getSession().getAttribute("uid"));
		order.setStatus(1);
		double total = getTotal(list);
		order.setTotal(total);
		List<OrderItem> orderItemList = toOrderItemList(list);
		order.setOrderItemList(orderItemList);
		os.createOrder(order);
		request.setAttribute("orderinfo", order);
		request.getRequestDispatcher("/jsps/order/ordersucc.jsp").forward(request, response);
	}

	private List<OrderItem> toOrderItemList(List<CartItem> list) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for (CartItem cartItem : list) {
			OrderItem orderitem = new OrderItem();
			orderitem.setBid(cartItem.getBook().getBid());
			orderitem.setBname(cartItem.getBook().getBname());
			orderitem.setCurrPrice(cartItem.getBook().getCurrPrice());
			orderitem.setImage_b(cartItem.getBook().getImage_b());
			orderitem.setOrderItemId(UUID.randomUUID().toString().replace("-", ""));
			orderitem.setQuantity(cartItem.getQuantity());
			orderitem.setSubtotal(cartItem.getSubtotal());
			orderItemList.add(orderitem);
		}
		return orderItemList;
	}

	private double getTotal(List<CartItem> list) {
		double total = 0.0;
		for (CartItem cartItem : list) {
			total = total+cartItem.getSubtotal();
		}
		return total;
	}
	
	public void updateStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		int status = Integer.parseInt(request.getParameter("status"));
		String oid = request.getParameter("oid");
		String uid = (String)request.getSession().getAttribute("uid");
		os.updateStatus(status,oid);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "您的订单已取消！");
		request.getRequestDispatcher("/jsps/msg2.jsp").forward(request, response);
	}
	
	public void pay(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		String oid = request.getParameter("oid");
		Order order = os.payFindByOid(oid);
		request.setAttribute("order", order);
		request.getRequestDispatcher("/jsps/order/pay.jsp").forward(request, response);
	}
	
	public void payment(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		/*
		 * 1. 准备13个参数
		 */
		String p0_Cmd = "Buy";//业务类型，固定值Buy
		String p1_MerId = props.getProperty("p1_MerId");//商号编码，在易宝的唯一标识
		String p2_Order = req.getParameter("oid");//订单编码
		String p3_Amt = "0.01";//支付金额
		String p4_Cur = "CNY";//交易币种，固定值CNY
		String p5_Pid = "";//商品名称
		String p6_Pcat = "";//商品种类
		String p7_Pdesc = "";//商品描述
		String p8_Url = props.getProperty("p8_Url");//在支付成功后，易宝会访问这个地址。
		String p9_SAF = "";//送货地址
		String pa_MP = "";//扩展信息
		String pd_FrpId = req.getParameter("yh");//支付通道
		String pr_NeedResponse = "1";//应答机制，固定值1
		
		/*
		 * 2. 计算hmac
		 * 需要13个参数
		 * 需要keyValue
		 * 需要加密算法
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
		
		/*
		 * 3. 重定向到易宝的支付网关
		 */
		StringBuilder sb = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node");
		sb.append("?").append("p0_Cmd=").append(p0_Cmd);
		sb.append("&").append("p1_MerId=").append(p1_MerId);
		sb.append("&").append("p2_Order=").append(p2_Order);
		sb.append("&").append("p3_Amt=").append(p3_Amt);
		sb.append("&").append("p4_Cur=").append(p4_Cur);
		sb.append("&").append("p5_Pid=").append(p5_Pid);
		sb.append("&").append("p6_Pcat=").append(p6_Pcat);
		sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
		sb.append("&").append("p8_Url=").append(p8_Url);
		sb.append("&").append("p9_SAF=").append(p9_SAF);
		sb.append("&").append("pa_MP=").append(pa_MP);
		sb.append("&").append("pd_FrpId=").append(pd_FrpId);
		sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
		sb.append("&").append("hmac=").append(hmac);
		
		resp.sendRedirect(sb.toString());
	}
	
	public void back(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * 1. 获取12个参数
		 */
		String p1_MerId = req.getParameter("p1_MerId");
		String r0_Cmd = req.getParameter("r0_Cmd");
		String r1_Code = req.getParameter("r1_Code");
		String r2_TrxId = req.getParameter("r2_TrxId");
		String r3_Amt = req.getParameter("r3_Amt");
		String r4_Cur = req.getParameter("r4_Cur");
		String r5_Pid = req.getParameter("r5_Pid");
		String r6_Order = req.getParameter("r6_Order");
		String r7_Uid = req.getParameter("r7_Uid");
		String r8_MP = req.getParameter("r8_MP");
		String r9_BType = req.getParameter("r9_BType");
		String hmac = req.getParameter("hmac");
		/*
		 * 2. 获取keyValue
		 */
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		String keyValue = props.getProperty("keyValue");
		/*
		 * 3. 调用PaymentUtil的校验方法来校验调用者的身份
		 *   >如果校验失败：保存错误信息，转发到msg.jsp
		 *   >如果校验通过：
		 *     * 判断访问的方法是重定向还是点对点，如果要是重定向
		 *     修改订单状态，保存成功信息，转发到msg.jsp
		 *     * 如果是点对点：修改订单状态，返回success
		 */
		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId,
				r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType,
				keyValue);
		if(!bool) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "无效的签名，支付失败！");
			req.getRequestDispatcher("/jsps/msg2.jsp").forward(req, resp);
		}
		if(r1_Code.equals("1")) {
			os.updateStatus(2, r6_Order);
			if(r9_BType.equals("1")) {
				req.setAttribute("code", "success");
				req.setAttribute("msg", "恭喜，支付成功！");
				req.getRequestDispatcher("/jsps/msg2.jsp").forward(req, resp);				
			} else if(r9_BType.equals("2")) {
				resp.getWriter().print("success");
			}
		}
	}
	
}
