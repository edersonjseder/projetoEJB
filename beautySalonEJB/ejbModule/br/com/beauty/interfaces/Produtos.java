package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.Remote;

import br.com.beauty.pojo.ProdutosPOJO;

@Remote
public interface Produtos {
	public void insert(ProdutosPOJO produtos);
	public ProdutosPOJO retrieve(Integer identity);
	public void update(ProdutosPOJO produtos);
	public void deleteById(Integer id);
	public void delete(ProdutosPOJO produtos);
	public List<ProdutosPOJO> listar();
	
}
