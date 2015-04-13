package com.dean.receiptmaker.model.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dean.receiptmaker.model.Coordinate;

public class CoordinateTest {

	@Test
	public void shouldCorrectlyDetectHigherCoordinate() {
		Coordinate higherCoordinate = new Coordinate(5, 4);
		Coordinate lowerCoordinate = new Coordinate(5, 5);
		
		assertThat(higherCoordinate.isHigherThan(lowerCoordinate), is(true));
		assertThat(lowerCoordinate.isHigherThan(higherCoordinate), is(false));
	}
	
	@Test
	public void shouldCorrectlyDetectHigherYValue() {
		Coordinate higherCoordinate = new Coordinate(5, 4);
		Coordinate lowerCoordinate = new Coordinate(5, 100);
		int y = 5;
		
		assertThat(higherCoordinate.isHigherThan(y), is(true));
		assertThat(lowerCoordinate.isHigherThan(y), is(false));
	}
	
	@Test
	public void shouldCorrectlyDetectLeftMostCoordinate() {
		Coordinate leftMostCoordinate = new Coordinate(4, 5);
		Coordinate coordinate2 = new Coordinate(5, 5);
		
		assertThat(leftMostCoordinate.isFurtherLeftThan(coordinate2), is(true));
		assertThat(coordinate2.isFurtherLeftThan(leftMostCoordinate), is(false));
	}
	
	@Test
	public void shouldCorrectlyDetectLeftXValue() {
		Coordinate leftMostCoordinate = new Coordinate(4, 5);
		Coordinate rightMostCoordinate = new Coordinate(100, 5);
		int x = 5;
		
		assertThat(leftMostCoordinate.isFurtherLeftThan(x), is(true));
		assertThat(rightMostCoordinate.isFurtherLeftThan(x), is(false));
	}
	
	@Test
	public void shouldCorrectlyDetectIfCoordinatesAreEqual() {
		Coordinate coordinate = new Coordinate(5, 5);
		
		assertThat(coordinate.isHigherThan(coordinate), is(false));
		assertThat(coordinate.isFurtherLeftThan(coordinate), is(false));
	}
	
	@Test
	public void shouldCorrectlyDetectHighestCoordinates() {
		Coordinate higherCoordinate = new Coordinate(2, 0);
		List<Coordinate> coordinates = new ArrayList<>();
		for (int i = 1; i < 99; i++) {
			coordinates.add(new Coordinate(3, i));
		}
		assertThat(higherCoordinate.isHighest(coordinates), is(true));
	}
	
	@Test
	public void shouldCorrectlyDetectLowestCoordinates() {
		Coordinate higherCoordinate = new Coordinate(2, 1000);
		List<Coordinate> coordinates = new ArrayList<>();
		for (int i = 1; i < 99; i++) {
			coordinates.add(new Coordinate(3, i));
		}
		assertThat(higherCoordinate.isHighest(coordinates), is(false));
	}
	
	@Test
	public void shouldCorrectlyDetectLeftMostCoordinates() {
		Coordinate coordinate = new Coordinate(0, 2);
		List<Coordinate> coordinates = new ArrayList<>();
		for (int i = 1; i < 99; i++) {
			coordinates.add(new Coordinate(i, 2));
		}
		assertThat(coordinate.isMostLeft(coordinates), is(true));
	}
	
	@Test
	public void shouldCorrectlyDetectRightMostCoordinates() {
		Coordinate coordinate = new Coordinate(1000, 2);
		List<Coordinate> coordinates = new ArrayList<>();
		for (int i = 1; i < 99; i++) {
			coordinates.add(new Coordinate(i, 2));
		}
		assertThat(coordinate.isMostLeft(coordinates), is(false));
	}
}
