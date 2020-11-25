package utils;

import java.util.function.BiConsumer;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;

public class GenericTableButton {

	public static <T> void initButtons(TableColumn<T, T> tableColumn, int size, String svgIcon, String colorClassName,
			BiConsumer<T, ActionEvent> buttonAction) {
		final int COLUMN_ICON_SPACE = 20; 
		tableColumn.setMinWidth(size + COLUMN_ICON_SPACE);
		tableColumn.setMaxWidth(size + COLUMN_ICON_SPACE);
		tableColumn.setStyle("-fx-alignment: CENTER"); 

		Callback<TableColumn<T, T>, TableCell<T, T>> cellFactory = new Callback<TableColumn<T, T>, TableCell<T, T>>() {
			@Override
			public TableCell<T, T> call(final TableColumn<T, T> param) {
				final TableCell<T, T> cell = new TableCell<T, T>() {
					private final Button btn = createIconButton(svgIcon, size, colorClassName);

					@Override
					public void updateItem(T item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}

					{
						btn.setOnAction((ActionEvent event) -> {
							T data = getTableView().getItems().get(getIndex());
							buttonAction.accept(data, event);
						});
					}
				};
				return cell;
			}
		};
		tableColumn.setCellFactory(cellFactory);
	}

	public static Button createIconButton(String svgAbsolutePath, int size, String colorClassName) {
		SVGPath path = new SVGPath();
		path.setContent(svgAbsolutePath);
		Bounds bounds = path.getBoundsInLocal();

		double scaleFactor = size / Math.max(bounds.getWidth(), bounds.getHeight());
		path.setScaleX(scaleFactor);
		path.setScaleY(scaleFactor);
		path.getStyleClass().add(colorClassName);

		Button button = new Button();
		button.setPickOnBounds(true);
		button.setGraphic(path);
		button.setAlignment(Pos.CENTER);
		button.getStyleClass().add("icon-button");
		button.setMaxWidth(size);
		button.setMaxHeight(size);
		button.setMinWidth(size);
		button.setMinHeight(size);
		button.setPrefWidth(size);
		button.setPrefHeight(size);
		return button;
	}

}
