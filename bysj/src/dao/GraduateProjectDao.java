package dao;


import domain.GraduateProject;
import service.GraduateProjectCategoryService;
import service.GraduateProjectTypeService;
import service.TeacherService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class GraduateProjectDao {
	//创建私有的静态的GraduateProjectDao对象
	private static GraduateProjectDao graduateProjectDao = new GraduateProjectDao();
	//创建私有的静态的集合graduateProjects
	private static Collection<GraduateProject> graduateProjects = new HashSet<>();
	//定义私有的构造器
	private GraduateProjectDao(){}
	//定义getInstance()方法,返回graduateProjectDao指向对象
	public static GraduateProjectDao getInstance(){
		return graduateProjectDao;
	}
	//定义findAll()方法,返回graduateProjects集合
	public Collection<GraduateProject> findAll() throws SQLException{
		graduateProjects.clear();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from project");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()) {
			//以当前记录中的id,description,no,remarks值为参数，创建Teacher对象
			graduateProjects.add(new GraduateProject(
					resultSet.getInt("id"),
					resultSet.getString("title"),
					GraduateProjectTypeService.getInstance().findByDescription(resultSet.getString("ProjectType_description")),
					GraduateProjectCategoryService.getInstance().findByDescription(resultSet.getString("ProjectCategory_description")),
					TeacherService.getInstance().find(resultSet.getInt("teacher_id"))
			));
		}
		//关闭资源
		JdbcHelper.close(resultSet, statement, connection);
		return graduateProjects;
	}
	//定义find()方法
	public GraduateProject find(Integer id) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String graduateProject_sql = "SELECT * FROM Project where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(graduateProject_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		GraduateProject graduateProject = null;
		if (resultSet.next()) {
			graduateProject = new GraduateProject(
					resultSet.getInt("id"),
					resultSet.getString("title"),
					GraduateProjectTypeService.getInstance().findByDescription(resultSet.getString("ProjectType_description")),
					GraduateProjectCategoryService.getInstance().findByDescription(resultSet.getString("ProjectCategory_description")),
					TeacherService.getInstance().find(resultSet.getInt("teacher_id"))
			);
		}
		JdbcHelper.close(pstmt,connection);
		return graduateProject;
	}
	public Collection<GraduateProject> findByTeacher(Integer teacherId) throws SQLException {
		Collection<GraduateProject> graduateProjects = new HashSet<GraduateProject>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		PreparedStatement preparedStatement = connection.prepareStatement("select * from project where teacher_id=?");
		preparedStatement.setInt(1, teacherId);
		ResultSet resultSet = preparedStatement.executeQuery();
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()) {
			//以当前记录中的id,description,no,remarks值为参数，创建Teacher对象
			graduateProjects.add(new GraduateProject(
					resultSet.getInt("id"),
					resultSet.getString("title"),
					GraduateProjectTypeService.getInstance().findByDescription(resultSet.getString("ProjectType_description")),
					GraduateProjectCategoryService.getInstance().findByDescription(resultSet.getString("ProjectCategory_description")),
					TeacherService.getInstance().find(resultSet.getInt("teacher_id"))
			));
		}
		//关闭资源
		JdbcHelper.close(resultSet, preparedStatement, connection);
		return graduateProjects;
	}
	//通过向集合中添加graduateProject，实现添加功能
	public boolean add(GraduateProject graduateProject) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String addGraduateProject_sql="Insert into Project (title,ProjectType_description,ProjectCategory_description,teacher_id) values (?,?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement pstmt =connection.prepareStatement(addGraduateProject_sql);
		//为预编译参数赋值
		pstmt.setString(1, graduateProject.getTitle());
		pstmt.setString(2, graduateProject.getGraduateProjectType().getDescription());
		pstmt.setString(3, graduateProject.getGraduateProjectCategory().getDescription());
		pstmt.setInt(4, graduateProject.getTeacher().getId());
		graduateProjects.add(graduateProject);
		//执行预编译对象的excuteUpdate方法，获取添加的记录行数
		int affectedRowNum=pstmt.executeUpdate();
		//显示添加的记录的行数
		System.out.println("添加了"+affectedRowNum+"条记录");
		//关闭连接
		JdbcHelper.close(pstmt,connection);
		return affectedRowNum > 0;
	}
	public boolean delete(GraduateProject graduateProject) throws  SQLException {
		Connection connection = JdbcHelper.getConn();
		int affectedRowNum = 0;
		//写sql语句
		String deleteGraduateProject_sql = "DELETE FROM Project WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteGraduateProject_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1, graduateProject.getId());
		//执行预编译语句，获取删除记录行数并赋值给affectedRowNum
		affectedRowNum = preparedStatement.executeUpdate();
		//关闭连接
		JdbcHelper.close(preparedStatement,connection);
		return affectedRowNum > 0;
	}
	public boolean update(GraduateProject graduateProject)throws ClassNotFoundException,SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateGraduateProject_sql = " update Project set title=?,ProjectType_description=?,ProjectCategory_description=?,teacher_id=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateGraduateProject_sql);
		//为预编译参数赋值
		preparedStatement.setString(1, graduateProject.getTitle());
		preparedStatement.setString(2, graduateProject.getGraduateProjectType().getDescription());
		preparedStatement.setString(3, graduateProject.getGraduateProjectCategory().getDescription());
		preparedStatement.setInt(4,graduateProject.getTeacher().getId());
		preparedStatement.setInt(5,graduateProject.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("更新了" + affectedRows + "条记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}
}

