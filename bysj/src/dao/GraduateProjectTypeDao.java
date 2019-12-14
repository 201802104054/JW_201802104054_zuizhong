package dao;


import domain.GraduateProjectType;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public class GraduateProjectTypeDao {
	//创建私有的静态的GraduateProjectTypeDao对象
	private static GraduateProjectTypeDao graduateProjectTypeDao = new GraduateProjectTypeDao();
	//创建私有的静态的集合graduateProjectTypes
	private static Collection<GraduateProjectType> graduateProjectTypes = new HashSet<GraduateProjectType>();
	//定义私有的构造器
	private GraduateProjectTypeDao(){}
	//定义getInstance()方法,返回graduateProjectTypeDao指向对象
	public static GraduateProjectTypeDao getInstance(){
		return graduateProjectTypeDao;
	}
	//定义findAll()方法,返回graduateProjectTypes集合
	public static Collection<GraduateProjectType> findAll() throws SQLException {
		graduateProjectTypes.clear();
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建语句盒子对象
		Statement stmt = connection.createStatement();
		//执行SQL查询语句并获得结果集对象
		ResultSet resultSet = stmt.executeQuery("select * from ProjectType");
		while (resultSet.next()){
			GraduateProjectType graduateProjectType = new GraduateProjectType(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
			graduateProjectTypes.add(graduateProjectType);
		}
		return graduateProjectTypes;
	}
	//定义find()方法
	public GraduateProjectType findByDescription(String description) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String graduateProjectType_sql = "SELECT * FROM ProjectType where description = ?";
		PreparedStatement pstmt = connection.prepareStatement(graduateProjectType_sql);
		pstmt.setString(1,description);
		ResultSet resultSet = pstmt.executeQuery();
		GraduateProjectType graduateProjectType = null;
		if (resultSet.next()) {
			graduateProjectType = new GraduateProjectType(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"));
		}
		JdbcHelper.close(pstmt,connection);
		return graduateProjectType;
	}
	//定义find()方法
	public GraduateProjectType find(Integer id) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String graduateProjectType_sql = "SELECT * FROM ProjectType where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(graduateProjectType_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		GraduateProjectType graduateProjectType = null;
		if (resultSet.next()) {
			graduateProjectType = new GraduateProjectType(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"));
		}
		JdbcHelper.close(pstmt,connection);
		return graduateProjectType;
	}
	//通过向集合中添加graduateProjectType，实现添加功能
	public boolean add(GraduateProjectType graduateProjectType) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String addGraduateProjectType_sql="Insert into ProjectType (description,no,remarks) values (?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement pstmt =connection.prepareStatement(addGraduateProjectType_sql);
		//为预编译参数赋值
		pstmt.setString(1, graduateProjectType.getDescription());
		pstmt.setString(2, graduateProjectType.getNo());
		pstmt.setString(3, graduateProjectType.getRemarks());
		graduateProjectTypes.add(graduateProjectType);
		//执行预编译对象的excuteUpdate方法，获取添加的记录行数
		int affectedRowNum=pstmt.executeUpdate();
		//显示添加的记录的行数
		System.out.println("添加了"+affectedRowNum+"条记录");
		JdbcHelper.close(pstmt,connection);
		return affectedRowNum > 0;
	}
	//定义delete()方法
	public boolean delete(GraduateProjectType graduateProjectType) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String deleteGraduateProjectType_sql="Delete from ProjectType where id=? ";
		//在该连接上创建预编译语句对象
		PreparedStatement pstmt =connection.prepareStatement(deleteGraduateProjectType_sql);
		//为预编译参数赋值
		pstmt.setInt(1,graduateProjectType.getId());
		//执行预编译对象的excuteUpdate方法
		int affectedRowNum=pstmt.executeUpdate();
		//显示删除的记录的行数
		System.out.println("删除了"+affectedRowNum+"条记录");
		JdbcHelper.close(pstmt,connection);
		return true ;
	}
	public boolean update(GraduateProjectType graduateProjectType) throws ClassNotFoundException, SQLException {
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateGraduateProjectType_sql = " update ProjectType set description=?,no=?,remarks=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateGraduateProjectType_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,graduateProjectType.getDescription());
		preparedStatement.setString(2,graduateProjectType.getNo());
		preparedStatement.setString(3,graduateProjectType.getRemarks());
		preparedStatement.setInt(4,graduateProjectType.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("更新了" + affectedRows + "条记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}
}
