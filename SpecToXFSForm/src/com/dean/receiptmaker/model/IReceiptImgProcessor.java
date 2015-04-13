package com.dean.receiptmaker.model;

import java.awt.image.BufferedImage;
import java.util.List;

public interface IReceiptImgProcessor {

	/**
	 * Should cut the image down to its first dark border
	 */
	BufferedImage cropInitialBorder(BufferedImage image);
	
	/**
	 * Should draw grids onto the image for each ROWCOLUMNX and
	 * ROWCOLUMNY and return a list of these grids with pixel info.
	 */
	Grid drawGrids(BufferedImage image);

	/**
	 * Pass in the correct parameters and it should return a collection
	 * of all the Grids (which contains pixel information).
	 * 
	 * @param image
	 * @param gridWidth
	 * @param gridHeight
	 * @return grids
	 */
	List<Grid> initGridsPixelsCollection(BufferedImage image, double gridWidth,
			double gridHeight);

	/**
	 * Tells you what Grid the passedin coordindates(X,Y) were in.
	 * 
	 * @param grids
	 * @param x
	 * @param y
	 * @return Grid
	 */
	Grid coordinates2Grid(List<Grid> grids, int x, int y);
	
	/**
	 * Draw a red circle near the center of the Grid that coordinates are in.
	 * 
	 * @param image
	 * @param grids
	 * @param posX
	 * @param posY
	 * @return didCoordinatesMapOntoGrid
	 */
	boolean drawRedMarkOnGrid(BufferedImage image, List<Grid> grids, int posX, int posY);
	
	
}
