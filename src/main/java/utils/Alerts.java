package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	
	public static Alert alertAtencao(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		return alert;
	}
	
	public static Alert alertAtencao(String title, String headerText, String contentText, Double height, Double width) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.setContentText(contentText);
		alert.setWidth(width);
		return alert;
	}
	
	public static Alert alertConfirmacao(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		return alert;
	}
	
	public static Alert alertConfirmacao(String title, String headerText, String contentText, Double height, Double width) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		if(height != null) alert.setContentText(contentText);
		if(width != null) alert.setWidth(width);
		return alert;
	}
	
	public static Alert alertAviso(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		return alert;
	}
	
	public static Alert alertAviso(String title, String headerText, String contentText, Double height, Double width) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		if(height != null) alert.setContentText(contentText);
		if(width != null) alert.setWidth(width);
		return alert;
	}
	
	public static Alert alertErro(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		return alert;
	}
	
	public static Alert alertErro(String title, String headerText, String contentText, Double height, Double width) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		if(height != null) alert.setContentText(contentText);
		if(width != null) alert.setWidth(width);
		return alert;
	}
	
}
