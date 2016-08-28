package br.com.beauty.dao;

import java.util.List;

public interface DAO<T> {
	public void save(T entity);
	public T update(T entity);
	public T retrieveById(Integer identity);
	public void removeById(Integer id);
	public void remove(T entity);
	public List<T> listar();
}
