package views;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import controllers.HomeController;
import daos.ProdutoDAO;
import entitys.Processador;
import entitys.Produto;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import utils.Alerts;
import utils.ConexaoMySql;
import utils.FormatacaoNumeros;
import utils.GenericTableButton;
import utils.TrocarScene;
import utils.enums.EnumMemoria;
import utils.enums.EnumPlacaDeVideo;
import utils.enums.EnumProcessador;

public class HomeView implements Initializable {

	public static final String PEN_SOLID = "M290.74 93.24l128.02 128.02-277.99 277.99-114.14 12.6C11.35 513.54-1.56 500.62.14 485.34l12.7-114.22 277.9-277.88zm207.2-19.06l-60.11-60.11c-18.75-18.75-49.16-18.75-67.91 0l-56.55 56.55 128.02 128.02 56.55-56.55c18.75-18.76 18.75-49.16 0-67.91z";

	HomeController controller;
	Connection conexao;
	PrincipalView principalView;
	
	public HomeView(PrincipalView principalView) {
		try {
			this.principalView = principalView;
			conexao = ConexaoMySql.getInstance().getConnection();
			controller = new HomeController(new ProdutoDAO(conexao));
		} catch (Exception e) {
			Alerts.alertErro("Erro no ProdutoDAO", "Erro na hora de instanciar uma conexao my sql", e.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			iniciarColunas();
			tableView.setItems(FXCollections.observableArrayList(controller.listarProdutos()));
		} catch (Exception e) {
			Alerts.alertErro("Erro ao listar", "Não foi possivel carregar dados do banco", e.getMessage());
		}
	}

	private void iniciarColunas() {
		cNome.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Produto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Produto, String> param) {
						return new ReadOnlyStringWrapper(param.getValue().getNome());
					}
				});

		cPreco.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Produto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Produto, String> param) {
						return new ReadOnlyStringWrapper(String.format("R$ %s", FormatacaoNumeros.round(param.getValue().getPreco(), 2)));
					}
				});

		GenericTableButton.initButtons(cBtn, 15, PEN_SOLID, "svg-gray", (Produto produto, ActionEvent event) -> {
			try {
				String tipo = controller.acharTipoProduto(produto.getCod());
				String path = "/views/Home.fxml";
				if (tipo.equals(EnumProcessador.processador.name())) {
					FXMLLoader fxml = new FXMLLoader(getClass().getResource("/views/EditarProcessador.fxml"));
					fxml.setController(new EditarProcessadorView(principalView, produto.getCod()));
					principalView.mudarPanePrincipal("" ,fxml.load());
				}
				if (tipo.equals(EnumMemoria.memoria.name())) {
					//TODO: trocar tela para editar Memoria
				}
				if (tipo.equals(EnumPlacaDeVideo.placadevideo.name())) {
					//TODO: trocar tela para editar Placa
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<Produto> tableView;

	@FXML
	private TableColumn<Produto, String> cNome;

	@FXML
	private TableColumn<Produto, String> cPreco;

	@FXML
	private TableColumn<Produto, Produto> cBtn;

	@FXML
	void initialize() {
		assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'Home.fxml'.";
		assert cNome != null : "fx:id=\"cNome\" was not injected: check your FXML file 'Home.fxml'.";
		assert cPreco != null : "fx:id=\"cPrreço\" was not injected: check your FXML file 'Home.fxml'.";
		assert cBtn != null : "fx:id=\"cBtn\" was not injected: check your FXML file 'Home.fxml'.";

	}
}
