package service;

import dao.GraduateProjectCategoryDao;
import domain.GraduateProjectCategory;

import java.sql.SQLException;
import java.util.Collection;

public final class GraduateProjectCategoryService {
	private static GraduateProjectCategoryDao graduateProjectCategoryDao= GraduateProjectCategoryDao.getInstance();
	private static GraduateProjectCategoryService graduateProjectCategoryService=new GraduateProjectCategoryService();
	private GraduateProjectCategoryService(){}

	public static GraduateProjectCategoryService getInstance(){
		return graduateProjectCategoryService;
	}

	public GraduateProjectCategory find(Integer id) throws SQLException {
		return graduateProjectCategoryDao.find(id);
	}

	public boolean update(GraduateProjectCategory graduateProjectCategory) throws SQLException, ClassNotFoundException {
		return graduateProjectCategoryDao.update(graduateProjectCategory);
	}

	public boolean add(GraduateProjectCategory graduateProjectCategory) throws SQLException {
		return graduateProjectCategoryDao.add(graduateProjectCategory);
	}

	public boolean delete(Integer id) throws SQLException {
		GraduateProjectCategory graduateProjectCategory = this.find(id);
		return graduateProjectCategoryDao.delete(graduateProjectCategory);
	}

	public boolean delete(GraduateProjectCategory graduateProjectCategory) throws SQLException {
		return graduateProjectCategoryDao.delete(graduateProjectCategory);
	}
	public Collection<GraduateProjectCategory> findAll() throws SQLException {
		return GraduateProjectCategoryDao.findAll();
	}
	public  GraduateProjectCategory findByDescription(String description) throws SQLException {
		return GraduateProjectCategoryDao.getInstance().findByDescription(description);
	}
}

