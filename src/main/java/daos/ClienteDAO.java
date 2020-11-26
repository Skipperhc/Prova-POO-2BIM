package daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daos.interfaces.IClienteDAO;
import entitys.Cliente;
import exceptions.InsertFalhouException;
import exceptions.RgExistenteException;
import exceptions.SelectFalhouException;
import utils.Alerts;
import utils.ConexaoMySql;
import utils.enums.EnumCliente;
import utils.enums.EnumPessoa;

public class ClienteDAO implements IClienteDAO {

	Connection conexao;

	public ClienteDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}

	public int inserir(Cliente obj) {
		String sqlPessoa = String.format("INSERT INTO %s ( %s, %s, %s , %s) VALUES (?, ?, ?, ?)", EnumPessoa.pessoa,
				EnumPessoa.nome, EnumPessoa.documento, EnumPessoa.telefone, EnumPessoa.senha);

		String sqlPessoaIns = String.format("SELECT * FROM %s ORDER BY cod DESC LIMIT 1", EnumPessoa.pessoa);

		String sqlCliente = String.format("INSERT INTO %s ( %s, %s) VALUES (?, ?)", EnumCliente.cliente,
				EnumCliente.pessoa_cod, EnumCliente.acesso);

		if (verificaRG(0, obj.getDocumento()))
			throw new RgExistenteException("Este documento ja existe no banco de dados");

		try (PreparedStatement statementPessoa = conexao.prepareStatement(sqlPessoa);
				PreparedStatement statementPessoaIns = conexao.prepareStatement(sqlPessoaIns);
				PreparedStatement statementCliente = conexao.prepareStatement(sqlCliente);) {

			statementPessoa.setString(1, obj.getNome());
			statementPessoa.setString(2, obj.getDocumento().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(3, obj.getTelefone().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(4, obj.getSenha());

			if (statementPessoa.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir a Pessoa");

			ResultSet resultSet = statementPessoaIns.executeQuery();
			resultSet.next();

			statementCliente.setInt(1, resultSet.getInt(EnumPessoa.cod.name()));
			statementCliente.setInt(2, obj.getAcesso());

			if (statementCliente.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir o Cliente");

			return 1;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	public int editar(Cliente obj) {
		String sqlPessoa = String.format("update %s set %s =?, %s =?, %s =?, %s =? where %s =?", EnumPessoa.pessoa,
				EnumPessoa.nome, EnumPessoa.documento, EnumPessoa.telefone, EnumPessoa.senha, EnumPessoa.cod);

		String sqlCliente = String.format("update %s set %s =? where %s =?", EnumCliente.cliente, EnumCliente.acesso,
				EnumCliente.pessoa_cod);

		if (verificaRG(obj.getCod(), obj.getDocumento()))
			throw new RgExistenteException("Este documento ja existe no banco de dados");
		
		try (PreparedStatement statementPessoa = conexao.prepareStatement(sqlPessoa);
				PreparedStatement statementCliente = conexao.prepareStatement(sqlCliente);) {

			statementPessoa.setString(1, obj.getNome());
			statementPessoa.setString(2, obj.getDocumento().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(3, obj.getTelefone().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(4, obj.getSenha());
			statementPessoa.setInt(5, obj.getCod());

			statementCliente.setInt(1, obj.getAcesso());
			statementCliente.setInt(2, obj.getCod());

			if (statementCliente.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir o Cliente");

			if (statementPessoa.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir o Cliente");

			return 1;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	public int deletar(Integer cod) {
		String sql = String.format("update from %s set %s ='N' where %s = ?", EnumPessoa.pessoa, EnumPessoa.ativo,
				EnumPessoa.cod);

		try (PreparedStatement statement = conexao.prepareStatement(sql);) {

			statement.setInt(1, cod);

			if (statement.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir o Cliente");

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
				(cod != 0?" and cod=?":""));

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			statement.setString(1, rg);
			if(cod != 0) statement.setInt(2, cod);
			
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

	public List<Cliente> listar() {
		List<Cliente> lista = new ArrayList<Cliente>();

		String sql = String.format("SELECT * FROM %s p inner join %s c on p.%s=c.%s", EnumPessoa.pessoa,
				EnumCliente.cliente, EnumPessoa.cod, EnumCliente.pessoa_cod);

		try (PreparedStatement statement = conexao.prepareStatement(sql);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString(EnumPessoa.ativo.name()).equals("S")) {

					lista.add(new Cliente(resultSet.getInt(EnumPessoa.cod.name()),
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

	public Boolean verificarCliente(String doc, String senha) {
		String sql = String.format("Select * from %s where %s=? and %s=?", EnumPessoa.pessoa, EnumPessoa.documento,
				EnumPessoa.senha);
		try (PreparedStatement statement = conexao.prepareStatement(sql);) {

			statement.setString(1, doc);
			statement.setString(2, senha);

			ResultSet resultSet = statement.executeQuery();

			return resultSet.next();

		} catch (Exception e) {
			Alerts.alertErro("Erro", "Erro ao tentar verificar cliente cadastrado", e.getMessage()).showAndWait();
		}
		return false;
	}

	public Cliente buscar(Integer cod) {
		Cliente cliente = new Cliente();
		String sql = String.format("SELECT * FROM %s p inner join %s c on p.%s=c.%s WHERE p.%s=?", EnumPessoa.pessoa,
				EnumCliente.cliente, EnumPessoa.cod, EnumCliente.pessoa_cod);

		try (PreparedStatement statement = conexao.prepareStatement(sql);) {
			statement.setInt(1, cod);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				if (resultSet.getString(EnumPessoa.ativo.name()).equals("S")) {
					cliente = new Cliente(resultSet.getInt(EnumPessoa.cod.name()),
							resultSet.getString(EnumPessoa.nome.name()),
							resultSet.getString(EnumPessoa.documento.name()),
							resultSet.getNString(EnumPessoa.telefone.name()),
							resultSet.getString(EnumPessoa.senha.name()));
				}
				return cliente;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		throw new SelectFalhouException("Erro ao buscar o cliente");
	}

}
