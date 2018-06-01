package cn.lxa.book.web;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;
import cn.lxa.book.domain.Book;
import cn.lxa.book.service.BookService;
import cn.lxa.category.domain.category;

public class AddBookServlet extends HttpServlet {
	private void error(String msg, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").
				forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置字符
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(80*1024); //设置文件的尺寸
		List<FileItem> list = null ;
		try{
			list = sfu.parseRequest(request);
		}catch(Exception e){
			error("文件过大 请重试",request,response);
			return ;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		for (FileItem fileItem : list) {
			if(fileItem.isFormField()){
				map.put(fileItem.getFieldName(),fileItem.getString("UTF-8"));
			}
		}
		Book b = CommonUtils.toBean(map, Book.class);
		category c = CommonUtils.toBean(map,category.class);
		b.setParent(c);
		
		FileItem fi = list.get(1); //获取大图  下标为1
		String filename = fi.getName();
		int index = filename.lastIndexOf("\\");
		if(index!=-1){
			filename = filename.substring(index+1);
		}
		filename = UUID.randomUUID().toString().replace("-", "")+"_"+filename;
		if(!filename.toLowerCase().endsWith(".jpg")) {
			error("上传的图片扩展名必须是JPG", request, response);
			return;
		}
		String savepath = request.getServletContext().getRealPath("/book_image");
		//保存到服务器
		File destFile = new File(savepath,filename);
		try {
			fi.write(destFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
		Image image = icon.getImage();
		if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
			error("您上传的图片尺寸超出了350*350！", request, response);
			destFile.delete();//删除图片
			return;
		}// 把图片的路径设置给book对象
		b.setImage_w("book_img/" + filename);
		fi = list.get(2);//获取小图
		filename = fi.getName();
		// 截取文件名，因为部分浏览器上传的绝对路径
		index = filename.lastIndexOf("\\");
		if(index != -1) {
			filename = filename.substring(index + 1);
		}
		filename = CommonUtils.uuid() + "_" + filename;
		if(!filename.toLowerCase().endsWith(".jpg")) {
			error("上传的图片扩展名必须是JPG", request, response);
			return;
		}
		/*
		 * 保存图片：
		 * 1. 获取真实路径
		 */
		savepath = this.getServletContext().getRealPath("/book_img");
		/*
		 * 2. 创建目标文件
		 */
		destFile = new File(savepath, filename);
		/*
		 * 3. 保存文件
		 */
		try {
			fi.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 校验尺寸
		// 1. 使用文件路径创建ImageIcon
		icon = new ImageIcon(destFile.getAbsolutePath());
		// 2. 通过ImageIcon得到Image对象
		image = icon.getImage();
		// 3. 获取宽高来进行校验
		if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
			error("您上传的图片尺寸超出了350*350！", request, response);
			destFile.delete();//删除图片
			return;
		}
		
		// 把图片的路径设置给book对象
		b.setImage_b("book_img/" + filename);
		b.setBid(UUID.randomUUID().toString().replace("-", ""));
		BookService bookService = new BookService();
		try {
			bookService.addBookInf(b);
			request.setAttribute("msg", "添加图书成功！");
			request.getRequestDispatcher("/adminjsps/msg.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
