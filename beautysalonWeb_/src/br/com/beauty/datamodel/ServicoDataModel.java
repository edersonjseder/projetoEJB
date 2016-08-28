package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ServicoDataModel extends ListDataModel<ServicoModel> implements SelectableDataModel<ServicoModel> {

	public ServicoDataModel(){
		
	}
	
	public ServicoDataModel(List<ServicoModel> produtos){
		super(produtos);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ServicoModel getRowData(String rowKey) {
		List<ServicoModel> servicos = (List<ServicoModel>) getWrappedData();
		
		for (ServicoModel servico : servicos) {
			if(servico.getId().equals(Integer.parseInt(rowKey)))
				return servico;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(ServicoModel servico) {
		return servico.getNome();
	}

}
