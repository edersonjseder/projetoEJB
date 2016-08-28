package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class EstoqueDataModel extends ListDataModel<EstoqueModel> implements SelectableDataModel<EstoqueModel> {

	public EstoqueDataModel(){
		
	}
	
	public EstoqueDataModel(List<EstoqueModel> estoque){
		super(estoque);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EstoqueModel getRowData(String rowKey) {
		List<EstoqueModel> estoques = (List<EstoqueModel>) getWrappedData();
		
		for (EstoqueModel estoqueModel : estoques) {
			if(estoqueModel.getId().equals(Integer.parseInt(rowKey)))
				return estoqueModel;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(EstoqueModel estoque) {
		return estoque.getValorTotalEstoque();
	}

}
