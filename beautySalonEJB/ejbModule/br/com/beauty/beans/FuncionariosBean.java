package br.com.beauty.beans;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.criptografia.Criptografia;
import br.com.beauty.dao.FuncionarioDAO;
import br.com.beauty.impl.FuncionarioDAOImpl;
import br.com.beauty.interfaces.Funcionarios;
import br.com.beauty.pojo.FuncionarioPOJO;

@Stateful
public class FuncionariosBean implements Funcionarios {

	private FuncionarioDAO funcionarioDAO = new FuncionarioDAOImpl();
	
	
	@Override
	public void insert(FuncionarioPOJO funcionario) {
		
		Criptografia cripto = new Criptografia();
		byte[] senhaCriptografada = cripto.criptografarInformacao(funcionario.getUser().getSenha());
		funcionario.getUser().setSenha(Arrays.toString(senhaCriptografada));
		
		funcionarioDAO.save(funcionario);
	}
	
	@Override
	public FuncionarioPOJO retrieve(Integer identity) {
		return funcionarioDAO.retrieveById(identity);
	}

	@Override
	public void update(FuncionarioPOJO funcionario) {
		
		Criptografia cripto = new Criptografia();
		byte[] senhaCriptografada = cripto.criptografarInformacao(funcionario.getUser().getSenha());
		funcionario.getUser().setSenha(Arrays.toString(senhaCriptografada));
		
		funcionarioDAO.update(funcionario);
	}

	@Override
	public List<FuncionarioPOJO> listar() {
		return funcionarioDAO.listar();
	}

	@Override
	public List<FuncionarioPOJO> buscarFuncionariosPorCargo(String cargo) throws EJBException {
		return funcionarioDAO.buscarFuncionariosPorCargo(cargo);
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		funcionarioDAO.removeById(id);
	}

	@Override
	public void delete(FuncionarioPOJO funcionario) throws EJBException {
		funcionarioDAO.remove(funcionario);
	}

}
