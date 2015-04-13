package com.dean.receiptmaker.model;

import java.util.List;

public class Grid {

	private List<Pixel> pixels;
	private int rowcolumnX;
	private int rowcolumnY;
	private Coordinate topLeftCoordinate;
	private double width;
	private double height;
	
	public Grid(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean doesContainRedPixel() {
		List<Pixel> pixels = getPixels();
		for (Pixel pixel : pixels) {
			if (pixel.isRed()){
				return true;
			}
		}
		return false;
	}
	
	public List<Pixel> getPixels() {
		return pixels;
	}

	public void setPixels(List<Pixel> pixels) {
		this.pixels = pixels;
	}

	public int getRowcolumnX() {
		return rowcolumnX;
	}

	public void setRowcolumnX(int rowcolumnX) {
		this.rowcolumnX = rowcolumnX;
	}

	public int getRowcolumnY() {
		return rowcolumnY;
	}

	public void setRowcolumnY(int rowcolumnY) {
		this.rowcolumnY = rowcolumnY;
	}

	public Coordinate getTopLeftCoordinate() {
		return topLeftCoordinate;
	}

	public void setTopLeftCoordinate(Coordinate coordinate) {
		this.topLeftCoordinate = coordinate;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	public String toString() {
		String str = "ROWCOLUMNXY(" + getRowcolumnX() + ", " + getRowcolumnY() + ")";
		return str;
	}
	
	/**
	 * Checks to see if ROWCOLUMN values are equal.
	 *
	 * Note: Doesn't care about grid width/height
	 */
	@Override
	public boolean equals(Object obj) {
		Grid grid = null;
		if (obj instanceof Grid) {
			grid = (Grid) obj;
			if (getRowcolumnX() == grid.getRowcolumnX() &&
					getRowcolumnY() == grid.getRowcolumnY()) {
				return true;
			} 
		}
		return false;
	}
	
}
