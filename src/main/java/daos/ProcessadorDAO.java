package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daos.interfaces.IProcessadorDAO;
import entitys.Processador;
import exceptions.DeleteFalhouException;
import exceptions.InsertFalhouException;
import exceptions.SelectFalhouException;
import utils.enums.EnumCliente;
import utils.enums.EnumPessoa;
import utils.enums.EnumProcessador;
import utils.enums.EnumProduto;

public class ProcessadorDAO implements IProcessadorDAO {

	Connection conexao;

	public ProcessadorDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}

	@Override
	public int inserir(Processador obj) {
		String sqlProduto = String.format("INSERT INTO %s ( %s, %s, %s, %s) VALUES (?, ?, ?, ?)", EnumProduto.produtobase,
				EnumProduto.peso, EnumProduto.nome, EnumProduto.descricao, EnumProduto.preco);

		String sqlProdutoIns = String.format("SELECT * FROM %s ORDER BY cod DESC LIMIT 1", EnumProduto.produtobase);

		String sqlProcessador = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
				EnumProcessador.processador, EnumProcessador.produtobase_cod, EnumProcessador.nucleos,
				EnumProcessador.threads);

		String sqlDelProduto = String.format("delete from %s where %s=?", EnumProduto.produtobase, EnumProduto.cod);

		try (PreparedStatement statementProduto = conexao.prepareStatement(sqlProduto);
				PreparedStatement statementProdutoIns = conexao.prepareStatement(sqlProdutoIns);
				PreparedStatement statementProcessador = conexao.prepareStatement(sqlProcessador);
				PreparedStatement statementDelPrroduto = conexao.prepareStatement(sqlDelProduto);) {

			statementProduto.setDouble(1, obj.getPeso());
			statementProduto.setString(2, obj.getNome());
			statementProduto.setString(3, obj.getDescricao());
			statementProduto.setDouble(4, obj.getPreco());

			if (statementProduto.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir a Produto");

			ResultSet resultSet = statementProdutoIns.executeQuery();
			resultSet.next();

			statementProcessador.setInt(1, resultSet.getInt(EnumProduto.cod.name()));
			statementProcessador.setInt(2, obj.getNucleos());
			statementProcessador.setInt(3, obj.getThreads());

			if (statementProcessador.executeUpdate() != 1) {
				if (statementDelPrroduto.executeUpdate() != 1)
					throw new DeleteFalhouException("Não foi possivel inserir a Produto");
				throw new InsertFalhouException("Não foi possivel inserir a Processador");
			}

			return 1;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	@Override
	public int editar(Processador obj) {
		String sqlPessoa = String.format("update %s set %s =?, %s =?, %s =?, %s =? where %s =?", EnumProduto.produtobase,
				EnumProduto.peso, EnumProduto.nome, EnumProduto.descricao, EnumProduto.preco, EnumProduto.cod);

		String sqlCliente = String.format("update %s set %s =?, %s =? where %s =?", EnumProcessador.processador,
				EnumProcessador.nucleos, EnumProcessador.threads, EnumProcessador.produtobase_cod);

		try (PreparedStatement statementPessoa = conexao.prepareStatement(sqlPessoa);
				PreparedStatement statementCliente = conexao.prepareStatement(sqlCliente);) {

			statementPessoa.setDouble(1, obj.getPeso());
			statementPessoa.setString(2, obj.getNome());
			statementPessoa.setString(3, obj.getDescricao());
			statementPessoa.setDouble(4, obj.getPreco());
			statementPessoa.setInt(5, obj.getCod());

			statementCliente.setInt(1, obj.getNucleos());
			statementCliente.setInt(2, obj.getThreads());
			statementCliente.setInt(3, obj.getCod());

			if (statementCliente.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir a Cliente");

			if (statementPessoa.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir a Cliente");

			return 1;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	@Override
	public int deletar(Integer cod) {
		String sql = String.format("update %s set %s ='N' where %s = ?", EnumProduto.produtobase, EnumProduto.ativo,
				EnumProduto.cod);

		try (PreparedStatement statement = conexao.prepareStatement(sql);) {

			statement.setInt(1, cod);

			if (statement.executeUpdate() != 1)
				throw new InsertFalhouException("Não foi possivel inserir a Cliente");

			return 1;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return 0;
	}

	@Override
	public List<Processador> listar() {
		List<Processador> lista = new ArrayList<Processador>();

		String sql = String.format("SELECT * FROM %s pd inner join %s pc on pd.%s=pc.%s", EnumProduto.produtobase,
				EnumProcessador.processador, EnumProduto.cod, EnumProcessador.produtobase_cod);

		try (PreparedStatement statement = conexao.prepareStatement(sql);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString(EnumProduto.ativo.name()).equals("S")) {
					if (resultSet.getString(EnumProduto.ativo.name()).equals("S")) {
						lista.add(new Processador(resultSet.getInt(EnumProcessador.nucleos.name()),
								resultSet.getInt(EnumProcessador.threads.name()),
								resultSet.getInt(EnumProduto.cod.name()), 
								resultSet.getDouble(EnumProduto.peso.name()),
								resultSet.getString(EnumProduto.nome.name()),
								resultSet.getString(EnumProduto.descricao.name()),
								resultSet.getDouble(EnumProduto.preco.name())));
					}
				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return lista;
	}

	@Override
	public Processador buscar(Integer cod) {
		Processador processador = new Processador();
		String sql = String.format("SELECT * FROM %s p inner join %s d on p.%s=d.%s WHERE p.%s=?", EnumProduto.produtobase, EnumProcessador.processador, EnumProduto.cod, EnumProcessador.produtobase_cod, EnumProduto.cod);

		try (PreparedStatement statement = conexao.prepareStatement(sql);) {
			statement.setInt(1, cod);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				if (resultSet.getString(EnumProduto.ativo.name()).equals("S")) {
					processador = new Processador(resultSet.getInt(EnumProcessador.nucleos.name()),
							resultSet.getInt(EnumProcessador.threads.name()),
							resultSet.getInt(EnumProduto.cod.name()), 
							resultSet.getDouble(EnumProduto.peso.name()),
							resultSet.getString(EnumProduto.nome.name()),
							resultSet.getString(EnumProduto.descricao.name()),
							resultSet.getDouble(EnumProduto.preco.name()));
				}
				return processador;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		throw new SelectFalhouException("Erro ao buscar o processador");
	}

}
