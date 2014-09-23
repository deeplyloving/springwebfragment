package org.test.base.dao;

import java.io.Serializable;
import java.util.List;

public interface CURDDao<T extends Serializable> {
	public void save(T t);
	public void delete(T t);
	public void update(T t);
	public T get(String id);
	public void saveOrUpdate(T t);
	public List<T> list();
}
