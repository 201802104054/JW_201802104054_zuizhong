package domain;

import java.io.Serializable;

public class GraduateProjectCategory implements
		Comparable<GraduateProjectCategory>, Serializable {
	//声明Integer类型私有字段id
	private Integer id;
	//声明String类型私有字段description
	private String description;
	//声明String类型私有字段no
	private String no;
	//声明String类型私有字段remarks
	private String remarks;

	//定义GraduateProjectCategory构造器，传入形参，完成四个字段的对象指向
	public GraduateProjectCategory(Integer id, String description, String no, String remarks) {
		this(description, no, remarks);
		this.id = id;
	}
	//定义GraduateProjectCategory构造器，传入形参，完成五个字段的对象指向
	public GraduateProjectCategory(String description, String no, String remarks) {
		this.description = description;
		this.no = no;
		this.remarks = remarks;
	}

	//定义getId()方法返回id指向的对象
	public Integer getId() {
		return id;
	}
	//定义getDescription()方法返回description的字符串
	public String getDescription() {
		return description;
	}
	//定义getNo()方法返回no的字符串
	public String getNo() {
		return no;
	}
	//定义getRemarks()方法返回remarks的字符串
	public String getRemarks() {
		return remarks;
	}
	//定义setId()方法设置变量id所指向的对象
	public void setId(Integer id) {
		this.id = id;
	}
	//定义setDescription()方法，设置字段description的值
	public void setDescription(String description) {
		this.description = description;
	}
	//定义setNo()方法，设置字段no的值
	public void setNo(String no) {
		this.no = no;
	}
	//定义setRemarks()方法，设置字段remarks的值
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	//重写compareTo()方法
	public int compareTo(GraduateProjectCategory o) {
		return this.id - o.getId();
	}
}
