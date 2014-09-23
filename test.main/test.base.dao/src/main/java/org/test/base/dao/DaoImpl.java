package org.test.base.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DaoImpl<T> implements IDao<T> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T t) {
		getCurrentSession().save(t);
	}
	
	

	@Override
	public void update(T t) {
		getCurrentSession().update(t);
		;
	}

	@Override
	public void delete(T t) {
		this.getCurrentSession().delete(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		this.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public List<?> find(String hql, Object... params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(String hql, Object[] params, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public T get(Class<?> c, String id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	@Override
	public T get(String hql, Object[] params) {
		List<T> l = (List<T>) this.find(hql, params);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Long count(String hql, Object... params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public Integer updateByQuery(String hql, Object... params) {
		Query q = this.getCurrentSession().createQuery(hql);  
        if (params != null && params.length > 0) {  
            for (int i = 0; i < params.length; i++) {  
                q.setParameter(i, params[i]);  
            }  
        }  
        return q.executeUpdate(); 
	}

	@Override
	public List<T> findAll(Class<?> cls) {
		return this.getCurrentSession().createQuery(" from "+cls.getSimpleName()).list();
	}

}
