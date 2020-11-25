package daos.interfaces;

import java.util.List;

import entitys.dtos.ProdutoDTO;

public interface IProdutoDAO {

	public List<ProdutoDTO> listarProdutos();
	
	public String verificarTipo(int cod);
}
