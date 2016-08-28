package br.com.beauty.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import br.com.beauty.dao.DAO;

public abstract class DAOImpl<T> implements DAO<T> {
	
	static EntityManagerFactory factory = null;
	private Class<T> classe;
	
	static {
		factory = Persistence.createEntityManagerFactory("beautydb");
	}
	
	@SuppressWarnings("unchecked")
	public DAOImpl(){
		Class<?> thisClass = getClass();

		ParameterizedType t =
			(ParameterizedType) thisClass.getGenericSuperclass();
		Type t2 = t.getActualTypeArguments()[0];
		
		this.classe = (Class<T>) t2;
	}
	
	public void save(T entity) {
		EntityManager em = getEntityManager();
		em.merge(entity);
		em.close();
	}
	
	public T update(T entity) {
		EntityManager em = getEntityManager();
		T ent = em.merge(entity);
		em.close();
		return ent;
	}
	
	public void removeById(Integer id) {
		EntityManager em = getEntityManager();
		T object = em.find(classe, id);
		em.remove(object);
		
		em.close();
		
	}
	
	public void remove(T entity) {
		EntityManager em = getEntityManager();
		em.remove(entity);
		em.close();
		
	}
	
	public T retrieveById(Integer identity) {
		try {
			EntityManager em = getEntityManager();
			T ent = em.find(classe, identity);
			em.close();
			
			return ent;
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listar() {
		EntityManager em = getEntityManager();
		List<T> lEnt = em.createQuery("FROM " + classe.getSimpleName()).getResultList();
		em.close();
		
		return lEnt;
	}
	
	public EntityManager getEntityManager() {
		EntityManager em = factory.createEntityManager();
		return em;
	}
}
