package br.com.beauty.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class AgendamentosDataModel extends ListDataModel<AgendamentoModel> implements SelectableDataModel<AgendamentoModel> {

	public AgendamentosDataModel(){
		
	}
	
	public AgendamentosDataModel(List<AgendamentoModel> agendamento){
		super(agendamento);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AgendamentoModel getRowData(String rowKey) {
		List<AgendamentoModel> agendamentos = (List<AgendamentoModel>) getWrappedData();
		
		for (AgendamentoModel agendamentoModel : agendamentos) {
			if(agendamentoModel.getId().equals(Integer.parseInt(rowKey)))
				return agendamentoModel;
		}
		
		return null;
	}

	@Override
	public Object getRowKey(AgendamentoModel agendamento) {
		return agendamento.getDataAgendamento();
	}

}
