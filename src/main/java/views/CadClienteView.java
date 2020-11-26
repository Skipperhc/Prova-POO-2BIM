package views;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controllers.ClienteController;
import daos.ClienteDAO;
import entitys.Cliente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import utils.Alerts;
import utils.ConexaoMySql;

public class CadClienteView implements Initializable {

	ClienteController controller;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addListener(txtNome);
		addListener(txtDocumento);
		addListener(txtTelefone);
		txtSenha.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
				if (verificarCampos()) {
					btnCad.setDisable(true);
				} else {
					btnCad.setDisable(false);
				}
			}
		});
	}

	public CadClienteView() {
		try {
			controller = new ClienteController(new ClienteDAO(ConexaoMySql.getInstance().getConnection()));
		} catch (ClassNotFoundException | SQLException e) {
			Alerts.alertErro("Erro grave", "pelo jeito", "travou o conector com mysql");
			e.printStackTrace();
		}
	}
	
	private void addListener(JFXTextField campo) {
		campo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
				if (verificarCampos()) {
					btnCad.setDisable(true);
				} else {
					btnCad.setDisable(false);
				}
			}
		});
	}
	
	private Boolean verificarCampos() {
		String msg = controller.verificarCampos(txtNome.getText(), txtDocumento.getText(), txtTelefone.getText(), txtSenha.getText());
		txtMensagemErro.setText(msg);
		return msg.length() > 0;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtDocumento;

	@FXML
	private JFXTextField txtTelefone;

	@FXML
	private JFXPasswordField txtSenha;

	@FXML
	private JFXButton btnCad;

	@FXML
	private JFXButton btnLogar;

	@FXML
    private Label txtMensagemErro;
	
	@FXML
	void cadCliente_Action(ActionEvent event) {
		try {
			controller.inserirCliente(new Cliente(1, txtNome.getText(), txtDocumento.getText(),
					txtTelefone.getText(), txtSenha.getText()), event);
		} catch (Exception e) {
			Alerts.alertErro("Algo deu errado", "Alguma coisa impediu o cadastro", "Erro" + e.getMessage());
		}
	}

	@FXML
	void cadLogar_Action(ActionEvent event) {
		try {
			controller.trocarParaLogin(event);
		} catch (Exception e) {
			Alerts.alertErro("Erro", "Erro no cadCliente", e.getMessage()).showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert txtDocumento != null : "fx:id=\"txtDocumento\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert txtTelefone != null : "fx:id=\"txtTelefone\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert txtSenha != null : "fx:id=\"txtSenha\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert btnCad != null : "fx:id=\"btnCad\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert btnLogar != null : "fx:id=\"btnLogar\" was not injected: check your FXML file 'CadCliente.fxml'.";

	}
}
