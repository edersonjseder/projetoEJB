package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ProdutoDataModel extends ListDataModel<ProdutoModel> implements SelectableDataModel<ProdutoModel> {

	public ProdutoDataModel(){
		
	}
	
	public ProdutoDataModel(List<ProdutoModel> produtos){
		super(produtos);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProdutoModel getRowData(String rowKey) {
		List<ProdutoModel> produtos = (List<ProdutoModel>) getWrappedData();
		
		for (ProdutoModel produtosPOJO : produtos) {
			if(produtosPOJO.getId().equals(Integer.parseInt(rowKey)))
				return produtosPOJO;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(ProdutoModel produto) {
		return produto.getMarca();
	}

}
