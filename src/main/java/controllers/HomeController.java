package controllers;

import java.util.ArrayList;
import java.util.List;

import daos.ProdutoDAO;
import entitys.dtos.ProdutoDTO;
import utils.Alerts;

public class HomeController {

	ProdutoDAO dao;
	
	public HomeController(ProdutoDAO produtoDAO) {
		dao = produtoDAO;
	}
	
	public List<ProdutoDTO> listarProdutos() {
		List<ProdutoDTO> lista = new ArrayList<ProdutoDTO>();
		try {
			lista = dao.listarProdutos(); 
		} catch (Exception e) {
			Alerts.alertErro("Erro ao listar produtos", "Erro no Home controller ao listar", e.getMessage());
		}
		return lista;
	}
	
	public String acharTipoProduto(int cod) {
		return dao.verificarTipo(cod);
	}
}
