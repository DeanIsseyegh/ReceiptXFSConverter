package com.dean.receiptmaker.model;

import java.util.List;

public class Coordinate {

	final private double x;
	final private double y;
	
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public boolean isHigherThan(Coordinate coordinate) {
		return isHigherThan(coordinate.getY());
	}
	
	public boolean isHigherThan(double y) {
		if (this.y < y) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFurtherLeftThan(Coordinate coordinate) {
		return isFurtherLeftThan(coordinate.getX());
	}
	
	public boolean isFurtherLeftThan(double x) {
		if (this.x < x) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether this coordinate is higher up than all the others
	 * 
	 * @param coordinates
	 * @return isHighestCoordinate
	 */
	public boolean isHighest(List<Coordinate> coordinates) {
		for (Coordinate coordinate : coordinates) {
			if (!this.isHigherThan(coordinate.getY())
					&& this.y != coordinate.getY()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks whether this coordinate is further left than all the other coordinates
	 * 
	 * @param coordinates
	 * @return
	 */
	public boolean isMostLeft(List<Coordinate> coordinates) {
		for (Coordinate coordinate : coordinates) {
			if (!this.isFurtherLeftThan(coordinate.getX())
					&& this.x != coordinate.getX()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		String str = "Coorindates: xy(" + getX() + ", " + getY() + ")"; 
		return str;
	}
}
