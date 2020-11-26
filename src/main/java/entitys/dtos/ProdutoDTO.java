package entitys.dtos;

import entitys.Produto;

public class ProdutoDTO extends Produto {
	public ProdutoDTO() {
		super();
	}

	public ProdutoDTO(Double peso, String nome, String descricao, Double preco) {
		super(peso, nome, descricao, preco);
	}
	
	public ProdutoDTO(int cod, Double peso, String nome, String descricao, Double preco) {
		super(cod, peso, nome, descricao, preco);
	}

	@Override
	public Double calcularImposto() {
		return getPreco();
	}
}
