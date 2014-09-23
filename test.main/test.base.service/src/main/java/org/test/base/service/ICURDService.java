package org.test.base.service;

import java.io.Serializable;
import java.util.List;

public interface ICURDService<T extends Serializable>{
	public void save(T t);
	public void delete(T t);
	public void update(T t);
	public T get(String id);
	public void saveOrUpdate(T t);
	public List<T> list();
}
