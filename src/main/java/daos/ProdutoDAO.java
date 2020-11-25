package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daos.interfaces.IProdutoDAO;
import entitys.Processador;
import entitys.Produto;
import entitys.dtos.ProdutoDTO;
import exceptions.SelectFalhouException;
import utils.enums.EnumMemoria;
import utils.enums.EnumPlacaDeVideo;
import utils.enums.EnumProcessador;
import utils.enums.EnumProduto;

public class ProdutoDAO implements IProdutoDAO {

	private Connection conexao;

	public ProdutoDAO(Connection conexaoMySql) {
		super();
		conexao = conexaoMySql;
	}

	@Override
	public List<ProdutoDTO> listarProdutos() {
		List<ProdutoDTO> lista = new ArrayList<ProdutoDTO>();

		String sql = String.format("SELECT * FROM %s", EnumProduto.produtobase);

		try (PreparedStatement statementProcessador = conexao.prepareStatement(sql);) {

			ResultSet resultSetProcessador = statementProcessador.executeQuery();

			while (resultSetProcessador.next()) {
				if (resultSetProcessador.getString(EnumProduto.ativo.name()).equals("S")) {
					lista.add(new ProdutoDTO(resultSetProcessador.getInt(EnumProduto.cod.name()),
							resultSetProcessador.getDouble(EnumProduto.peso.name()),
							resultSetProcessador.getString(EnumProduto.nome.name()),
							resultSetProcessador.getString(EnumProduto.descricao.name()),
							resultSetProcessador.getDouble(EnumProduto.preco.name())));
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
	public String verificarTipo(int cod) {
		String sqlProcessador = String.format("SELECT * FROM %s p inner join %s d on p.%s=d.%s where p.%s=?",
				EnumProduto.produtobase, EnumProcessador.processador, EnumProduto.cod, EnumProcessador.produtobase_cod,
				EnumProduto.cod);
		String sqlMemorias = String.format("SELECT * FROM %s p inner join %s d on p.%s=d.%s where p.%s=?", EnumProduto.produtobase,
				EnumMemoria.memoria, EnumProduto.cod, EnumMemoria.produtobase_cod, EnumProduto.cod);
		String sqlPlacaDeVideo = String.format("SELECT * FROM %s p inner join %s d on p.%s=d.%s where p.%s=?",
				EnumProduto.produtobase, EnumPlacaDeVideo.placadevideo, EnumProduto.cod,
				EnumPlacaDeVideo.produtobase_cod, EnumProduto.cod);

		try (PreparedStatement statementProcessador = conexao.prepareStatement(sqlProcessador);
				PreparedStatement statementMemoria = conexao.prepareStatement(sqlMemorias);
				PreparedStatement statementPlacaDeVideo = conexao.prepareStatement(sqlPlacaDeVideo);) {

			statementProcessador.setInt(1, cod);
			statementMemoria.setInt(1, cod);
			statementPlacaDeVideo.setInt(1, cod);
			
			ResultSet resultSetProcessador = statementProcessador.executeQuery();
			ResultSet resultSetMemoria = statementMemoria.executeQuery();
			ResultSet resultSetPlacaDeVidedo = statementPlacaDeVideo.executeQuery();

			if (resultSetProcessador.next())
				return EnumProcessador.processador.name();
			if (resultSetMemoria.next())
				return EnumMemoria.memoria.name();
			if (resultSetPlacaDeVidedo.next())
				return EnumPlacaDeVideo.placadevideo.name();

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		throw new SelectFalhouException("Não foi possivel encontrar o produto");
	}

}
