package views;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import controllers.FuncionarioController;
import controllers.HomeController;
import daos.FuncionarioDAO;
import entitys.Processador;
import entitys.Funcionario;
import entitys.Funcionario;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import utils.Alerts;
import utils.ConexaoMySql;
import utils.Formatacao;
import utils.FormatacaoNumeros;
import utils.GenericTableButton;
import utils.TrocarScene;
import utils.enums.EnumMemoria;
import utils.enums.EnumPlacaDeVideo;
import utils.enums.EnumProcessador;

public class ListarFuncionarioView implements Initializable {

	public static final String PEN_SOLID = "M290.74 93.24l128.02 128.02-277.99 277.99-114.14 12.6C11.35 513.54-1.56 500.62.14 485.34l12.7-114.22 277.9-277.88zm207.2-19.06l-60.11-60.11c-18.75-18.75-49.16-18.75-67.91 0l-56.55 56.55 128.02 128.02 56.55-56.55c18.75-18.76 18.75-49.16 0-67.91z";
	public static final String TRASH_SOLID = "M432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16zM53.2 467a48 48 0 0 0 47.9 45h245.8a48 48 0 0 0 47.9-45L416 128H32z";

	FuncionarioController controller;
	Connection conexao;
	PrincipalView principalView;

	public ListarFuncionarioView(PrincipalView principalView) {
		try {
			this.principalView = principalView;
			conexao = ConexaoMySql.getInstance().getConnection();
			controller = new FuncionarioController(new FuncionarioDAO(conexao));
		} catch (Exception e) {
			Alerts.alertErro("Erro no FuncionarioDAO", "Erro na hora de instanciar uma conexao my sql", e.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			iniciarColunas();
			tableView.setItems(FXCollections.observableArrayList(controller.listarFuncionarios()));
		} catch (Exception e) {
			Alerts.alertErro("Erro ao listar", "Não foi possivel carregar dados do banco", e.getMessage());
		}
	}

	@FXML
	void cadFuncionarioAction(ActionEvent event) {
		try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("/views/CadFuncionario.fxml"));
			fxml.setController(new CadFuncionarioView(principalView));
			principalView.mudarPanePrincipal("Cadastrar Funcionario", fxml.load());
		} catch (Exception e) {
			e.printStackTrace();
			Alerts.alertErro("Erro ao mudar Pane", "Quando tentou trocar para o cad funcionario", e.getMessage()).showAndWait();
		}
	}
	
	private void recarregarLista() {
		tableView.setItems(FXCollections.observableArrayList(controller.listarFuncionarios()));
	}

	private void iniciarColunas() {
//		/cod,, nome,, doc, tel, cargo, acesso
		cCod.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Funcionario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Funcionario, String> param) {
						return new ReadOnlyStringWrapper(String.format(param.getValue().getCod() + ""));
					}
				});

		cDoc.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Funcionario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Funcionario, String> param) {
						return new ReadOnlyStringWrapper(
								String.format(Formatacao.formatarDocumento(param.getValue().getDocumento())));
					}
				});

		cNome.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Funcionario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Funcionario, String> param) {
						return new ReadOnlyStringWrapper(param.getValue().getNome());
					}
				});

		cCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));

		cTel.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Funcionario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Funcionario, String> param) {
						return new ReadOnlyStringWrapper(Formatacao.formatarTelefone(param.getValue().getTelefone()));
					}
				});

		cCargo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Funcionario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Funcionario, String> param) {
						return new ReadOnlyStringWrapper(param.getValue().getCargo().getNome());
					}
				});

		cAcesso.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Funcionario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Funcionario, String> param) {
						return new ReadOnlyStringWrapper(param.getValue().getCargo().getAcesso() + "");
					}
				});

		GenericTableButton.initButtons(cEditar, 15, PEN_SOLID, "svg-gray",
				(Funcionario Funcionario, ActionEvent event) -> {
					try {
						FXMLLoader fxml = new FXMLLoader(getClass().getResource("/views/EditarFuncionario.fxml"));
						fxml.setController(new EditarFuncionarioView(principalView, Funcionario));
						principalView.mudarPanePrincipal("Editar Funcionario", fxml.load());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		GenericTableButton.initButtons(cExcluir, 15, TRASH_SOLID, "svg-gray",
				(Funcionario funcionario, ActionEvent event) -> {
					try {
						int retorno = controller.deletarFuncionario(event, funcionario.getCod());
						recarregarLista();
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
	private TableView<Funcionario> tableView;

	@FXML
	private TableColumn<Funcionario, String> cCod;

	@FXML
	private TableColumn<Funcionario, String> cNome;

	@FXML
	private TableColumn<Funcionario, String> cDoc;

	@FXML
	private TableColumn<Funcionario, String> cTel;

	@FXML
	private TableColumn<Funcionario, String> cCargo;

	@FXML
	private TableColumn<Funcionario, String> cAcesso;

	@FXML
	private TableColumn<Funcionario, Funcionario> cEditar;

	@FXML
	private TableColumn<Funcionario, Funcionario> cExcluir;

	@FXML
	private JFXButton btnCad;

	@FXML
	void initialize() {
		assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";
		assert cCod != null : "fx:id=\"cCod\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";
		assert cNome != null : "fx:id=\"cNome\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";
		assert cDoc != null : "fx:id=\"cDoc\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";
		assert cTel != null : "fx:id=\"cTel\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";
		assert cCargo != null : "fx:id=\"cCargo\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";
		assert cAcesso != null : "fx:id=\"cAcesso\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";
		assert cEditar != null : "fx:id=\"cEditar\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";
		assert cExcluir != null : "fx:id=\"cExcluir\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";
		assert btnCad != null : "fx:id=\"btnCad\" was not injected: check your FXML file 'ListaFuncionario.fxml'.";

	}
}
