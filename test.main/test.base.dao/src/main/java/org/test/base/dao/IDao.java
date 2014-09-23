package org.test.base.dao;

import java.util.List;

/**
 * Hello world!
 *
 */
public interface IDao<T> 
{
    public void save(T t);
    
    public void update(T t);
    
    public void delete(T t);
    
    public void saveOrUpdate(T t);
    
    public List<?> find(String hql, Object... params);
    
    public List<T> find(String hql, Object[] params, Integer page, Integer rows);
    
    public T get(Class<?> class1, String id);
    
    public T get(String hql, Object[] params);
    
    public Long count(String hql, Object... params);
    
    public Integer updateByQuery(String hql,Object... params);
    
    public List<T> findAll(Class<?> cls);
    
}
