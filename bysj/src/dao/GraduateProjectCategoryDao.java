package dao;


import domain.GraduateProjectCategory;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public class GraduateProjectCategoryDao {
	//创建私有的静态的GraduateProjectCategoryDao对象
	private static GraduateProjectCategoryDao graduateProjectCategoryDao = new GraduateProjectCategoryDao();
	//创建私有的静态的集合graduateProjectCategorys
	private static Collection<GraduateProjectCategory> graduateProjectCategorys = new HashSet<GraduateProjectCategory>();
	//定义私有的构造器
	private GraduateProjectCategoryDao(){}
	//定义getInstance()方法,返回graduateProjectCategoryDao指向对象
	public static GraduateProjectCategoryDao getInstance(){
		return graduateProjectCategoryDao;
	}
	//定义findAll()方法,返回graduateProjectCategorys集合
	public static Collection<GraduateProjectCategory> findAll() throws SQLException {
		graduateProjectCategorys.clear();
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建语句盒子对象
		Statement stmt = connection.createStatement();
		//执行SQL查询语句并获得结果集对象
		ResultSet resultSet = stmt.executeQuery("select * from ProjectCategory");
		while (resultSet.next()){
			GraduateProjectCategory graduateProjectCategory = new GraduateProjectCategory(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
			graduateProjectCategorys.add(graduateProjectCategory);
		}
		return graduateProjectCategorys;
	}
	//定义find()方法
	public GraduateProjectCategory find(Integer id) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String graduateProjectCategory_sql = "SELECT * FROM ProjectCategory where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(graduateProjectCategory_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		GraduateProjectCategory graduateProjectCategory = null;
		if (resultSet.next()) {
			graduateProjectCategory = new GraduateProjectCategory(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"));
		}
		JdbcHelper.close(pstmt,connection);
		return graduateProjectCategory;
	}
	public GraduateProjectCategory findByDescription(String description) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String graduateProjectCategory_sql = "SELECT * FROM ProjectCategory where description = ?";
		PreparedStatement pstmt = connection.prepareStatement(graduateProjectCategory_sql);
		pstmt.setString(1,description);
		ResultSet resultSet = pstmt.executeQuery();
		GraduateProjectCategory graduateProjectCategory = null;
		if (resultSet.next()) {
			graduateProjectCategory = new GraduateProjectCategory(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"));
		}
		JdbcHelper.close(pstmt,connection);
		return graduateProjectCategory;
	}
	//通过向集合中添加graduateProjectCategory，实现添加功能
	public boolean add(GraduateProjectCategory graduateProjectCategory) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String addGraduateProjectCategory_sql="Insert into ProjectCategory (description,no,remarks) values (?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement pstmt =connection.prepareStatement(addGraduateProjectCategory_sql);
		//为预编译参数赋值
		pstmt.setString(1, graduateProjectCategory.getDescription());
		pstmt.setString(2, graduateProjectCategory.getNo());
		pstmt.setString(3, graduateProjectCategory.getRemarks());
		graduateProjectCategorys.add(graduateProjectCategory);
		//执行预编译对象的excuteUpdate方法，获取添加的记录行数
		int affectedRowNum=pstmt.executeUpdate();
		//显示添加的记录的行数
		System.out.println("添加了"+affectedRowNum+"条记录");
		JdbcHelper.close(pstmt,connection);
		return affectedRowNum > 0;
	}
	//定义delete()方法
	public boolean delete(GraduateProjectCategory graduateProjectCategory) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String deleteGraduateProjectCategory_sql="Delete from ProjectCategory where id=? ";
		//在该连接上创建预编译语句对象
		PreparedStatement pstmt =connection.prepareStatement(deleteGraduateProjectCategory_sql);
		//为预编译参数赋值
		pstmt.setInt(1,graduateProjectCategory.getId());
		//执行预编译对象的excuteUpdate方法
		int affectedRowNum=pstmt.executeUpdate();
		//显示删除的记录的行数
		System.out.println("删除了"+affectedRowNum+"条记录");
		JdbcHelper.close(pstmt,connection);
		return true ;
	}
	public boolean update(GraduateProjectCategory graduateProjectCategory) throws ClassNotFoundException,SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateGraduateProjectCategory_sql = " update ProjectCategory set description=?,no=?,remarks=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateGraduateProjectCategory_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,graduateProjectCategory.getDescription());
		preparedStatement.setString(2,graduateProjectCategory.getNo());
		preparedStatement.setString(3,graduateProjectCategory.getRemarks());
		preparedStatement.setInt(4,graduateProjectCategory.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("更新了" + affectedRows + "条记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}
}
