package br.com.beauty.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public abstract class GenericDAO<T> {
	
	private Session session;
	private Class<T> entityClass;
	
	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public void insert(T entity){
		try{
			session.save(entity);
			
		}catch(HibernateException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public void delete(T entity){
		try{
			session.delete(entity);
			
		}catch(HibernateException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T retrieve(Integer identity){
		try{
			return (T) session.get(entityClass, identity);
			
		}catch(HibernateException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public void update(T entity){
		try{
			session.update(entity);
			
		}catch(HibernateException e){
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> listar(){
		
		List<T> list = null;
		
		try{
			Criteria selectAll = session.createCriteria(entityClass);   
			list = selectAll.list();
			
		}catch(HibernateException e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
}
