package com.dean.receiptmaker.view;

import java.awt.image.BufferedImage;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import com.dean.receiptmaker.DialogMessages;
import com.dean.receiptmaker.MainApp;
import com.dean.receiptmaker.eventhandlers.GUIEditorEventHandler;
import com.dean.receiptmaker.model.FieldValues;

public class GUIEditorController {

	private MainApp mainApp;

	@FXML
	private ImageView imageView;

	public void setImage(Image image) {
		imageView.setImage(image);
		imageView.setFitHeight(0);
		imageView.setFitWidth(0);
		mainApp.getSecondaryStage().setHeight(image.getHeight());
		mainApp.getSecondaryStage().setWidth(image.getWidth());
	}

	public void setImage(BufferedImage image) {
		Image fxImage = SwingFXUtils.toFXImage(image, (WritableImage) null);
		setImage(fxImage);
	}

	public Image getImage() {
		return imageView.getImage();
	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	public void onDonePress() {
		mainApp.getSecondaryStage().close();

	}

	@FXML
	public void onClearChangesPress() {
		clear();
	}

	public void clear() {
		imageView.setImage(mainApp.getPreGUIEditorImg());
		mainApp.getMainOverviewController().setReceiptImage(mainApp.getPreGUIEditorImg());
		mainApp.revertFieldNames();
		mainApp.getSecondaryStage().removeEventHandler(MouseEvent.MOUSE_CLICKED, mainApp.getGuiEditorEventHandler());
		GUIEditorEventHandler eventHandler = new GUIEditorEventHandler(mainApp, this);
		mainApp.setGuiEditorEventHandler(eventHandler);
		mainApp.getSecondaryStage().addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		mainApp.getSecondaryStage().close();
	}

	public boolean showFieldInputDialog(int fieldNumber) {
		// Create the custom dialog.
		Dialog<String[]> dialog = new Dialog<>();
		dialog.setTitle("Field Name Input");
		dialog.setHeaderText("Field" + fieldNumber);
		dialog.setContentText("Please enter desired XFS field name:");

		// Set the button types.
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		// Create the XFSField label.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField xfsFieldName = new TextField();
		xfsFieldName.requestFocus();
		xfsFieldName.setPromptText("(Mandatory) Field" + fieldNumber);
		final ChoiceBox<String> justificationCb = new ChoiceBox<String>(FXCollections.observableArrayList(
	            "Left", "Right", "Center")
	        );
		justificationCb.setValue("Left");
		TextField initialValue = new TextField();
		initialValue.setPromptText("(Optional) Default Value");

		grid.add(new Label("XFS Field Name:"), 0, 0);
		grid.add(xfsFieldName, 1, 0);
		grid.add(new Label("Initial Value:"), 0, 1);
		grid.add(initialValue, 1, 1);
		grid.add(new Label("Justification:"), 0, 2);
		grid.add(justificationCb, 1, 2);
		

		// Enable/Disable login button depending on whether a username was
		// entered.
		Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		xfsFieldName.textProperty().addListener((observable, oldValue, newValue) -> {
			okButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		Platform.runLater(() -> xfsFieldName.requestFocus());
		
		// Convert the result to an array of Strings when the ok button is pressed
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				return new String[]{xfsFieldName.getText(), justificationCb.getValue(), initialValue.getText()};
			}
			return null;
		});

		//Show the dialog and wait for the result
		Optional<String[]> result = dialog.showAndWait();

		boolean didUserClickCancel = false;
		if (result.isPresent()) {
			String[] resultArr = result.get();
			FieldValues fieldValues = new FieldValues(resultArr[0], resultArr[1], resultArr[2]);
			mainApp.getFieldNames().add(fieldValues);
			didUserClickCancel = false;
		} else {
			didUserClickCancel = true;
		}
		return didUserClickCancel;
	}

	public void showInvalidClickYAlert() {
		showInvalidGridAlert(DialogMessages.ALERT_INVALID_GRID_Y_MSG);
	}

	public void showInvalidClickXAlert() {
		showInvalidGridAlert(DialogMessages.ALERT_INVALID_GRID_X_MSG);
	}

	public void showInvalidGridAlert(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(DialogMessages.ALERT_OOPS_TITLE);
		alert.setHeaderText(DialogMessages.ALERT_INVALID_GRID_HEADER);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
