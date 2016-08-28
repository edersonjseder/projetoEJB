package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class HistoricoFinanceiroDataModel extends ListDataModel<HistoricoFinanceiroModel> implements SelectableDataModel<HistoricoFinanceiroModel> {

	public HistoricoFinanceiroDataModel(){
		
	}
	
	public HistoricoFinanceiroDataModel(List<HistoricoFinanceiroModel> historicoFinanceiro){
		super(historicoFinanceiro);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HistoricoFinanceiroModel getRowData(String rowKey) {
		List<HistoricoFinanceiroModel> historicoFinanceiro = (List<HistoricoFinanceiroModel>) getWrappedData();
		
		for (HistoricoFinanceiroModel historicoFinanceiroPOJO : historicoFinanceiro) {
			if(historicoFinanceiroPOJO.getId().equals(Integer.parseInt(rowKey)))
				return historicoFinanceiroPOJO;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(HistoricoFinanceiroModel historicoFinanceiro) {
		return historicoFinanceiro.getId();
	}

}
