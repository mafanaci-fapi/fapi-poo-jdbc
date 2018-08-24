package br.edu.fapi.poo.jdbc.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.edu.fapi.poo.jdbc.dao.FuncionarioDAO;
import br.edu.fapi.poo.jdbc.model.Funcionario;

public class FuncionarioDAOImplTest {

	private FuncionarioDAO funcionarioDAO;
	
	@Before
	public void setUp() throws Exception{
		funcionarioDAO = new FuncionarioDAOImpl();
	}
	
	@Test
	public void testCadastrarFuncionario() throws Exception {
		int resultado = funcionarioDAO.cadastrarFuncionario(criarFuncionario("10987654321", "Marcio", "Advogado", 120000.30f));
		assertEquals(1, resultado);
	}
	
	@Test
	public void testPesquisarFuncionario() throws Exception {
		Funcionario funcionario = funcionarioDAO.pesquisarFuncionario("012345678910");
		assertNotNull(funcionario);
	}

	@Test
	public void listarFuncionariosTest() throws Exception{
		List<Funcionario> funcionarios = funcionarioDAO.listarFuncionarios("Marcio");
		assertNotNull(funcionarios);
	}
	
	@Test
	public void atualizarFuncionarioTest() throws Exception{
		Funcionario funcionario = criarFuncionario("10987654321", "Marcio", "Engenheiro", 500000.00f);
		assertTrue(funcionarioDAO.atualizarFuncionario(funcionario));
	}
	
	@Test
	public void  apagarFuncionarioTest() throws Exception{
		//criarFuncionario("10987654321", "Marcio", "Engenheiro", 500000.00f);
		assertTrue(funcionarioDAO.apagarFuncionario("10987654321"));
	}
	
	private Funcionario criarFuncionario(String cpf, String nome, String profissao, Float salario) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cpf);
		funcionario.setNome(nome);
		funcionario.setProfissao(profissao);
		funcionario.setSalario(salario);
		return funcionario;
	}
}
