package com.dean.receiptmaker.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ReceiptImgProcessor implements IReceiptImgProcessor {
	
	public static int NUM_OF_COLUMNS = 40;
	public static int NUM_OF_ROWS = 21;

	@Override
	public BufferedImage cropInitialBorder(BufferedImage image) {
		Coordinate topLeft = null;
		Coordinate topRight = null;
		Coordinate bottomLeft = null;
		
		List<Coordinate> darkPixelCoordinates = new ArrayList<Coordinate>();
		// Find 3 corners of border to identify it
	    for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {   
                  Pixel pixel = new Pixel(image.getRGB(x, y));
                  try {
	                  if (pixel.isDark() && isPixelPartOfBorder(image, x, y)) {
	                	  Coordinate currentCoordinate = new Coordinate(x, y);
	                	  darkPixelCoordinates.add(currentCoordinate);
	                	  // Find top left black pixel that is part of a border
	                	  if (currentCoordinate.isMostLeft(darkPixelCoordinates) &&
	                			  currentCoordinate.isHighest(darkPixelCoordinates)) {
	                		  topLeft = currentCoordinate;                 		  
	                	  }
	                	  
	                	  // Find top right black pixel that is part of a border
	                	  if (!currentCoordinate.isMostLeft(darkPixelCoordinates) &&
	                			  currentCoordinate.isHighest(darkPixelCoordinates)) {
	                		  topRight = currentCoordinate;                 		  
	                	  }
	                	  
	                	  // Find bottom left black pixel that is part of a border
	                	  if (currentCoordinate.isMostLeft(darkPixelCoordinates) &&
	                			  !currentCoordinate.isHighest(darkPixelCoordinates)) {
	                		  bottomLeft = currentCoordinate;                 		  
	                	  }
	
	                  }
                  } catch (ArrayIndexOutOfBoundsException e) {
                	  throw e;
                  }
            }
	    }
	    
	    double width = topRight.getX() - topLeft.getX();
	    double height = bottomLeft.getY() - topLeft.getY();
	    Rectangle rect = new Rectangle((int) topLeft.getX(), (int) topLeft.getY(), (int) width, (int) height);
	    BufferedImage croppedImg = cropImage(image, rect);
	    return croppedImg;
	}

	@Override
	public Grid drawGrids(BufferedImage image) {
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.GRAY);
		Line2D line = new Line2D.Double();
		
		// Start drawing grid for ROWCOLUMNX at 18% width until 96% width of image. There are 40 ROWS.
		double startXPos = (int) Math.round(image.getWidth() * 0.18);
		double endXPos = (int) Math.round(image.getWidth() * 0.96);
		double gridWidth = (endXPos - startXPos) / NUM_OF_COLUMNS;
		
		double tmpPos = startXPos;
		for (int i = 0; i < NUM_OF_COLUMNS + 1; i++) {
			if (i !=0) {
				tmpPos += gridWidth;
			}
			line.setLine(tmpPos, 0, tmpPos, image.getHeight());
			g2d.draw(line);
		}
		
		// Start drawing grid for ROWCOLUMNY at 13.1% until 99.76% of image. There are 21 columns.
		double startYPos = (int) Math.round(image.getHeight() * 0.131);
		double endYPos = (int) Math.round(image.getHeight() * 0.9976);
		double gridHeight = (endYPos - startYPos) / NUM_OF_ROWS;
	
		tmpPos = startYPos;
		for (int i = 0; i < NUM_OF_ROWS + 1; i++) {
			if (i !=0) {
				tmpPos += gridHeight;
			}
			line.setLine(0, tmpPos, image.getHeight(), tmpPos);
			g2d.draw(line);
		}
		g2d.dispose();
		
		Grid grid = new Grid(gridWidth, gridHeight);
		return grid;
	}
	
	@Override
	public List<Grid> initGridsPixelsCollection(BufferedImage image, double gridWidth, double gridHeight) {
		List<Grid> grids = new ArrayList<>();
		populateGridList(image, grids, gridWidth, gridHeight);
		populateGridPixels(image, grids);
		return grids;
	}
	
	/**
	 * Populate list of Grids with their coordinates (but no pixels)
	 * 
	 * @param image
	 * @param grids
	 * @param gridWidth
	 * @param gridHeight
	 */
	private void populateGridList(BufferedImage image, List<Grid> grids, double gridWidth, double gridHeight) {
		//Starting x point offset is 18% of width
		//Starting y point offset is 17.1875% of height
		double xOffset = image.getWidth() * 0.18;
		double yOffset = image.getHeight() * 0.131;
		for (int y = 0; y < NUM_OF_ROWS; y++) {
			for (int x = 0; x < NUM_OF_COLUMNS; x++) {
				Grid grid = new Grid(gridWidth, gridHeight);
				grid.setRowcolumnX(x);
				grid.setRowcolumnY(y);
				double xCoordinate = xOffset + gridWidth * x;
				double yCoordinate = yOffset + gridHeight * y;
				Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);
				grid.setTopLeftCoordinate(coordinate);
				grids.add(grid);
			}
		}
	}
	
	/**
	 * Populate Grid objects in a list with pixels.
	 * 
	 * @param image
	 * @param grids
	 */
	private void populateGridPixels(BufferedImage image, List<Grid> grids) {
		for (Grid grid : grids) {
			List<Pixel> pixels = new ArrayList<Pixel>();
			Coordinate topLeftCoordinate = grid.getTopLeftCoordinate();
			int posX = (int) Math.round(topLeftCoordinate.getX());
			int posY = (int) Math.round(topLeftCoordinate.getY());
			for (int y = 0; y < grid.getHeight(); y++) {
				for (int x = 0; x < grid.getWidth(); x++) {
					int argb = image.getRGB(posX + x, posY + y);
					Pixel pixel = new Pixel(argb);
					pixel.setPosXY(new Coordinate(posX + x,  posY + y));
					pixels.add(pixel);
				}
			}
			grid.setPixels(pixels);
		}
	}
	
	/**
	 * Crops image based on coordindates, width and height
	 * 
	 * @param src
	 * @param rect
	 * @return croppedImage
	 */
	private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
	      BufferedImage croppedImg = src.getSubimage((int) rect.getX(), (int) rect.getY(), rect.width, rect.height);
	      return croppedImg; 
	   }

    /**
     * Checks to see if the pixel is just a "random" dark pixel sticking out. It is considered "random"
     * or an outlier if there is only one other dark pixel touching it. A dark border should always have at least two
     * pixels touching it.
     * 
     * @param image
     * @param posx
     * @param posy
     * @return isPixelPartOfBorder
     */
    private boolean isPixelPartOfBorder(BufferedImage image, int posX, int posY) {
		   List<Pixel> adjPixels = new ArrayList<>();
		   try {
			   // pixel to the left
			   adjPixels.add(new Pixel(image.getRGB(posX - 1, posY)));
			   
			   // pixel to the right
			   adjPixels.add(new Pixel(image.getRGB(posX + 1, posY)));
			   // pixel above
			   adjPixels.add(new Pixel(image.getRGB(posX, posY - 1)));
			   // pixel below
			   adjPixels.add(new Pixel(image.getRGB(posX, posY + 1)));
		   } catch (ArrayIndexOutOfBoundsException e) {
			   throw e;
		   }
		   int numOfAdjDarkPixels = 0;
		   for (Pixel pixel : adjPixels) {
			   if (pixel.isDark()) {
				   numOfAdjDarkPixels++;
			   }
		   }
		   
		   if (numOfAdjDarkPixels <= 1) {
			   return false;
		   } else {
			   return true;
		   }
 		      	   
    }
    
    public Grid coordinates2Grid(List<Grid> grids, int x, int y) {
    	for (Grid grid : grids) {
			List<Pixel> pixels = grid.getPixels();
			for (Pixel pixel : pixels) {
				Coordinate coordinate = pixel.getCoordinate();
				if ((int) coordinate.getX() == x &&
						(int) coordinate.getY() == y) {
					return grid;
				}
			}
		}
    	return null;
    }

    /**
     * Draws a red dot in the center of the nearest grid that matches the coordinates x and y.
     * 
     * @param image 
     * @param grids
     * @param x
     * @param y
     * 
     * @return doCoordinatesFitOnAGrid
     */
	@Override
	public boolean drawRedMarkOnGrid(BufferedImage image, List<Grid> grids, int x, int y) {
		Grid grid = coordinates2Grid(grids, x, y);
		if (grid != null) {
			int gridCenterX = (int) (grid.getTopLeftCoordinate().getX() + grid.getWidth()/3);
			int gridCenterY = (int) (grid.getTopLeftCoordinate().getY() + grid.getHeight()/3);
			Graphics2D g2d = image.createGraphics();
			g2d.setColor(Color.red);
			//g2d.drawLine(drawOnX, drawOnY, drawOnX, drawOnY);
			g2d.fillOval(gridCenterX, gridCenterY, 5, 5);
			g2d.dispose();
			return true;
		} else {
			return false;
		}
	}	
}
