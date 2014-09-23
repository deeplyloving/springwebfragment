package org.test.base.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class CURDDaoImpl<T extends Serializable> implements CURDDao<T>{
	
	public Class<?> getGenericClass(){
		Class<?> entityClass = (Class <?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];   
		return entityClass;
	}
	
	@Autowired
	public IDao<T> dao;
	
	@Override
	public void save(T t) {
		dao.save(t);
	}

	@Override
	public void delete(T t) {
		dao.delete(t);
	}

	@Override
	public void update(T t) {
		dao.update(t);
	}

	@Override
	public T get(String id) {
		return (T) dao.get(getGenericClass(), id);
	}


	@Override
	public void saveOrUpdate(T t) {
		dao.saveOrUpdate(t);
	}

	@Override
	public List<T> list() {
		return dao.findAll(getGenericClass());
	}

}
