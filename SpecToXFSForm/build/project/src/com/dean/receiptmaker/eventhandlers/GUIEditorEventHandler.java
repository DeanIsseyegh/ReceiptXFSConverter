package com.dean.receiptmaker.eventhandlers;

import java.awt.image.BufferedImage;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import com.dean.receiptmaker.DialogMessages;
import com.dean.receiptmaker.MainApp;
import com.dean.receiptmaker.model.Grid;
import com.dean.receiptmaker.model.IReceiptImgProcessor;
import com.dean.receiptmaker.view.GUIEditorController;

/**
 * Responsibly for keep state of red dots on the GUI editor
 * 
 * @author Dean
 *
 */
public class GUIEditorEventHandler implements EventHandler<MouseEvent> {

	private MainApp mainApp;
	private GUIEditorController guiEditorController;
	private int redDotCounter = 0;
	private Grid lastGridClicked;
	private BufferedImage lastEvenRedDotsImage;
	
	public GUIEditorEventHandler(MainApp mainApp, GUIEditorController guiController) {
		this.mainApp = mainApp;
		this.guiEditorController = guiController;
		lastEvenRedDotsImage = SwingFXUtils.fromFXImage(
				mainApp.getMainOverviewController().getImageView().getImage(), (BufferedImage) null);
	}

	@Override
	public void handle(MouseEvent event) {
		int posX = (int) event.getX();
		int posY = (int) event.getY();
		
		IReceiptImgProcessor imgProcessor = mainApp.getReceiptImgProcessor();
		Image image = mainApp.getMainOverviewController().getImageView().getImage();
		BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, (BufferedImage) null);
		List<Grid> grids = imgProcessor.initGridsPixelsCollection(bufferedImage, 
				mainApp.getGrid().getWidth(), mainApp.getGrid().getHeight());
		
		if (imgProcessor.drawRedMarkOnGrid(bufferedImage, grids, posX, posY)) {
			Grid gridClicked = imgProcessor.coordinates2Grid(grids, posX, posY);
			if (isWrongRowcolumnY(gridClicked)) {
				guiEditorController.showInvalidClickYAlert();
			} else if (isWrongRowcolumnX(gridClicked)) {
				guiEditorController.showInvalidClickXAlert();
			} else {
				++redDotCounter;
				boolean didUserClickCancel = false;
				guiEditorController.setImage(bufferedImage);
				if (redDotCounter % 2 == 0) {
					didUserClickCancel = guiEditorController.showFieldInputDialog(mainApp.getFieldNames().size() + 1);
					if (!didUserClickCancel) lastEvenRedDotsImage = bufferedImage;
				}
				// If user pressed cancel lets revert the state of the image/guieditor
				// to before the user drew the latest red dot.
				if (didUserClickCancel) {
					guiEditorController.setImage(lastEvenRedDotsImage);
					mainApp.getMainOverviewController().setReceiptImage(lastEvenRedDotsImage);
					--redDotCounter;
					--redDotCounter;
				} else {
					guiEditorController.setImage(bufferedImage);
					mainApp.getMainOverviewController().setReceiptImage(bufferedImage);
					lastGridClicked = gridClicked;
				}
			}
		} else {
			guiEditorController.showInvalidGridAlert(DialogMessages.ALERT_INVALID_GRID_STD_MSG);
		}
	}
	
	/**
	 * Checks if if the user is trying to place a red dot in an invalid location. There always has to be
	 * an "opening" red dot followed by a "closing" red dot on the same row (y axis).
	 * @return
	 */
	private boolean isWrongRowcolumnY(Grid gridClicked) {
		if (gridClicked != null &&
				redDotCounter != 0 && 
				redDotCounter % 2 != 0 &&
				!gridClicked.equals(lastGridClicked) &&
				gridClicked.getRowcolumnY() != lastGridClicked.getRowcolumnY()) {
			return true;
		} else {			
			return false;
		}
	}
	
	private boolean isWrongRowcolumnX(Grid gridClicked) {
		if (gridClicked != null &&
				redDotCounter != 0 &&
				redDotCounter % 2 != 0 &&
				!gridClicked.equals(lastGridClicked) &&
				gridClicked.getRowcolumnX() < lastGridClicked.getRowcolumnX()) {
			return true;
		} else {			
			return false;
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setGuiEditorController(GUIEditorController guiEditorController) {
		this.guiEditorController = guiEditorController;
	}
	
}
