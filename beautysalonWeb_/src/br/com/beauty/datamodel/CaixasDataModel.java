package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class CaixasDataModel extends ListDataModel<CaixaModel> implements SelectableDataModel<CaixaModel> {

	public CaixasDataModel(){
		
	}
	
	public CaixasDataModel(List<CaixaModel> caixa){
		super(caixa);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CaixaModel getRowData(String rowKey) {
		List<CaixaModel> caixas = (List<CaixaModel>) getWrappedData();
		
		for (CaixaModel caixaModel : caixas) {
			if(caixaModel.getId().equals(Integer.parseInt(rowKey)))
				return caixaModel;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(CaixaModel caixa) {
		return caixa.getHistorico();
	}

}
