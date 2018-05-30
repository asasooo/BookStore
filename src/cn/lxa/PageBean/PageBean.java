package cn.lxa.PageBean;

import java.util.List;

public class PageBean<T> {
	private int tr ; //总记录数
	private int pc ; //当前页码
	private int ps ; //每页记录数
	private int tp ; //总页面数
	private String url ; //请求路径
	private List<T> beanList ; //商品数据
	public void setTp(){
		 int tpnumm = tr/ps;
		 tp = (tr%ps == 0 ?tpnumm:tpnumm+1) ; 
	}
	public int getTp(){
		return tp ;
	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
}
