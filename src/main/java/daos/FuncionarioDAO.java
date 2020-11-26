package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daos.interfaces.IFuncionarioDAO;
import entitys.Funcionario;
import exceptions.InsertFalhouException;
import exceptions.RgExistenteException;
import exceptions.SelectFalhouException;
import utils.Alerts;
import utils.ConexaoMySql;
import utils.enums.EnumFuncionario;
import utils.enums.EnumPessoa;

public class FuncionarioDAO implements IFuncionarioDAO {

	Connection conexao;

	public FuncionarioDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}

	public int inserir(Funcionario obj) {
		String sqlPessoa = String.format("INSERT INTO %s ( %s, %s, %s , %s) VALUES (?, ?, ?, ?)", EnumPessoa.pessoa,
				EnumPessoa.nome, EnumPessoa.documento, EnumPessoa.telefone, EnumPessoa.senha);

		String sqlPessoaIns = String.format("SELECT * FROM %s ORDER BY cod DESC LIMIT 1", EnumPessoa.pessoa);

		String sqlFuncionario = String.format("INSERT INTO %s ( %s, %s, %s) VALUES (?, ?, ?)", EnumFuncionario.funcionario,
				EnumFuncionario.pessoa_cod, EnumFuncionario.cargo, EnumFuncionario.acesso);

		if (verificaRG(0, obj.getDocumento()))
			throw new RgExistenteException("Este documento ja existe no banco de dados");

		try (PreparedStatement statementPessoa = conexao.prepareStatement(sqlPessoa);
				PreparedStatement statementPessoaIns = conexao.prepareStatement(sqlPessoaIns);
				PreparedStatement statementFuncionario = conexao.prepareStatement(sqlFuncionario);) {

			statementPessoa.setString(1, obj.getNome());
			statementPessoa.setString(2, obj.getDocumento().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(3, obj.getTelefone().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(4, obj.getSenha());

			if (statementPessoa.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir a Pessoa");

			ResultSet resultSet = statementPessoaIns.executeQuery();
			resultSet.next();

			statementFuncionario.setInt(1, resultSet.getInt(EnumPessoa.cod.name()));
			statementFuncionario.setString(2, obj.getCargo());
			statementFuncionario.setInt(3, obj.getAcesso());

			if (statementFuncionario.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir o Funcionario");

			return 1;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	public int editar(Funcionario obj) {
		String sqlPessoa = String.format("update %s set %s =?, %s =?, %s =?, %s =? where %s =?", EnumPessoa.pessoa,
				EnumPessoa.nome, EnumPessoa.documento, EnumPessoa.telefone, EnumPessoa.senha, EnumPessoa.cod);
		
		if(obj.getSenha().equals("")) {
			sqlPessoa = String.format("update %s set %s =?, %s =?, %s =? where %s =?", EnumPessoa.pessoa,
					EnumPessoa.nome, EnumPessoa.documento, EnumPessoa.telefone, EnumPessoa.cod);
		}

		String sqlFuncionario = String.format("update %s set %s =?, %s =? where %s =?", EnumFuncionario.funcionario, EnumFuncionario.cargo, EnumFuncionario.acesso,
				EnumFuncionario.pessoa_cod);

		if (verificaRG(obj.getCod(), obj.getDocumento()))
			throw new RgExistenteException("Este documento ja existe no banco de dados");

		try (PreparedStatement statementPessoa = conexao.prepareStatement(sqlPessoa);
				PreparedStatement statementFuncionario = conexao.prepareStatement(sqlFuncionario);) {

			statementPessoa.setString(1, obj.getNome());
			statementPessoa.setString(2, obj.getDocumento().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(3, obj.getTelefone().replaceAll("[^0-9]+", ""));
			if(obj.getSenha().equals("")) {
				statementPessoa.setInt(4, obj.getCod());
			} else {
				statementPessoa.setString(4, obj.getSenha());
				statementPessoa.setInt(5, obj.getCod());
			}

			statementFuncionario.setString(1, obj.getCargo());
			statementFuncionario.setInt(2, obj.getAcesso());
			statementFuncionario.setInt(3, obj.getCod());

			if (statementFuncionario.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir o Funcionario");

			if (statementPessoa.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir a Funcionario");

			return 1;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	public int deletar(Integer cod) {
		String sql = String.format("update %s set %s ='N' where %s = ?", EnumPessoa.pessoa, EnumPessoa.ativo,
				EnumPessoa.cod);

		try (PreparedStatement statement = conexao.prepareStatement(sql);) {

			statement.setInt(1, cod);

			if (statement.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir a Funcionario");

			return 1;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	public boolean verificaRG(int cod, String rg) {

		String sql = String.format("SELECT * FROM %s WHERE %s = ?", EnumPessoa.pessoa, EnumPessoa.documento,
				(cod != 0 ? " and cod=?" : ""));

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			statement.setString(1, rg);
			if (cod != 0)
				statement.setInt(2, cod);

			ResultSet resultSet = statement.executeQuery();
			return resultSet.next();

		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public List<Funcionario> listar() {
		List<Funcionario> lista = new ArrayList<Funcionario>();

		String sql = String.format("SELECT * FROM %s p inner join %s f on p.%s=f.%s", EnumPessoa.pessoa,
				EnumFuncionario.funcionario, EnumPessoa.cod, EnumFuncionario.pessoa_cod);

		try (PreparedStatement statement = conexao.prepareStatement(sql);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString(EnumPessoa.ativo.name()).equals("S")) {
					lista.add(new Funcionario(resultSet.getString(EnumFuncionario.cargo.name()),
							resultSet.getInt(EnumFuncionario.acesso.name()),
							resultSet.getInt(EnumPessoa.cod.name()),
							resultSet.getString(EnumPessoa.nome.name()),
							resultSet.getString(EnumPessoa.documento.name()),
							resultSet.getNString(EnumPessoa.telefone.name()),
							resultSet.getString(EnumPessoa.senha.name())));
				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return lista;
	}

	public Boolean verificarFuncionario(String doc, String senha) {
		String sql = String.format("Select * from %s where %s=? and %s=?", EnumPessoa.pessoa, EnumPessoa.documento,
				EnumPessoa.senha);
		try (PreparedStatement statement = conexao.prepareStatement(sql);) {

			statement.setString(1, doc);
			statement.setString(2, senha);

			ResultSet resultSet = statement.executeQuery();

			return resultSet.next();

		} catch (Exception e) {
			Alerts.alertErro("Erro", "Erro ao tentar verificar Funcionario cadastrado", e.getMessage()).showAndWait();
		}
		return false;
	}

	public Funcionario buscar(Integer cod) {
		Funcionario Funcionario = new Funcionario();
		String sql = String.format("SELECT * FROM %s p inner join %s f on p.%s=f.%s WHERE p.%s=?", EnumPessoa.pessoa,
				EnumFuncionario.funcionario, EnumPessoa.cod, EnumFuncionario.pessoa_cod);

		try (PreparedStatement statement = conexao.prepareStatement(sql);) {
			statement.setInt(1, cod);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				if (resultSet.getString(EnumPessoa.ativo.name()).equals("S")) {
					Funcionario = new Funcionario(resultSet.getString(EnumFuncionario.cargo.name()),
							resultSet.getInt(EnumFuncionario.acesso.name()),
							resultSet.getInt(EnumPessoa.cod.name()),
							resultSet.getString(EnumPessoa.nome.name()),
							resultSet.getString(EnumPessoa.documento.name()),
							resultSet.getNString(EnumPessoa.telefone.name()),
							resultSet.getString(EnumPessoa.senha.name()));
				}
				return Funcionario;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		throw new SelectFalhouException("Erro ao buscar o Funcionario");
	}
}
