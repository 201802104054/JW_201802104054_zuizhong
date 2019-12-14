package service;

import dao.GraduateProjectDao;
import domain.GraduateProject;

import java.sql.SQLException;
import java.util.Collection;

public final class GraduateProjectService {
	private static GraduateProjectDao graduateProjectDao= GraduateProjectDao.getInstance();
	private static GraduateProjectService graduateProjectService=new GraduateProjectService();
	private GraduateProjectService(){}

	public static GraduateProjectService getInstance(){
		return graduateProjectService;
	}

	public Collection<GraduateProject> findAll() throws SQLException {
		return graduateProjectDao.findAll();
	}

	public GraduateProject find(Integer id) throws SQLException {
		return graduateProjectDao.find(id);
	}

	public boolean update(GraduateProject graduateProject) throws SQLException, ClassNotFoundException {
		return graduateProjectDao.update(graduateProject);
	}

	public boolean add(GraduateProject graduateProject) throws SQLException {
		return graduateProjectDao.add(graduateProject);
	}

	public boolean delete(Integer id) throws SQLException {
		GraduateProject graduateProject = this.find(id);
		return graduateProjectDao.delete(graduateProject);
	}

	public boolean delete(GraduateProject graduateProject) throws SQLException {
		return graduateProjectDao.delete(graduateProject);
	}
	public Collection<GraduateProject> findByTeacher(Integer teacherId) throws SQLException {
		return graduateProjectDao.findByTeacher(teacherId);
	}
}
