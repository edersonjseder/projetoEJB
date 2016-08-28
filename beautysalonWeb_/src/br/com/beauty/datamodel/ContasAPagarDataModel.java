package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ContasAPagarDataModel extends ListDataModel<ContasAPagarModel> implements SelectableDataModel<ContasAPagarModel> {

	public ContasAPagarDataModel(){
		
	}
	
	public ContasAPagarDataModel(List<ContasAPagarModel> contasPagar){
		super(contasPagar);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ContasAPagarModel getRowData(String rowKey) {
		List<ContasAPagarModel> contasPagar = (List<ContasAPagarModel>) getWrappedData();
		
		for (ContasAPagarModel contasPagarModel : contasPagar) {
			if(contasPagarModel.getId().equals(Integer.parseInt(rowKey)))
				return contasPagarModel;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(ContasAPagarModel contasPagar) {
		return contasPagar.getDataPagamento();
	}

}
