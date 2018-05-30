package cn.lxa.PageBean;

/*
 * 表达式封装体
 */
public class Expression {
	private String name ;
	private String operator ;
	private String value ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
