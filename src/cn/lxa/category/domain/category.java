package cn.lxa.category.domain;

import java.util.List;

//分类的实体类
public class category {
	private String cid ;
	private String cname ;
	private String desc ;
	private int orderBy ;
	private List<category> children ;
	private category parent ;
	
	public List<category> getChildren() {
		return children;
	}
	public void setChildren(List<category> children) {
		this.children = children;
	}
	public category getParent() {
		return parent;
	}
	public void setParent(category parent) {
		this.parent = parent;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	
}
