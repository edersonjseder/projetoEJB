package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ClienteDataModel extends ListDataModel<ClienteModel> implements SelectableDataModel<ClienteModel> {

	public ClienteDataModel(){
		
	}
	
	public ClienteDataModel(List<ClienteModel> clientes){
		super(clientes);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ClienteModel getRowData(String rowKey) {
		List<ClienteModel> clientes = (List<ClienteModel>) getWrappedData();
		
		for (ClienteModel clientesPOJO : clientes) {
			if(clientesPOJO.getId().equals(Integer.parseInt(rowKey)))
				return clientesPOJO;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(ClienteModel cliente) {
		return cliente.getCpf();
	}

}
