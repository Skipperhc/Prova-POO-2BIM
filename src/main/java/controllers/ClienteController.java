package controllers;

import daos.interfaces.IClienteDAO;
import entitys.Cliente;
import exceptions.CampoInvalidoException;
import exceptions.InsertFalhouException;
import javafx.event.ActionEvent;
import utils.Alerts;
import utils.TrocarScene;

public class ClienteController {

	private IClienteDAO dao;

	public ClienteController(IClienteDAO clienteDao) {
		dao = clienteDao;
	}

	public void inserirCliente(Cliente obj, ActionEvent event) {
		try {
			if (true) {
				if (dao.inserir(obj) != 1)
					throw new InsertFalhouException("Erro ao cadastrar cliente");
				Alerts.alertConfirmacao("Cadastrado", "Cliente cadastrado com sucesso",
						"Já pode começar a usufruir da sua conta").showAndWait();
				new TrocarScene().trocar(event, "/views/Login.fxml");
			}
		} catch (CampoInvalidoException e) {
			
		} catch (Exception e) {
			Alerts.alertErro("Erro", "Não foi possivel cadastrar oo cliente", e.getMessage()).showAndWait();
		}
	}

	public String verificarCampos(String nome, String documento, String telefone,
			String senha) {
		try {
			if (nome == null | nome.equals("") | (nome.length() < 3 || nome.length() > 45)) {
				if (nome.length() < 3)
					throw new CampoInvalidoException("Insira um nome maior");
				else if (nome.length() > 45)
					throw new CampoInvalidoException("Insira um nome menor");
				throw new CampoInvalidoException("Preencha o campo nome");
			}

			if (documento == null | documento.equals("") | (documento.replaceAll("[^0-9]+", "").length() < 9
					|| documento.replaceAll("[^0-9]+", "").length() > 14)) {
				if (documento.equals(""))
					throw new CampoInvalidoException("Preencha o campo documento");
				if (documento.replaceAll("[^0-9]+", "").length() < 9 | documento.replaceAll("[^0-9]+", "").length() > 14 | documento.replaceAll("[^0-9]+", "").equals(""))
					throw new CampoInvalidoException("Insira um documento válido");
			}

			if (telefone == null | telefone.equals("") | (telefone.length() < 8 || telefone.length() > 11)) {
				if (telefone.equals(""))
					throw new CampoInvalidoException("Preencha o campo telefone");
				if (telefone.replaceAll("[^0-9]+", "").equals("") || telefone.replaceAll("[^0-9]+", "").length() < 8
						| telefone.replaceAll("[^0-9]+", "").length() > 11)
					throw new CampoInvalidoException("Insira um telefone válido");
			}

			if (senha == null | senha.equals("") | (senha.length() < 3 || senha.length() > 50)) {
				if (senha.equals(""))
					throw new CampoInvalidoException("Preencha o campo senha");
			}

		} catch (CampoInvalidoException e) {
			return e.getMessage();
		} catch (Exception e) {
			Alerts.alertErro("Erro ao validar os campos", "Algum campo está causando um erro no sistema",
					e.getMessage()).showAndWait();
		}
		return "";
	}

	public void trocarParaLogin(ActionEvent event) {
		try {
			new TrocarScene().trocar(event, "/views/Login.fxml");
		} catch (Exception e) {
			Alerts.alertErro("Erro", "Erro no cadCliente", "Erro no cadLogar_Action").showAndWait();
			e.printStackTrace();
		}
	}
}
