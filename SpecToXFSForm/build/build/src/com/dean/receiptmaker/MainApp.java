package com.dean.receiptmaker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.dean.receiptmaker.eventhandlers.GUIEditorEventHandler;
import com.dean.receiptmaker.model.FieldValues;
import com.dean.receiptmaker.model.Grid;
import com.dean.receiptmaker.model.IReceiptImgProcessor;
import com.dean.receiptmaker.model.ISettings;
import com.dean.receiptmaker.model.ReceiptImgProcessor;
import com.dean.receiptmaker.model.SettingsWrapper;
import com.dean.receiptmaker.model.XFSField;
import com.dean.receiptmaker.model.XFSForm;
import com.dean.receiptmaker.view.GUIEditorController;
import com.dean.receiptmaker.view.MainOverviewController;
import com.dean.receiptmaker.view.RootLayoutController;

public class MainApp extends Application {

	public static ISettings SETTINGS;
	public static final String PRIMARY_STAGE_TITLE = "Receipt Specs to Form Converter";
	public static final String MAIN_OVERVIEW_FXML = "view/MainOverview.fxml";
	public static final String ROOT_LAYOUT_FXML = "view/RootLayout.fxml";
	public static final String GUI_EDITOR_FXML = "view/GUIEditor.fxml";
	public static final String GUIDE_FXML = "view/Guide.fxml";
	
	private Stage primaryStage;
	private Stage secondaryStage;
	private BorderPane rootLayout;
	private MainOverviewController mainOverviewController;
	private GUIEditorController guiEditorController;
	private IReceiptImgProcessor receiptImgProcessor;
	private Grid grid;
	private List<FieldValues> fieldNames = new ArrayList<>();
	private List<FieldValues> oldFieldNames = new ArrayList<>();
	private Image oldReceiptImage;
	private GUIEditorEventHandler guiEditorEventHandler;

	public Grid getGrid() {
		return grid;
	}

	public MainApp() {
		this.receiptImgProcessor = new ReceiptImgProcessor();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(PRIMARY_STAGE_TITLE);
		loadSettings();
		initRootLayout();
		showMainOverview();
	}
	
	public void addIconImageToStage(Stage stage) {
		Image icon = new Image(FileLocations.APP_ICON);
		stage.getIcons().add(icon);
	}

	public void loadSettings() {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(SettingsWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			File file = new File(FileLocations.SETTINGS_LOCATION);
			SETTINGS = (ISettings) um.unmarshal(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(ROOT_LAYOUT_FXML));
			rootLayout = (BorderPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			addIconImageToStage(primaryStage);
			primaryStage.show();
			RootLayoutController rootController = loader.getController();
			rootController.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showHelpGuide() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(GUIDE_FXML));
			AnchorPane guide = (AnchorPane) loader.load();
			Scene scene = new Scene(guide);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showGUIEditor(Image image) {
		try {
			// Save the state of fieldNames/Image before we open the GUI editor
			oldFieldNames.addAll(fieldNames);
			oldReceiptImage = mainOverviewController.getImageView().getImage();
			
			//Load view
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(GUI_EDITOR_FXML));
			AnchorPane guiEdtior = (AnchorPane) loader.load();
			Scene scene = new Scene(guiEdtior);
			scene.setCursor(Cursor.CROSSHAIR);
			secondaryStage = new Stage();
			secondaryStage.setScene(scene);
			addIconImageToStage(secondaryStage);
			// tell stage it is meant to pop-up (Modal)
			secondaryStage.initModality(Modality.APPLICATION_MODAL);
			secondaryStage.setTitle("GUI Editor");
			secondaryStage.show();
			secondaryStage.setResizable(false);
			guiEditorController = loader.getController();
			guiEditorController.setMainApp(this);
			guiEditorController.setImage(image);
			
			//Set event handler
			guiEditorEventHandler = initGuiEditorEventHandler();
			secondaryStage.addEventHandler(MouseEvent.MOUSE_CLICKED, guiEditorEventHandler);
			secondaryStage.setOnHiding(value -> {
				if (getFieldNames() != null || getFieldNames().size() > 0) {
					getMainOverviewController().postToGUIConsole(ConsoleMessages.getDefinedXFieldsMsg(getFieldNames().size()));
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			mainOverviewController.postToGUIConsole(e.getMessage(), true);
		}
	}

	public GUIEditorEventHandler initGuiEditorEventHandler() {
		GUIEditorEventHandler eventHandler;
		if (guiEditorEventHandler == null) {
			eventHandler = new GUIEditorEventHandler(this, guiEditorController);
		} else {
			eventHandler = guiEditorEventHandler;
			eventHandler.setMainApp(this);
			eventHandler.setGuiEditorController(guiEditorController);
		}
		return eventHandler;
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showMainOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(MAIN_OVERVIEW_FXML));
			AnchorPane mainOverview = (AnchorPane) loader.load();
			// Set person overview into the center of root layout.
			rootLayout.setCenter(mainOverview);
			mainOverviewController = loader.getController();
			mainOverviewController.setMainApp(this);
			Image noReceiptSplash = new Image(FileLocations.NO_RECEIPT_SPLASH_IMG);
			mainOverviewController.setReceiptImage(noReceiptSplash);
		} catch (IOException e) {
			e.printStackTrace();
			mainOverviewController.postToGUIConsole(e.getMessage(), true);
		}
	}

	public void cropAndGridImg(Image image) {
		BufferedImage bufferedImg = SwingFXUtils.fromFXImage(image, (BufferedImage) null);
		try {
			BufferedImage croppedImg = receiptImgProcessor.cropInitialBorder(bufferedImg);
			this.grid = receiptImgProcessor.drawGrids(croppedImg);
			mainOverviewController.setReceiptImage(croppedImg);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw e;
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public MainOverviewController getMainOverviewController() {
		return mainOverviewController;
	}

	public IReceiptImgProcessor getReceiptImgProcessor() {
		return receiptImgProcessor;
	}

	public List<FieldValues> getFieldNames() {
		if (fieldNames == null) {
			fieldNames = new ArrayList<>();
		}
		return fieldNames;
	}

	public void setFieldNames(List<FieldValues> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public void convert2XFS() {
		int fieldCounter = 0;
		List<XFSField> fields = new ArrayList<XFSField>();
		BufferedImage image = SwingFXUtils.fromFXImage(getMainOverviewController().getImageView().getImage(),
				(BufferedImage) null);
		List<Grid> grids = receiptImgProcessor.initGridsPixelsCollection(image, grid.getWidth(), grid.getHeight());
		Iterator<Grid> iterator = grids.iterator();

		while (iterator.hasNext()) {
			Grid grid1 = iterator.next();
			if (grid1.doesContainRedPixel()) {
				XFSField field = new XFSField(SETTINGS);
				FieldValues fieldValue = null;
				try {
					fieldValue = fieldNames.get(fieldCounter);
				} catch (IndexOutOfBoundsException ex) {
					mainOverviewController.alertAndClose(DialogMessages.ALERT_ERROR_TITLE, 
							DialogMessages.ALERT_OOPS_HEADER,
							DialogMessages.ALERT_XFS_ERROR_MSG);
				}
				field.setFieldName(fieldValue.getFieldName());
				field.setHorizontalJustify(fieldValue.getHorizontalJustify());
				field.setInitialValue(fieldValue.getInitialValue());
				field.setPosX(grid1.getRowcolumnX());
				field.setPosY(grid1.getRowcolumnY());

				while (iterator.hasNext()) {
					Grid grid2 = iterator.next();
					if (grid2.doesContainRedPixel()) {
						field.setLength(grid2.getRowcolumnX() - grid1.getRowcolumnX());
						fields.add(field);
						if (field.getLength() < field.getInitialValue().length()) {
							String warningMessage = ConsoleMessages.CONVERT_2_XFS_WRN + field;
							mainOverviewController.postToGUIConsole(warningMessage, true);
						}
						fieldCounter++;
						break;
					}
				}
			}
		}
		XFSForm xfsForm = new XFSForm(fields, SETTINGS);
		try {
			File file = xfsForm.writeToFile();
			mainOverviewController.postToGUIConsole(ConsoleMessages.FILE_SAVED_TO + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			mainOverviewController.postToGUIConsole(e.getMessage(), true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			mainOverviewController.postToGUIConsole(e.getMessage(), true);
		}
	}

	public Stage getSecondaryStage() {
		return secondaryStage;
	}

	public Image getPreGUIEditorImg() {
		return oldReceiptImage;
	}

	public void setGuiEditorEventHandler(GUIEditorEventHandler guiEditorEventHandler) {
		this.guiEditorEventHandler = guiEditorEventHandler;
	}

	public GUIEditorController getGuiEditorController() {
		return guiEditorController;
	}

	public EventHandler<MouseEvent> getGuiEditorEventHandler() {
		return guiEditorEventHandler;
	}

	public void revertFieldNames() {
		fieldNames.clear();
		fieldNames.addAll(oldFieldNames);
	}

	public void resetSession() {
		guiEditorEventHandler = null;
		fieldNames = new ArrayList<>();
		oldFieldNames = new ArrayList<>();
		grid = null;
		oldReceiptImage = null;
		getMainOverviewController().disableButtons();
	}
}
