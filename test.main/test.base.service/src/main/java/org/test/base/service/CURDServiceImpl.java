package org.test.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.test.base.dao.CURDDao;

public class CURDServiceImpl<T extends Serializable> extends ServiceImpl implements ICURDService<T>{

	public CURDDao<T> dao;
	
	@Autowired
	public void setDao(CURDDao<T> dao) {
		this.dao = dao;
	}

	public void save(T t) {
		dao.save(t);
	}

	public void delete(T t) {
		dao.delete(t);
	}

	public void update(T t) {
		dao.update(t);
	}

	public T get(String id) {
		return dao.get(id);
	}

	public void saveOrUpdate(T t) {
		dao.saveOrUpdate(t);
	}

	public List<T> list() {
		return dao.list();
	}

}
