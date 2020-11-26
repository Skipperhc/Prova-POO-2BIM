package views;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controllers.FuncionarioController;
import daos.FuncionarioDAO;
import entitys.Funcionario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.Alerts;
import utils.ConexaoMySql;
import utils.constants.ListaCargos;

public class CadFuncionarioView implements Initializable {

	FuncionarioController controller;

	public CadFuncionarioView() {
		try {
			controller = new FuncionarioController(new FuncionarioDAO(ConexaoMySql.getInstance().getConnection()));
		} catch (ClassNotFoundException | SQLException e) {
			Alerts.alertErro("Erro grave", "pelo jeito", "travou o conector com mysql");
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		cbxCargo.setItems(FXCollections.observableArrayList(new ListaCargos().getLista()));
		addListener(txtNome);
		addListener(txtDocumento);
		addListener(txtTelefone);
		addListener(txtSenha);
		addListener(txtConfirmarSenha);
	}

	private void addListener(TextField campo) {
//		TODO: Mudar o tipo do parrametro para os outros tbm
		campo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
				if (verificarCampos()) {
//					btnCad.setDisable(true);
				} else {
//					btnCad.setDisable(false);
				}
			}
		});
	}

	private Boolean verificarCampos() {
		// String cargo, int acesso, String nome, String documento, String telefone,
		// String senha
		String msg = controller.verificarCampos(cbxCargo.getSelectionModel().getSelectedItem().getNome(),
				cbxCargo.getSelectionModel().getSelectedItem().getAcesso(), txtNome.getText(), txtDocumento.getText(),
				txtTelefone.getText(), txtSenha.getText(), txtConfirmarSenha.getText());
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
	private Label txtMensagemErro;

	@FXML
	private JFXPasswordField txtConfirmarSenha;

	@FXML
	private ComboBox<ListaCargos.Cargo> cbxCargo;
	
	@FXML
    private JFXButton btnCadastrar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    void cadastrarAction(ActionEvent event) {

    }

    @FXML
    void voltarAction(ActionEvent event) {

    }
	

//	@FXML
//	private JFXButton btnVoltar;

//	@FXML
//	void cadFuncionario_Action(ActionEvent event) {
//
//	}
//	try {
//			controller.inserirFuncionario(new Funcionario(cbxCargo.getSelectionModel().getSelectedItem().getNome(), 1,
//					txtNome.getText(), txtDocumento.getText(), txtTelefone.getText(), txtSenha.getText()), event);
//		} catch (Exception e) {
//			Alerts.alertErro("Algo deu errado", "Alguma coisa impediu o cadastro", "Erro" + e.getMessage());
//		}
//	}

//	@FXML
//	void cancelar_Action(ActionEvent event) {
//		controller.cancelarAction(event);
//	}
}
