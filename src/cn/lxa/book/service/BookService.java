package cn.lxa.book.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.lxa.PageBean.Expression;
import cn.lxa.PageBean.PageBean;
import cn.lxa.book.Dao.PageDao;
import cn.lxa.book.domain.Book;

public class BookService {
	private PageDao bd = new PageDao();
	public PageBean<Book> findByCategory(String cid , int pc ) throws SQLException{
		return bd.findByCategory(cid,pc);
	}
	
	public PageBean<Book> findByAuthor(String author , int pc ) throws SQLException{
		return bd.findByAuthor(author,pc);
	}
	
	public PageBean<Book> findByCombination(Book book , int pc ) throws SQLException{
		return bd.findByCombination(book,pc);
	}
	
	public PageBean<Book> findByPress(String press , int pc ) throws SQLException{
		return bd.findByPress(press,pc);
	}
	
	public PageBean<Book> findByName(String bname , int pc ) throws SQLException{
		return bd.findByName(bname,pc);
	}

	public Book findByBid(String bid) throws SQLException {
		return bd.findByBid(bid);
	}
	
}
