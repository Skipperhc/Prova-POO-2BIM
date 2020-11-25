package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TrocarScene {
	public void trocar(ActionEvent event, String path) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource(path));
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();
		} catch (Exception e) {
			Alerts.alertErro("Erro ao mudar scene", "Erro ao trocar o scene para" + "path", "");
		}
	}
	
	public void trocar(ActionEvent event, String path, FXMLLoader fxml) {
		try {
			Scene scene = new Scene(fxml.load());
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.show();
		} catch (Exception e) {
			Alerts.alertErro("Erro ao mudar scene", "Erro ao trocar o scene para" + "path", "");
		}
	}
	
	
}
