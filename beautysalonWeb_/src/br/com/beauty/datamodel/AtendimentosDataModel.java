package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class AtendimentosDataModel extends ListDataModel<AtendimentoModel> implements SelectableDataModel<AtendimentoModel> {

	public AtendimentosDataModel(){
		
	}
	
	public AtendimentosDataModel(List<AtendimentoModel> atendimento){
		super(atendimento);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AtendimentoModel getRowData(String rowKey) {
		List<AtendimentoModel> agendamentos = (List<AtendimentoModel>) getWrappedData();
		
		for (AtendimentoModel atendimentoModel : agendamentos) {
			if(atendimentoModel.getId().equals(Integer.parseInt(rowKey)))
				return atendimentoModel;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(AtendimentoModel atendimento) {
		return atendimento.getDataAtendimento();
	}

}
