package com.dean.receiptmaker.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.dean.receiptmaker.ConsoleMessages;
import com.dean.receiptmaker.DialogMessages;
import com.dean.receiptmaker.FileLocations;
import com.dean.receiptmaker.MainApp;
import com.dean.receiptmaker.model.SettingsWrapper;

public class RootLayoutController {

	private MainApp mainApp;
	/**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"Pictures (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        extFilter = new FileChooser.ExtensionFilter("Pictures (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			mainApp.getMainOverviewController().setReceiptImage(image);
			mainApp.getMainOverviewController().disableButtons();
			mainApp.getMainOverviewController().enableCropButton();
			mainApp.getMainOverviewController().postToGUIConsole(ConsoleMessages.IMAGE_LOADED, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    @FXML
    private void handleSave() {    	  
        //Set extension filter
    	FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Jpg files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        
        if(file != null){
            saveFile(SwingFXUtils.fromFXImage(mainApp.getMainOverviewController().getImageView().getImage(), 
            		(BufferedImage) null), file);
        }
    }
    
    private void saveFile(BufferedImage img, File file){
        try {
        	ImageIO.write(img, "png", file);
        	mainApp.getMainOverviewController().postToGUIConsole(ConsoleMessages.FILE_SAVED_TO + file.getAbsolutePath(), false);
        } catch (IOException ex) {
        	ex.printStackTrace();
            mainApp.getMainOverviewController().postToGUIConsole(ex.getMessage(), true);
        }
         
    }
    
    @FXML
    private void handleExit() {
    	System.exit(1);
    }
    
    
    @FXML
    private void handleAbout() {
    	Dialog<String> dialog = new Dialog<String>();
    	dialog.setTitle(DialogMessages.ABOUT_TITLE);
    	dialog.setContentText(DialogMessages.ABOUT_MSG);
    	dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
    	dialog.showAndWait();
    	dialog.hide();
    }

    @FXML
    private void handleSettings() {
		// Create the custom dialog.
		Dialog<Map<ResultKey, String>> dialog = new Dialog<>();
		dialog.setTitle(DialogMessages.SETTINGS_TITLE);
		dialog.setHeaderText(DialogMessages.SETTINGS_HEADER);
		dialog.setContentText(DialogMessages.SETTINGS_MSG);

		// Set the button types.
		ButtonType saveButton = new ButtonType(DialogMessages.BTN_SAVE_LABEL, ButtonData.OK_DONE);
		ButtonType revertButton = new ButtonType(DialogMessages.BTN_REVERT_LABEL, ButtonData.OTHER);
		dialog.getDialogPane().getButtonTypes().addAll(saveButton, revertButton, ButtonType.CANCEL);

		// Create the XFSField label.
		GridPane grid = new GridPane();
		grid.setHgap(30);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		//FormName
		TextField formName = new TextField(MainApp.SETTINGS.getFormName());
		
		// UnitType
		final ChoiceBox<String> unitType = new ChoiceBox<String>(FXCollections.observableArrayList(
	            "MM", "ROWCOLUMN")
	        );
		unitType.setValue(MainApp.SETTINGS.getUnit());
		
		//Unit Header
		TextField unitHeader = new TextField(MainApp.SETTINGS.getUnitHeader());
		
		//formSize
		TextField formSize = new TextField(MainApp.SETTINGS.getFormSize());

		String[] zeroTo99 = new String[100];
		for (int i = 0; i < 100; i++) {
			zeroTo99[i] = "" + i;
		}
		//xModifier
		final ChoiceBox<String> xModifier = new ChoiceBox<String>(FXCollections.observableArrayList(
				zeroTo99)
	        );
		xModifier.setValue(String.valueOf(MainApp.SETTINGS.getxModifier()));
		
		//yModifier
		final ChoiceBox<String> yModifier = new ChoiceBox<String>(FXCollections.observableArrayList(
				zeroTo99)
	        );
		yModifier.setValue(String.valueOf(MainApp.SETTINGS.getyModifier()));
		
		//Font
		TextField font = new TextField(MainApp.SETTINGS.getFont());
		
		//CPI
		final ChoiceBox<String> cpi = new ChoiceBox<String>(FXCollections.observableArrayList(
				zeroTo99)
	        );
		cpi.setValue(String.valueOf(MainApp.SETTINGS.getCpi()));
		
		int gridPosY = 0;
		// Add labels/fields/choiceboxes to dialog/grid
		grid.add(new Label("Form Name:"), 0, gridPosY);
		grid.add(formName, 1, gridPosY++);
		
		grid.add(new Label("Unit Type:"), 0, gridPosY);
		grid.add(unitType, 1, gridPosY++);
		
		grid.add(new Label("Unit Header:"), 0, gridPosY);
		grid.add(unitHeader, 1, gridPosY++);
		
		grid.add(new Label("Form Size:"), 0, gridPosY);
		grid.add(formSize, 1, gridPosY++);
		
		grid.add(new Label("X Modifier:"), 0, gridPosY);
		grid.add(xModifier, 1, gridPosY++);
		
		grid.add(new Label("Y Modifier:"), 0, gridPosY);
		grid.add(yModifier, 1, gridPosY++);
		
		grid.add(new Label("Font:"), 0, gridPosY);
		grid.add(font, 1, gridPosY++);
		
		grid.add(new Label("CPI:"), 0, gridPosY);
		grid.add(cpi, 1, gridPosY++);

		dialog.getDialogPane().setContent(grid);

		// Convert the result to an array of Strings when the ok button is pressed
		dialog.setResultConverter(dialogButton -> {
			Map<ResultKey, String> results = new HashMap<>();
			if (dialogButton.getText() == "Save") {
				results = new HashMap<>();
				results.put(ResultKey.FORM_NAME, formName.getText());
				results.put(ResultKey.UNIT_TYPE, unitType.getValue());
				results.put(ResultKey.UNIT_HEADER, unitHeader.getText());
				results.put(ResultKey.FORM_SIZE, formSize.getText());
				results.put(ResultKey.X_MODIFIER, xModifier.getValue());
				results.put(ResultKey.Y_MODIFIER, yModifier.getValue());
				results.put(ResultKey.FONT, font.getText());
				results.put(ResultKey.CPI, cpi.getValue());
				return results;
			} else if (dialogButton.getText().equalsIgnoreCase(DialogMessages.BTN_REVERT_LABEL)){
				results.put(ResultKey.REVERT_DEFAULT, "true");
				return results;
			}
			return null;
		});

		//Show the dialog and wait for the result
		Optional<Map<ResultKey, String>> result = dialog.showAndWait();
		if (result.isPresent()) {
			Map<ResultKey, String> results = result.get();
			if ("true".equalsIgnoreCase(results.get(ResultKey.REVERT_DEFAULT))) {
				revertSettingsToDefault();
			} else if (validateInput(results)) {
				saveSettings(results);
			} else {
				showSettingsNotSavedMsg();
			}
		} 
    }
    
    private void revertSettingsToDefault() {
		File defaultSettings = new File(FileLocations.DEFAULT_SETTINGS_LOCATION);
		File settingsToBeReplaced = new File(FileLocations.SETTINGS_LOCATION);
    	try {
			Files.copy(defaultSettings.toPath(), settingsToBeReplaced.toPath(), StandardCopyOption.REPLACE_EXISTING);
			mainApp.getMainOverviewController().postToGUIConsole(ConsoleMessages.SETTINGS_REVERTED);
		} catch (IOException e) {
			e.printStackTrace();
			showSettingsNotSavedMsg();
		}
    }
    
    private void saveSettings(Map<ResultKey, String> results) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(SettingsWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        SettingsWrapper wrapper = new SettingsWrapper();
	        wrapper.setFormName(results.get(ResultKey.FORM_NAME));
	        wrapper.setUnit(results.get(ResultKey.UNIT_TYPE));
	        wrapper.setUnitHeader(results.get(ResultKey.UNIT_HEADER));
	        wrapper.setFormSize(results.get(ResultKey.FORM_SIZE));
	        wrapper.setxModifier(Integer.parseInt(results.get(ResultKey.X_MODIFIER)));
	        wrapper.setyModifier(Integer.parseInt(results.get(ResultKey.Y_MODIFIER)));
	        wrapper.setFont(results.get(ResultKey.FONT));
	        wrapper.setCpi(results.get(ResultKey.CPI));

	        // Marshalling and saving XML to the file.
	        File file = new File(FileLocations.SETTINGS_LOCATION);
	        m.marshal(wrapper, file);
	        mainApp.loadSettings();
	        mainApp.getMainOverviewController().postToGUIConsole(ConsoleMessages.SETTINGS_CHANGED);
	    } catch (Exception e) { 
	    	e.printStackTrace();
	    	showSettingsNotSavedMsg();
	    }
    }
    
    private void showSettingsNotSavedMsg() {
    	mainApp.getMainOverviewController().alert(DialogMessages.ALERT_OOPS_TITLE, 
				DialogMessages.ALERT_SETTINGS_NOT_SAVED_HEADER, 
				DialogMessages.ALERT_SETTINGS_NOT_SAVED_MSG);
		mainApp.getMainOverviewController().postToGUIConsole(ConsoleMessages.SETTINGS_NOT_SAVED_INVALID);
    }
    
    private boolean validateInput(Map<ResultKey, String> input) {
    	String formName = input.get(ResultKey.FORM_NAME);
    	String unitType = input.get(ResultKey.UNIT_TYPE);
    	String unitHeader = input.get(ResultKey.UNIT_HEADER);
    	String formSize = input.get(ResultKey.FORM_SIZE);
    	String xModifier = input.get(ResultKey.X_MODIFIER);
    	String yModifier = input.get(ResultKey.Y_MODIFIER);
    	String font = input.get(ResultKey.FONT);
    	String cpi = input.get(ResultKey.CPI);
    	
    	try {
    		if (formName.length() < 1) return false;
	    	if (unitType.length() < 1) return false;
	    	if (unitHeader.length() < 5) return false;
	    	if (formSize.length() < 3) return false;
	    	if (!isNumber(xModifier)) return false;
	    	if (!isNumber(yModifier)) return false;
	    	if (font.length() < 1) return false;
	    	if (!isNumber(cpi)) return false;
    	} catch (NullPointerException e) {
    		return false;
    	}
    	return true;
    }
    
    private boolean isNumber(String str) {
    	try {
    		Integer.parseInt(str);
    		return true;
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    @FXML
    private void handleGuide() {
    	mainApp.showHelpGuide();
    }
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
    
    public enum ResultKey {
    	FORM_NAME,
    	UNIT_TYPE,
    	UNIT_HEADER,
    	FORM_SIZE,
    	X_MODIFIER,
    	Y_MODIFIER,
    	FONT,
    	CPI, 
    	REVERT_DEFAULT
    }
}
