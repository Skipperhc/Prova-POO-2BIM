package views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PrincipalView implements Initializable {

	private ArrayList<Painel> listaDePaineis = new ArrayList<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		criaDadosListView();
		
		Pane pane = carregaFXML("/views/Home.fxml");
		borderPane.setCenter(pane);
		
		listaAcoes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Painel>() {
			@Override
			public void changed(ObservableValue<? extends Painel> arg0, Painel arg1, Painel arg2) {
				Pane pane = carregaFXML(arg2.FXML);
				borderPane.setCenter(pane);
			}
		});
	}

	private void criaDadosListView() {
		listaDePaineis.add(new Painel("Home", "/views/Home.fxml"));
		listaDePaineis.add(new Painel("Login", "/views/Login.fxml"));
		listaDePaineis.add(new Painel("Cadastrar cliente", "/views/CadCliente.fxml"));
		listaDePaineis.add(new Painel("Gravar processador", "/views/CadProcessador.fxml"));

		listaAcoes.setItems(FXCollections.observableArrayList(listaDePaineis));
	}
	
	public void sair(ActionEvent event) {
		Login.getPrimaryStage().close();
	}
	
	@FXML
	void menuItemCadPessoa_Action(ActionEvent event) {
		Pane pane = carregaFXML("/views/CadCliente.fxml");
		borderPane.setCenter(pane);
	}
	
	@FXML
	void menuItemCadProduto_Action(ActionEvent event) {
		Pane pane = carregaFXML("/views/CadProcessador.fxml");
		borderPane.setCenter(pane);
	}

	public void mudarPanePrincipal(Pane pane) {
		borderPane.setCenter(pane);
	}
	
	@FXML
	void Deslogar_Action(ActionEvent event) {
		
	}
	
	@FXML
	void Sair_Action(ActionEvent event) {
		sair(event);
	}

	private Pane carregaFXML(String fxml) {
		try {
			return FXMLLoader.load(getClass().getResource(fxml));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
