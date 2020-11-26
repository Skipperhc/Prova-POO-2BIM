package controllers;

import daos.interfaces.IMemoriaDAO;
import daos.interfaces.IProcessadorDAO;
import entitys.Memoria;
import entitys.Processador;
import exceptions.CampoInvalidoException;
import exceptions.InsertFalhouException;
import javafx.event.ActionEvent;
import utils.Alerts;
import utils.TrocarScene;

public class MemoriaController {

	IMemoriaDAO dao;
	Processador processador;
	
	public MemoriaController(IMemoriaDAO memoriaDAO) {
		dao = memoriaDAO;
	}
	
	public String verificarCampos(String preco, String nome, String descricao, String peso, String nucleos,
			String threads) {
		try {
			if (preco == null | preco.replaceAll("[^0-9]+", "").equals("")) {
				if (preco.replaceAll("[^0-9]+", "").equals(""))
					throw new CampoInvalidoException("Insira um número no preço");
				if (Double.parseDouble(preco) < 0 || Double.parseDouble(preco) > 9999999)
					throw new CampoInvalidoException("Insira o preço correto");
				throw new CampoInvalidoException("Preencha o campo preço");
			}
			if (nome == null | nome.equals("") | (nome.length() < 3 && nome.length() > 50)) {
				if (nome.length() < 6)
					throw new CampoInvalidoException("Insira um nome maior");
				else if (nome.length() > 50)
					throw new CampoInvalidoException("Insira um nome menor");
				throw new CampoInvalidoException("Preencha o campo nome");
			}
			if (descricao == null | descricao.equals("")) {
				if (descricao.length() < 40)
					throw new CampoInvalidoException("Insira uma descrição maior");
				if (descricao.length() > 150)
					throw new CampoInvalidoException("Insira uma descrição menor");
				throw new CampoInvalidoException("Preencha o campo descrição");
			}
			if (peso == null | peso.replaceAll("[^0-9]+", "").equals("")) {
				if (peso.replaceAll("[^0-9]+", "").equals(""))
					throw new CampoInvalidoException("Insira um número no peso");
				if (Double.parseDouble(peso) < 0)
					throw new CampoInvalidoException("Preencha o campo ");
				throw new CampoInvalidoException("Preencha o campo ");
			}
			if (nucleos == null | nucleos.replaceAll("[^0-9]+", "").equals("")) {
				if (nucleos.replaceAll("[^0-9]+", "").equals(""))
					throw new CampoInvalidoException("Insira um número na quantidade de núcleos");
				if (Integer.parseInt(nucleos) < 0)
					throw new CampoInvalidoException("Preencha o campo quantidade de núcleos");
				throw new CampoInvalidoException("Preencha o campo quantidade de núcleos");
			}
			if (threads == null | threads.replaceAll("[^0-9]+", "").equals("")) {
				if (threads.replaceAll("[^0-9]+", "").equals(""))
					throw new CampoInvalidoException("Insira um número na quantidade de threads");
				if (Integer.parseInt(threads) < 0)
					throw new CampoInvalidoException("Preencha o campo quantidade de threads");
				throw new CampoInvalidoException("Preencha o campo quantidade de threads");
			}
		} catch (CampoInvalidoException e) {
			return e.getMessage();
		} catch (Exception e) {
			Alerts.alertErro("Erro ao validar os campos", "Algum campo está causando um erro no sistema",
					e.getMessage()).showAndWait();
		}
		return "";
	}

	public Memoria buscarProcessador(int cod) {
		return dao.buscar(cod);
	}
	
	public void cadProcessador(ActionEvent event, Memoria obj) {
		try {
			if(dao.inserir(obj) != 1) throw new InsertFalhouException("Não foi possivel cadastrar o processador");
			voltarHome(event);
		} catch (Exception e) {
			Alerts.alertErro("Erro na hora de cadastrar", "Não foi possivel cadastrar o processador", e.getMessage());
		}
	}
	
	public void editarProcessador(ActionEvent event, Memoria obj) {
		try {
			if(dao.editar(obj) != 1) throw new InsertFalhouException("Não foi possivel cadastrar o processador");
			voltarHome(event);
		} catch (Exception e) {
			Alerts.alertErro("Erro na hora de cadastrar", "Não foi possivel cadastrar o processador", e.getMessage());
		}
	}
	
	public void removerProcessador(ActionEvent event, int cod) {
		try {
			if(dao.deletar(cod) != 1) throw new InsertFalhouException("Não foi possivel cadastrar o processador");
			voltarHome(event);
		} catch (Exception e) {
			Alerts.alertErro("Erro na hora de cadastrar", "Não foi possivel cadastrar o processador", e.getMessage());
		}
	}
	
	private void voltarHome(ActionEvent event) {
		new TrocarScene().trocar(event, "/views/Principal.fxml");
	}
	
	public void cancelarAction(ActionEvent event) {
		try {
			voltarHome(event);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
