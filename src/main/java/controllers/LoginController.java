package controllers;

import daos.ClienteDAO;
import javafx.event.ActionEvent;
import utils.Alerts;
import utils.TrocarScene;

public class LoginController {

	ClienteDAO dao;

	public LoginController(ClienteDAO clienteDAO) {
		dao = clienteDAO;
	}

	public void trocarParaCadCliente(ActionEvent event) {
		try {
			new TrocarScene().trocar(event, "/views/CadCliente.fxml");
		} catch (Exception e) {
			Alerts.alertErro("Erro", "N�o foi possivel trocar de scene", e.getMessage()).showAndWait();
		}
	}

	public void logar(ActionEvent event, String doc, String senha) {
		try {
			if (dao.verificarCliente(doc, senha)) {
				new TrocarScene().trocar(event, "/views/Principal.fxml");
			} else {
				Alerts.alertAviso("Cliente n�o encontrado", "N�o foi possivel logar", "Tente novamente").showAndWait();
			}
		} catch (Exception e) {
			Alerts.alertErro("Erro", "N�o foi possivel trocar de scene", e.getMessage()).showAndWait();
		}
	}
}
