package views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controllers.LoginController;
import daos.ClienteDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Alerts;
import utils.ConexaoMySql;
import utils.TrocarScene;

public class LoginView {

	LoginController controller;

	public LoginView() {
		try {
			controller = new LoginController(new ClienteDAO(ConexaoMySql.getInstance().getConnection()));
		} catch (ClassNotFoundException | SQLException e) {
			Alerts.alertErro("Erro", "Não foi possivel pegar uma instancia do conector MySql", e.getMessage())
					.showAndWait();
		}
	}

	@FXML
	void cadCliente_Action(ActionEvent event) throws IOException {
		controller.trocarParaCadCliente(event);
	}

	@FXML
	void logar_Action(ActionEvent event) {
		controller.logar(event, txtDocumento.getText(), txtSenha.getText());
	}

	@FXML
	void initialize() {
		assert txtDocumento != null : "fx:id=\"txtDocumento\" was not injected: check your FXML file 'Login.fxml'.";
		assert btnCad != null : "fx:id=\"btnCad\" was not injected: check your FXML file 'Login.fxml'.";
		assert btnLogar != null : "fx:id=\"btnLogar\" was not injected: check your FXML file 'Login.fxml'.";
		assert txtSenha != null : "fx:id=\"txtSenha\" was not injected: check your FXML file 'Login.fxml'.";

	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField txtDocumento;

	@FXML
	private JFXButton btnCad;

	@FXML
	private JFXButton btnLogar;

	@FXML
	private JFXPasswordField txtSenha;

}
