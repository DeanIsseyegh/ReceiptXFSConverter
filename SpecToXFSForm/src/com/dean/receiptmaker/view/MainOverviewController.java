package com.dean.receiptmaker.view;

import java.awt.image.BufferedImage;
import java.time.LocalTime;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import com.dean.receiptmaker.ConsoleMessages;
import com.dean.receiptmaker.MainApp;

public class MainOverviewController {

	public static final int MAX_CONSOLE_LENGTH = 300;

	private MainApp mainApp;

	@FXML
	private ImageView imageView;

	@FXML
	private TextArea consoleId;
	
	@FXML 
	private Button cropGridButton, defineFieldsButton, convertButton;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		disableButtons();
		postToGUIConsole(ConsoleMessages.WELCOME);
	}

	public void disableButtons() {
		cropGridButton.setDisable(true);
		defineFieldsButton.setDisable(true);
		convertButton.setDisable(true);
	}
	
	@FXML
	public void onCropGridPress() {
		try {
		mainApp.cropAndGridImg(imageView.getImage());
		defineFieldsButton.setDisable(false);
		postToGUIConsole(ConsoleMessages.IMAGE_CROPPED, false);
		} catch (ArrayIndexOutOfBoundsException e) {
			postToGUIConsole(ConsoleMessages.COULD_NOT_GRID, true);
		}
	}

	@FXML
	public void onGUIEditorPress() {
		mainApp.showGUIEditor(imageView.getImage());
		convertButton.setDisable(false);
	}

	@FXML
	public void onConvert2XFS() {
		mainApp.convert2XFS();
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setReceiptImage(Image receiptImage) {
		imageView.setImage(receiptImage);
	}

	public void setReceiptImage(BufferedImage bufferedImg) {
		WritableImage image = SwingFXUtils.toFXImage(bufferedImg, (WritableImage) null);
		imageView.setImage(image);
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public TextArea getConsoleId() {
		return consoleId;
	}

	public void postToGUIConsole(String msg, boolean isWarning) {
		if (isWarning) {
			consoleId.setStyle("-fx-text-fill: red;");
		} else {
			consoleId.setStyle("-fx-text-fill: black;");
		}
		postToGUIConsole(msg);
	}

	public void postToGUIConsole(String msg) {
		if (msg.length() > MAX_CONSOLE_LENGTH) {
			consoleId.clear();
		}
		String currentTimeWithMilli = LocalTime.now().toString();
		String currentTimeNoMilli = currentTimeWithMilli.substring(0, currentTimeWithMilli.length() - 4);
		consoleId.appendText(currentTimeNoMilli + " : " + msg + "\n");
		consoleId.appendText(ConsoleMessages.CONSOLE_LINEBREAK);
	}

	public void alertAndClose(String title, String headerText, String contentText) {
		alert(title, headerText, contentText);
		mainApp.getPrimaryStage().close();
	}
	
	public void alert(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		if (title != null) {
			alert.setTitle(title);
		}
		if (title != null) {
			alert.setHeaderText(headerText);
		}
		if (title != null) {
			alert.setContentText(contentText);
		}
		alert.showAndWait();
	}

	public void enableCropButton() {
		cropGridButton.setDisable(false);
	}

}
