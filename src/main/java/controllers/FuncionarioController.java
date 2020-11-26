package controllers;

import daos.interfaces.IFuncionarioDAO;
import entitys.Funcionario;
import exceptions.CampoInvalidoException;
import exceptions.InsertFalhouException;
import javafx.event.ActionEvent;
import utils.Alerts;
import utils.TrocarScene;

public class FuncionarioController {

	private IFuncionarioDAO dao;

	public FuncionarioController(IFuncionarioDAO FuncionarioDao) {
		dao = FuncionarioDao;
	}

	public void inserirFuncionario(Funcionario obj, ActionEvent event) {
		try {
			if (true) {
				if (dao.inserir(obj) != 1)
					throw new InsertFalhouException("Erro ao cadastrar Funcionario");
				Alerts.alertConfirmacao("Cadastrado", "Funcionario cadastrado com sucesso",
						"Já pode começar a usufruir da sua conta").showAndWait();
				new TrocarScene().trocar(event, "/views/Principal.fxml");
			}
		} catch (Exception e) {
			Alerts.alertErro("Erro", "Não foi possivel cadastrar oo Funcionario", e.getMessage()).showAndWait();
		}
	}
	
	public void editarProcessador(ActionEvent event, Funcionario obj) {
		try {
			if (dao.editar(obj) != 1)
				throw new InsertFalhouException("Não foi possivel cadastrar o funcionario");
			voltarHome(event);
		} catch (Exception e) {
			Alerts.alertErro("Erro na hora de cadastrar", "Não foi possivel cadastrar o funcionario", e.getMessage());
		}
	}
	
	private void voltarHome(ActionEvent event) {
		new TrocarScene().trocar(event, "/views/Principal.fxml");
	}

	public String verificarCampos(String cargo, int acesso, String nome, String documento, String telefone,
			String senha, String senha2) {
		try {
			if (nome == null | nome.equals("") | (nome.length() < 3 || nome.length() > 45)) {
				if (nome.length() < 3)
					throw new CampoInvalidoException("Insira um nome maior");
				else if (nome.length() > 45)
					throw new CampoInvalidoException("Insira um nome menor");
				throw new CampoInvalidoException("Preencha o campo nome");
			}

			if (cargo == null | cargo.equals("") | (cargo.length() < 3 || cargo.length() > 45)) {
				if (cargo.equals(""))
					throw new CampoInvalidoException("Preencha o campo Cargo");
				if (cargo.length() < 3 | cargo.length() > 45)
					throw new CampoInvalidoException("Insira um cargo adequado");
			}

			if (documento == null | documento.equals("") | (documento.replaceAll("[^0-9]+", "").length() < 9
					|| documento.replaceAll("[^0-9]+", "").length() > 14)) {
				if (documento.equals(""))
					throw new CampoInvalidoException("Preencha o campo documento");
				if (documento.length() < 9 | documento.length() > 14 | documento.replaceAll("[^0-9]+", "").equals(""))
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
//				TODO: Adicionar maior segurança na senha
				if (senha2 == null | senha2.equals("") | (senha2.length() < 3 || senha2.length() > 50)) {
					if (senha2.equals(""))
						throw new CampoInvalidoException("Confirme a sua senha");
//					TODO: Adicionar maior segurança na senha
				}
				if(!senha.equals(senha2)) throw new CampoInvalidoException("Senhas não batem");
			}
			
			if (acesso == 0) {
				throw new CampoInvalidoException("Preencha o campo acesso");
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
			Alerts.alertErro("Erro", "Erro no cadFuncionario", "Erro no cadLogar_Action").showAndWait();
			e.printStackTrace();
		}
	}

	public void cancelarAction(ActionEvent event) {
		try {
			voltarHome(event);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
