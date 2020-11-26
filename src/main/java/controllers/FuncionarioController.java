package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import daos.FuncionarioDAO;
import daos.interfaces.IFuncionarioDAO;
import entitys.Funcionario;
import exceptions.CampoInvalidoException;
import exceptions.FormatacaoException;
import exceptions.InsertFalhouException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import utils.Alerts;
import utils.Formatacao;
import utils.TrocarScene;
import views.ListarFuncionarioView;

public class FuncionarioController {

	private FuncionarioDAO dao;

	public FuncionarioController(FuncionarioDAO FuncionarioDao) {
		dao = FuncionarioDao;
	}

	public int inserirFuncionario(Funcionario obj, ActionEvent event) {
		try {
			if (dao.inserir(obj) != 1)
				throw new InsertFalhouException("Erro ao cadastrar Funcionario");
			Alerts.alertAviso("Cadastrado", "Funcionario cadastrado com sucesso",
					"Já pode começar a usufruir da sua conta").showAndWait();
			return 1;
		} catch (Exception e) {
			Alerts.alertErro("Erro", "Não foi possivel cadastrar oo Funcionario", e.getMessage()).showAndWait();
		}
		return 0;
	}

	public int editarFuncionario(ActionEvent event, Funcionario obj) {
		try {
			if (dao.editar(obj) != 1)
				throw new InsertFalhouException("Não foi possivel cadastrar o funcionario");
			Alerts.alertAviso("Cadastrado", "Funcionario editado com sucesso", "").showAndWait();
			return 1;
		} catch (Exception e) {
			Alerts.alertErro("Erro na hora de cadastrar", "Não foi possivel cadastrar o funcionario", e.getMessage());
		}
		return 0;
	}

	public int deletarFuncionario(ActionEvent event, int cod) {
		try {
			Alert ConfirmaExcluir = new Alert(AlertType.CONFIRMATION);

			ConfirmaExcluir.setTitle("Exclusão");
			ConfirmaExcluir.setHeaderText("Deseja realmente excluir o cadastro do funcionário selecionado?");

			Optional<ButtonType> result = ConfirmaExcluir.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				if (dao.deletar(cod) != 1)
					throw new InsertFalhouException("Não foi possivel cadastrar o funcionario");
				return 1;
			}
		} catch (Exception e) {
			Alerts.alertErro("Erro na hora de cadastrar", "Não foi possivel cadastrar o funcionario", e.getMessage());
		}
		return 0;
	}

	public List<Funcionario> listarFuncionarios() {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		try {
			return dao.listar();
		} catch (Exception e) {
			Alerts.alertErro("Erro ao listar", "Não foi possivel listar os funcionarios", e.getMessage());
		}
		return lista;
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

			if (documento == null | documento.equals("")
					| (documento.replaceAll("[^0-9]+", "").length() < 9
							|| documento.replaceAll("[^0-9]+", "").length() > 14)
					| documento.replaceAll("[^0-9]+", "").length() == documento.length()) {
				if (documento.equals(""))
					throw new CampoInvalidoException("Preencha o campo documento");
			}

			if (documento.replaceAll("[^0-9]+", "").length() == documento.length())
				throw new CampoInvalidoException("Insira um documento formatado");

			Formatacao.formatarDocumento(documento);

			if (telefone == null | telefone.equals("") | (telefone.length() < 8 || telefone.length() > 11)) {
				if (telefone.equals(""))
					throw new CampoInvalidoException("Preencha o campo telefone");
				if (telefone.replaceAll("[^0-9]+", "").equals("") || telefone.replaceAll("[^0-9]+", "").length() < 8
						| telefone.replaceAll("[^0-9]+", "").length() > 11)
					throw new CampoInvalidoException("Insira um telefone válido");
			}

			Formatacao.formatarTelefone(telefone);

			if (cargo == null | cargo.equals("") | (cargo.length() < 3 || cargo.length() > 45)) {
				if (cargo.equals(""))
					throw new CampoInvalidoException("Selecione um Cargo");
				if (cargo.length() < 3 | cargo.length() > 45)
					throw new CampoInvalidoException("Selecione um cargo adequado");
			}

			if (senha == null | senha.equals("") | (senha.length() < 3 || senha.length() > 50)) {
				if (senha.equals(""))
					throw new CampoInvalidoException("Preencha o campo senha");
//				TODO: Adicionar maior segurança na senha
			}
			if (senha2 == null | senha2.equals("") | (senha2.length() < 3 || senha2.length() > 50)) {
				if (senha2.equals(""))
					throw new CampoInvalidoException("Confirme a sua senha");
//				TODO: Adicionar maior segurança na senha
			}

			if (!senha.equals(senha2))
				throw new CampoInvalidoException("Senhas não batem");

			if (acesso == 0)
				throw new CampoInvalidoException("Preencha o campo acesso");

			if (dao.verificaRG(0, documento.replaceAll("[^0-9]+", "")))
				throw new CampoInvalidoException("Este documento já está em uso.");

		} catch (FormatacaoException e) {
			return e.getMessage();
		} catch (CampoInvalidoException e) {
			return e.getMessage();
		} catch (Exception e) {
			Alerts.alertErro("Erro ao validar os campos", "Algum campo está causando um erro no sistema",
					e.getMessage()).showAndWait();
		}
		return "";
	}
}
