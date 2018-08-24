package br.edu.fapi.poo.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.fapi.poo.jdbc.dao.FuncionarioDAO;
import br.edu.fapi.poo.jdbc.dao.conexao.MySqlConnectionProvider;
import br.edu.fapi.poo.jdbc.model.Funcionario;

public class FuncionarioDAOImpl implements FuncionarioDAO {

	public int cadastrarFuncionario(Funcionario funcionario) {
		try (Connection connection = MySqlConnectionProvider.abrirConexao()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"insert into funcionario(cpf,nome, profissao, salario) values (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, funcionario.getCpf());
			preparedStatement.setString(2, funcionario.getNome());
			preparedStatement.setString(3, funcionario.getProfissao());
			preparedStatement.setFloat(4, funcionario.getSalario());

			// INSERT, UPDATE OU DELETE (executeUpdate())
			// Resultado é um valor int que indica o número de linhas afetadas.
			int resultado = preparedStatement.executeUpdate();
			System.out.println("Registro inserido");
			//// Obtém a pk gerada.
			ResultSet res = preparedStatement.getGeneratedKeys();
			if (res.first()) {
				System.out.println("Código gerado: " + res.getInt(1));
			}
			return resultado;
		} catch (SQLException e) {
			System.out.println("Conexão não estabelecida.");
			System.out.println(e.getMessage());
		}
		return 0;
	}

	@Override
	public Funcionario pesquisarFuncionario(String cpf) {
		try (Connection connection = MySqlConnectionProvider.abrirConexao()) {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from funcionario where cpf = ?",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, cpf);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultSet.getInt("id"));
				funcionario.setNome(resultSet.getString("nome"));
				funcionario.setProfissao(resultSet.getString("profissao"));
				funcionario.setSalario(resultSet.getFloat("salario"));
				return funcionario;
			}
		} catch (SQLException e) {
			System.out.println("Conexão não estabelecida.");
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Funcionario> listarFuncionarios(String nome) {
		List<Funcionario> funcionarios = new ArrayList<>();
		try (Connection connection = MySqlConnectionProvider.abrirConexao()) {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from funcionario where nome = ?", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, nome);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultSet.getInt("id"));
				funcionario.setNome(resultSet.getString("nome"));
				funcionario.setProfissao(resultSet.getString("profissao"));
				funcionario.setSalario(resultSet.getFloat("salario"));
				funcionarios.add(funcionario);
			}

			funcionarios.forEach(funcionario -> System.out.println(funcionario));

			return funcionarios;
		} catch (SQLException e) {
			System.out.println("Conexão não estabelecida.");
			System.out.println(e.getMessage());
		}
		return funcionarios;
	}

	public boolean atualizarFuncionario(Funcionario funcionario) {
		try (Connection connection = MySqlConnectionProvider.abrirConexao()) {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);


			ResultSet resultSet = statement.executeQuery("select * from funcionario where cpf = " + funcionario.getCpf());
			if (resultSet.first()) {
				resultSet.updateString("nome", funcionario.getNome());
				resultSet.updateString("profissao", funcionario.getProfissao());
				resultSet.updateFloat("salario", funcionario.getSalario());
				resultSet.updateRow();
				return resultSet.rowUpdated();
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Conexão não estabelecida.");
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean apagarFuncionario(String cpf) {
		try (Connection connection = MySqlConnectionProvider.abrirConexao()) {
			PreparedStatement preparedStatement = connection.prepareStatement("delete from funcionario where cpf = ?");
			preparedStatement.setString(1, cpf);
			preparedStatement.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.out.println("Conexão não estabelecida.");
			System.out.println(e);
			return false;
		}
	}

}
