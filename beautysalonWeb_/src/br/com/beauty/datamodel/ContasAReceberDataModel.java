package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ContasAReceberDataModel extends ListDataModel<ContasAReceberModel> implements SelectableDataModel<ContasAReceberModel> {

	public ContasAReceberDataModel(){
		
	}
	
	public ContasAReceberDataModel(List<ContasAReceberModel> contasPagar){
		super(contasPagar);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ContasAReceberModel getRowData(String rowKey) {
		List<ContasAReceberModel> contasReceber = (List<ContasAReceberModel>) getWrappedData();
		
		for (ContasAReceberModel contasReceberModel : contasReceber) {
			if(contasReceberModel.getId().equals(Integer.parseInt(rowKey)))
				return contasReceberModel;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(ContasAReceberModel contasReceber) {
		return contasReceber.getDataRecebimento();
	}

}
