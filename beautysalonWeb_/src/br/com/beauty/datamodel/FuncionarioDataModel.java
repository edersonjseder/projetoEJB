package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class FuncionarioDataModel extends ListDataModel<FuncionarioModel> implements SelectableDataModel<FuncionarioModel> {

	public FuncionarioDataModel(){
		
	}
	
	public FuncionarioDataModel(List<FuncionarioModel> funcionario){
		super(funcionario);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FuncionarioModel getRowData(String rowKey) {
		List<FuncionarioModel> funcionarios = (List<FuncionarioModel>) getWrappedData();
		
		for (FuncionarioModel funcionarioModel : funcionarios) {
			if(funcionarioModel.getId().equals(Integer.parseInt(rowKey)))
				return funcionarioModel;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(FuncionarioModel funcionario) {
		return funcionario.getCpf();
	}

}
