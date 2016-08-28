package br.com.beauty.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.CaixaPOJO;

@Remote
public interface Caixa {
	public void insert(CaixaPOJO caixa) throws EJBException;
	public CaixaPOJO retrieve(Integer identity) throws EJBException;
	public Number buscarSaldoDinheiroPorMes(Integer mes) throws EJBException;
	public Number buscarSaldoChequePorMes(Integer mes) throws EJBException;
	public void update(CaixaPOJO caixa) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(CaixaPOJO caixa) throws EJBException;
	public List<CaixaPOJO> listar() throws EJBException;
	public Number buscarTotalSaldoCaixaPorData(Date dtInicial, Date dtFinal) throws EJBException;
	
}
