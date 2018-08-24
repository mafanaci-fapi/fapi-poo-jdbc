package br.edu.fapi.poo.jdbc.dao;

import java.util.List;

import br.edu.fapi.poo.jdbc.model.Funcionario;

public interface FuncionarioDAO {

	int cadastrarFuncionario(Funcionario funcionario);
	
	Funcionario pesquisarFuncionario(String cpf);
	
	List<Funcionario> listarFuncionarios(String nome);
	
	boolean atualizarFuncionario(Funcionario funcionario);
	
	boolean apagarFuncionario(String nome);	
	
}
