package domain;

import java.io.Serializable;

public class GraduateProject implements
		Comparable<GraduateProject>, Serializable {
	//声明Integer类型私有字段id
	private Integer id;
	//声明String类型私有字段title
	private String title;
	//声明GraduateProjectType类型的私有字段graduateProjectType
	private GraduateProjectType graduateProjectType;
	//声明GraduateProjectCategory类型的私有字段graduateProjectCategory
	private GraduateProjectCategory graduateProjectCategory;
	//声明Teacher类型的私有字段teacher
	private Teacher teacher;
	//定义GraduateProject构造器，传入形参，完成五个字段的对象指向
	public GraduateProject(Integer id, String title, GraduateProjectType graduateProjectType, GraduateProjectCategory graduateProjectCategory, Teacher teacher) {
		this(title,graduateProjectType,graduateProjectCategory,teacher);
		this.id = id;
	}
	//定义GraduateProject构造器，传入形参，完成四个字段的对象指向
	public GraduateProject(String title, GraduateProjectType graduateProjectType, GraduateProjectCategory graduateProjectCategory, Teacher teacher) {
		this.title = title;
		this.graduateProjectType = graduateProjectType;
		this.graduateProjectCategory = graduateProjectCategory;
		this.teacher = teacher;
	}
	//定义setId()方法设置变量id所指向的对象
	public void setId(Integer id) {
		this.id = id;
	}
	//定义setTitle()方法，设置字段title的值
	public void setTitle(String title) {
		this.title = title;
	}
	//定义setGraduateProjectType()方法为graduateProjectType设置指向的对象
	public void setGraduateProjectType(GraduateProjectType graduateProjectType) {
		this.graduateProjectType = graduateProjectType;
	}
	//定义setGraduateProjectCategory()方法为graduateProjectCategory设置指向的对象
	public void setGraduateProjectCategory(GraduateProjectCategory graduateProjectCategory) {
		this.graduateProjectCategory = graduateProjectCategory;
	}

	//定义getId()方法返回id指向的对象
	public Integer getId() {
		return id;
	}
	//定义getTitle()方法返回title的字符串
	public String getTitle() {
		return title;
	}
	//定义getGraduateProjectType()方法返回graduateProjectType指向的对象
	public GraduateProjectType getGraduateProjectType() {
		return graduateProjectType;
	}
	//定义getGraduateProjectCategory()方法返回graduateProjectCategory指向的对象
	public GraduateProjectCategory getGraduateProjectCategory() {
		return graduateProjectCategory;
	}
	//定义setTeacher()的方法，为teacher设置指向的对象
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	//定义getTeacher()方法返回teacher指向的对象
	public Teacher getTeacher() {
		return teacher;
	}

	@Override
	//重写compareTo()方法
	public int compareTo(GraduateProject o) {
		return this.id - o.getId();
	}
}
