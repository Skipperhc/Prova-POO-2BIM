package views;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import controllers.ProcessadorController;
import daos.ProcessadorDAO;
import entitys.Processador;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import utils.Alerts;
import utils.ConexaoMySql;
import utils.FormatacaoNumeros;

public class EditarProcessadorView implements Initializable {

	ProcessadorController controller;
	Processador processador;
	PrincipalView principalView;

	public EditarProcessadorView(PrincipalView principalView, int cod) {
		try {
			this.principalView = principalView;
			controller = new ProcessadorController(new ProcessadorDAO(ConexaoMySql.getInstance().getConnection()));
			processador = controller.buscarProcessador(cod);
		} catch (Exception e) {
			Alerts.alertErro("Erro grave", "pelo jeito", "travou o conector com mysql");
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtPreco.setText(String.format("%s", FormatacaoNumeros.round(processador.getPreco(), 2)));
		txtNome.setText(processador.getNome());
		txtDescricao.setText(processador.getDescricao());
		txtPeso.setText(String.format("%s", FormatacaoNumeros.round(processador.getPeso(), 2)));
		txtNucleos.setText(String.format("%s", processador.getNucleos()));
		txtThreads.setText(String.format("%s", processador.getThreads()));

		addListener(txtPreco);
		addListener(txtNome);
		txtDescricao.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
				if (verificarCampos()) {
					btnEditar.setDisable(true);
				} else {
					btnEditar.setDisable(false);
				}
			}
		});
		addListener(txtPeso);
		addListener(txtNucleos);
		addListener(txtThreads);
	}

	private void addListener(JFXTextField campo) {
		campo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
				if (verificarCampos()) {
					btnEditar.setDisable(true);
				} else {
					btnEditar.setDisable(false);
				}
			}
		});
	}

	@FXML
	void EditarProduto_Action(ActionEvent event) {
		controller.editarProcessador(event,
				new Processador(Integer.parseInt(txtNucleos.getText()), Integer.parseInt(txtThreads.getText()),
						processador.getCod(), Double.parseDouble(txtPeso.getText()), txtNome.getText(),
						txtDescricao.getText(), Double.parseDouble(txtPreco.getText())));
	}

	@FXML
	void ExcluirProduto_Action(ActionEvent event) {
		controller.removerProcessador(event, processador.getCod());
	}

	@FXML
	void cancelar_Action(ActionEvent event) {
		controller.cancelarAction(event);
	}

	private Boolean verificarCampos() {
		String msg = controller.verificarCampos(txtPreco.getText(), txtNome.getText(), txtDescricao.getText(),
				txtPeso.getText(), txtNucleos.getText(), txtThreads.getText());
		errorMessage.setText(msg);
		return msg.length() > 0;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane pane;

	@FXML
	private JFXTextField txtPreco;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextArea txtDescricao;

	@FXML
	private JFXTextField txtPeso;

	@FXML
	private JFXTextField txtNucleos;

	@FXML
	private JFXTextField txtThreads;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private Slider slider;

	@FXML
	private Label txtLoading;

	@FXML
	private JFXSpinner loading;

	@FXML
	private Label errorMessage;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	void initialize() {
		assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert txtPreco != null : "fx:id=\"txtPreco\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert txtDescricao != null : "fx:id=\"txtDescricao\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert txtPeso != null : "fx:id=\"txtPeso\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert txtNucleos != null : "fx:id=\"txtNucleos\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert txtThreads != null : "fx:id=\"txtThreads\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert btnCancelar != null : "fx:id=\"btnCancelar\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert btnEditar != null : "fx:id=\"btnEditar\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert slider != null : "fx:id=\"slider\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert txtLoading != null : "fx:id=\"txtLoading\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert loading != null : "fx:id=\"loading\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert errorMessage != null : "fx:id=\"errorMessage\" was not injected: check your FXML file 'EditarProcessador.fxml'.";
		assert btnExcluir != null : "fx:id=\"btnExcluir\" was not injected: check your FXML file 'EditarProcessador.fxml'.";

	}
}
