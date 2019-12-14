package service;


import dao.GraduateProjectTypeDao;
import domain.GraduateProjectType;

import java.sql.SQLException;
import java.util.Collection;

public final class GraduateProjectTypeService {
	private static GraduateProjectTypeDao graduateProjectTypeDao= GraduateProjectTypeDao.getInstance();
	private static GraduateProjectTypeService graduateProjectTypeService=new GraduateProjectTypeService();
	private GraduateProjectTypeService(){}

	public static GraduateProjectTypeService getInstance(){
		return graduateProjectTypeService;
	}

	public GraduateProjectType find(Integer id) throws SQLException {
		return graduateProjectTypeDao.find(id);
	}

	public boolean update(GraduateProjectType graduateProjectType) throws SQLException, ClassNotFoundException {
		return graduateProjectTypeDao.update(graduateProjectType);
	}

	public boolean add(GraduateProjectType graduateProjectType) throws SQLException {
		return graduateProjectTypeDao.add(graduateProjectType);
	}

	public boolean delete(Integer id) throws SQLException {
		GraduateProjectType graduateProjectType = this.find(id);
		return graduateProjectTypeDao.delete(graduateProjectType);
	}

	public boolean delete(GraduateProjectType graduateProjectType) throws SQLException {
		return graduateProjectTypeDao.delete(graduateProjectType);
	}
	public Collection<GraduateProjectType> findAll() throws SQLException {
		return GraduateProjectTypeDao.findAll();
	}
	public  GraduateProjectType findByDescription(String description) throws SQLException {
		return GraduateProjectTypeDao.getInstance().findByDescription(description);
	}
}

