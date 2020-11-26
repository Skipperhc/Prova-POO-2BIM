package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Alerts;

public class Login extends Application {

	private static Stage primaryStage;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Login.primaryStage = primaryStage;
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			Alerts.alertAviso("Resumão professor", "Onde encontrar cada um dos pontos", ""
					+ "Herança - Produto no processador\n\n\n"
					+ "Agregação - Cargo no funcionario\n\n"
					+ "Baixo nivel de acomplamento - cada classe tem a sua responsabilidade\n\n"
					+ "Inversão de dependencia - Instancio o controller na view passando como\n parametro o DAO para o controlador(foi isso que entendi do seu audio)\n\n"
					+ "Injeção de dependencia - Todos as classes que precisam de algo, é obrigado a passar no construtor\n\n"
					+ "os generics estão na classe IPadraoDAO").showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
