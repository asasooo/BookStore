package cn.lxa.book.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.lxa.PageBean.Expression;
import cn.lxa.PageBean.PageBean;
import cn.lxa.PageBean.PageContents;
import cn.lxa.book.domain.Book;

public class PageDao {
	QueryRunner qr = new TxQueryRunner();
	
	/*
	 * 按类别查找
	 */
	public PageBean<Book> findByCategory(String cid , int pc ) throws SQLException{
		Expression exp = new Expression();
		exp.setName("cid");
		exp.setOperator("=");
		exp.setValue(cid);
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(exp);
		return findByCriteria(expList,pc);
	}
	
	/*
	 * 根据作者查找
	 */
	public PageBean<Book> findByAuthor(String author , int pc ) throws SQLException{
		Expression exp = new Expression();
		exp.setName("author");
		exp.setOperator("like");
		exp.setValue("%"+author+"%");
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(exp);
		return findByCriteria(expList,pc);
	}
	
	/*
	 * 多条件组合查询  全部使用模糊搜索
	 */
	public PageBean<Book> findByCombination(Book book,int pc) throws SQLException{
		Expression exp1 = new Expression();
		exp1.setName("bname");
		exp1.setOperator("like");
		exp1.setValue("%"+book.getBname()+"%");
		Expression exp2 = new Expression();
		exp2.setName("author");
		exp2.setOperator("like");
		exp2.setValue("%"+book.getAuthor()+"%");
		Expression exp3 = new Expression();
		exp3.setName("press");
		exp3.setOperator("like");
		exp3.setValue("%"+book.getPress()+"%");
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(exp1);
		expList.add(exp2);
		expList.add(exp3);
		return findByCriteria(expList,pc);
	}
	
	/*
	 * 根据出版社查找
	 */
	public PageBean<Book> findByPress(String press , int pc ) throws SQLException{
		Expression exp = new Expression();
		exp.setName("press");
		exp.setOperator("like");
		exp.setValue("%"+press+"%");
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(exp);
		return findByCriteria(expList,pc);
	}	
	
	/*
	 * 按照书名模糊查找
	 */
	public PageBean<Book> findByName(String bname , int pc ) throws SQLException{
		Expression exp = new Expression();
		exp.setName("bid");
		exp.setOperator("like");
		exp.setValue("%"+bname+"%");
		List<Expression> expList = new ArrayList<Expression>();
		expList.add(exp);
		return findByCriteria(expList,pc);
	}
	
	/*
	 * 通用查询模板
	 */
	public PageBean<Book> findByCriteria(List<Expression> expList , int pc ) throws SQLException{
		int ps = PageContents.PageSize;
		StringBuilder sb = new StringBuilder(" where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		for (Expression expression : expList) {
			sb.append("and ").append(expression.getName()).append(" ").append(expression.getOperator());
			if(!(expression.getOperator()).equalsIgnoreCase("is null")){
				sb.append("?");
				params.add(expression.getValue());
			}
		}
		String sql = "select count(*) from t_book"+sb.toString();
		Number num = (Number) qr.query( sql, new ScalarHandler(),params.toArray());
		int tr = num.intValue();
		sql = "select * from t_book"+sb.toString()+" order by orderBy limit ?,?";
		params.add((pc-1)*ps);
		params.add(ps);
		List<Book> bookList = qr.query( sql, new BeanListHandler<Book>(Book.class),params.toArray());
		//创建新的PageBean 进行赋值
		PageBean pb = new PageBean();
		pb.setBeanList(bookList);
		pb.setTr(tr);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTp();
		return pb;
	}

	public Book findByBid(String bid) throws SQLException {
		String sql = "select * from t_book where bid=?";
		Book book = qr.query(sql, new BeanHandler<Book>(Book.class),bid);
		return book;
	}

	public void addBookInf(Book book) throws SQLException {
		String sql = "insert into t_book(bid,bname,author,price,currPrice," +
				"discount,press,publishtime,edition,pageNum,wordNum,printtime," +
				"booksize,paper,cid,image_w,image_b)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {book.getBid(),book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), book.getParent().getCid(),
				book.getImage_w(),book.getImage_b()};
		qr.update(sql, params);
	}

	private int getMaxOrderBy(int cid) throws SQLException {
		String sql = "select max(orderBy) from t_book where cid=?";
		Number num = (Number) qr.query(sql, new ScalarHandler(),cid);
		return num.intValue();
	}

	public Book load(String bid) throws SQLException {
		String sql = "select * from t_book where bid=?";
		Book b = qr.query(sql, new BeanHandler<Book>(Book.class),bid);
		return b;
	}

	public void edit(Book book) throws SQLException {
		delete(book.getBid());
		String sql2 = "insert into t_book(bid,bname,author,price,currPrice," +
				"discount,press,publishtime,edition,pageNum,wordNum,printtime," +
				"booksize,paper,cid,image_w,image_b)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {book.getBid(),book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), book.getParent().getCid(),
				book.getImage_w(),book.getImage_b()};
		qr.update(sql2, params);
	}

	public void delete(String bid) throws SQLException {
		String sql1 = "delete from t_book where bid=?";
		qr.update(sql1,bid);
	}
}
