package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class FornecedorDataModel extends ListDataModel<FornecedorModel> implements SelectableDataModel<FornecedorModel> {

	public FornecedorDataModel(){
		
	}
	
	public FornecedorDataModel(List<FornecedorModel> fornecedor){
		super(fornecedor);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FornecedorModel getRowData(String rowKey) {
		List<FornecedorModel> fornecedores = (List<FornecedorModel>) getWrappedData();
		
		for (FornecedorModel fornecedorModel : fornecedores) {
			if(fornecedorModel.getId().equals(Integer.parseInt(rowKey)))
				return fornecedorModel;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(FornecedorModel fornecedor) {
		return fornecedor.getCpfOuCnpj();
	}

}
