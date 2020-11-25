package controllers;

import daos.interfaces.IClienteDAO;
import entitys.Cliente;
import exceptions.InsertFalhouException;
import javafx.event.ActionEvent;
import utils.Alerts;
import utils.TrocarScene;

public class CadClienteController /*extends IControllerPadrao<Cliente, Integer>*/  {

	private IClienteDAO dao;

	public CadClienteController(IClienteDAO clienteDao) {
		dao = clienteDao;
	}

	public void inserirCliente(Cliente obj, ActionEvent event) {
		try {
			if (dao.inserir(obj) != 1)
				throw new InsertFalhouException("Erro ao cadastrar cliente");
			Alerts.alertConfirmacao("Cadastrado", "Cliente cadastrado com sucesso",
					"Já pode começar a usufruir da sua conta").showAndWait();
			new TrocarScene().trocar(event, "/views/Login.fxml");
		} catch (Exception e) {
			Alerts.alertErro("Erro", "Não foi possivel cadastrar oo cliente", e.getMessage()).showAndWait();
		}
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
