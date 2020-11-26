package views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import controllers.Login;
import entitys.Produto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import utils.Alerts;
import utils.TrocarScene;

public class PrincipalView implements Initializable {

	private ArrayList<Painel> listaDePaineis = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		criaDadosListView();
		try {
			carregaFXML(new Painel("Home", "/views/Home.fxml"));
		} catch (Exception e) {
			Alerts.alertErro("Erro ao trocar para home", "No contrutor da classe", e.getMessage());
		}

		listaAcoes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Painel>() {
			@Override
			public void changed(ObservableValue<? extends Painel> arg0, Painel arg1, Painel arg2) {
				carregaFXML(arg2);
			}
		});
	}

	private void criaDadosListView() {
		listaDePaineis.add(new Painel("Home", "/views/Home.fxml"));
		listaDePaineis.add(new Painel("Login", "/views/Login.fxml"));
		listaDePaineis.add(new Painel("Gravar processador", "/views/CadProcessador.fxml"));
		listaDePaineis.add(new Painel("Cadastrar Funcionario", "/views/CadFuncionario.fxml"));
		listaDePaineis.add(new Painel("Listar funcionários", "/views/ListaFuncionario.fxml"));

		listaAcoes.setItems(FXCollections.observableArrayList(listaDePaineis));
	}

	public void sair(ActionEvent event) {
		Login.getPrimaryStage().close();
	}

	@FXML
	void menuItemCadPessoa_Action(ActionEvent event) {
		carregaFXML(new Painel("Cadastrar cliente", "/views/CadCliente.fxml"));
	}

	@FXML
	void menuItemCadProduto_Action(ActionEvent event) {
		carregaFXML(new Painel("Cadastrar cliente", "/views/CadProcessador.fxml"));
	}

	public void mudarPanePrincipal(String nomeTela, Pane pane) {
//		for(Painel p : listaDePaineis) {
//			if(p.identificacao.equals(nomeTela));
//			listaAcoes.getSelectionModel().select(p);
//		}
		borderPane.setCenter(pane);
	}

	@FXML
	void Deslogar_Action(ActionEvent event) {

	}

	@FXML
	void Sair_Action(ActionEvent event) {
		Alert ConfirmaExcluir = new Alert(AlertType.CONFIRMATION);

		ConfirmaExcluir.setTitle("Exclusão");
		ConfirmaExcluir.setHeaderText("Deseja realmente excluir o cadastro do funcionário selecionado?");

		Optional<ButtonType> result = ConfirmaExcluir.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			sair(event);
		}
	}

	private void carregaFXML(Painel painel) {
		try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource(painel.FXML));
			if (painel.identificacao.equals("Home")) {
				fxml.setController(new HomeView(this));
			}
			if (painel.identificacao.equals("Cadastrar Funcionario")) {
				fxml.setController(new CadFuncionarioView(this));
			}
			if (painel.identificacao.equals("Listar funcionários")) {
				fxml.setController(new ListarFuncionarioView(this));
			}
			borderPane.setCenter(fxml.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private BorderPane borderPane;

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem menuItemCadPessoa;

	@FXML
	private MenuItem menuItemCadProduto;

	@FXML
	private MenuItem menuItemDeslogar;

	@FXML
	private ListView<Painel> listaAcoes;

	@FXML
	private JFXButton btnSair;

	@FXML
	private TableView<Produto> tableProcessador;

	@FXML
	private TableColumn<Produto, String> cNome;

	@FXML
	private TableColumn<Produto, String> cPreco;

	@FXML
	private TableColumn<Produto, Produto> cDetalhes;

	private class Painel {
		private String identificacao;
		private String FXML;

		public Painel(String identificacao, String fXML) {
			super();
			this.identificacao = identificacao;
			FXML = fXML;
		}

		@Override
		public String toString() {
			return identificacao;
		}

	}

	@FXML
	void initialize() {
		assert borderPane != null : "fx:id=\"borderPane\" was not injected: check your FXML file 'Principal.fxml'.";
		assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'Principal.fxml'.";
		assert menuItemCadPessoa != null : "fx:id=\"menuItemCadPessoa\" was not injected: check your FXML file 'Principal.fxml'.";
		assert menuItemCadProduto != null : "fx:id=\"menuItemCadProduto\" was not injected: check your FXML file 'Principal.fxml'.";
		assert menuItemDeslogar != null : "fx:id=\"menuItemSair\" was not injected: check your FXML file 'Principal.fxml'.";
		assert listaAcoes != null : "fx:id=\"listaAcoes\" was not injected: check your FXML file 'Principal.fxml'.";
		assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'Principal.fxml'.";
		assert tableProcessador != null : "fx:id=\"tableProcessador\" was not injected: check your FXML file 'Principal.fxml'.";
		assert cNome != null : "fx:id=\"cNome\" was not injected: check your FXML file 'Principal.fxml'.";
		assert cPreco != null : "fx:id=\"cPreco\" was not injected: check your FXML file 'Principal.fxml'.";
		assert cDetalhes != null : "fx:id=\"cDetalhes\" was not injected: check your FXML file 'Principal.fxml'.";

	}
}
