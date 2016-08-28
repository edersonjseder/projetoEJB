package br.com.beauty.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.CaixaDAO;
import br.com.beauty.impl.CaixaDAOImpl;
import br.com.beauty.interfaces.Caixa;
import br.com.beauty.pojo.CaixaPOJO;
import br.com.beauty.util.StringUtils;

@Stateful
public class CaixaBean implements Caixa {
	
	private CaixaDAO caixaDAO = new CaixaDAOImpl();

	@Override
	public void insert(CaixaPOJO caixa) {
		
		StringUtils utils = new StringUtils();
		
		String dataMes = utils.dateToString(caixa.getDataLancamento());
		
		caixa.setMes(Integer.parseInt(dataMes.substring(3, 5)));
		caixa.setDataCaixaMensal(caixa.getDataLancamento());
		
		if(caixa.getEspecie().equals("Dinheiro")){
			caixa.setSaldoCaixaDinheiroMes(caixa.getValor());
			caixa.setSaldoAtualCaixaDinheiro(caixa.getValor());
		}else{
			caixa.setSaldoCaixaChequeMes(caixa.getValor());
			caixa.setSaldoAtualCaixaCheque(caixa.getValor());
		}
		
		caixaDAO.save(caixa);
	}

	@Override
	public CaixaPOJO retrieve(Integer identity) {
		return caixaDAO.retrieveById(identity);
	}

	@Override
	public void update(CaixaPOJO caixa) {
		caixaDAO.update(caixa);
	}

	@Override
	public List<CaixaPOJO> listar() {
		return caixaDAO.listar();
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		caixaDAO.removeById(id);
	}

	@Override
	public void delete(CaixaPOJO caixa) throws EJBException {
		caixaDAO.remove(caixa);
	}

	@Override
	public Number buscarSaldoDinheiroPorMes(Integer mes) throws EJBException {
		return caixaDAO.buscarSaldoDinheiroPorMes(mes);
	}

	@Override
	public Number buscarSaldoChequePorMes(Integer mes) throws EJBException {
		return caixaDAO.buscarSaldoChequePorMes(mes);
	}

	@Override
	public Number buscarTotalSaldoCaixaPorData(Date dtInicial, Date dtFinal) throws EJBException {
		return caixaDAO.buscarTotalSaldoCaixaPorData(dtInicial, dtFinal);
	}


}
