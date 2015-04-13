package com.dean.receiptmaker.model.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dean.receiptmaker.model.Grid;
import com.dean.receiptmaker.model.Pixel;

public class GridTest {
	
	public static final Pixel RED_PIXEL = new Pixel(255, 0 ,0);
	public static final Pixel WHITE_PIXEL = new Pixel(255, 255, 255);
	public static final Pixel BLACK_PIXEL = new Pixel(0, 0, 0);
	
	@Test
	public void shouldCorrectlyCompareSameGridRowcolumns() {
		Grid grid = new Grid(1, 1);
		grid.setRowcolumnX(2);
		grid.setRowcolumnY(2);
		
		Grid grid2 = new Grid(1, 1);
		grid2.setRowcolumnX(2);
		grid2.setRowcolumnY(2);
		
		assertThat(grid.equals(grid2), is(true));
	}
	
	@Test
	public void shouldCorrectlyCompareDifferentGridRowcolumns() {
		Grid grid = new Grid(1, 1);
		grid.setRowcolumnX(2);
		grid.setRowcolumnY(2);
		
		Grid grid2 = new Grid(1, 1);
		grid2.setRowcolumnX(1);
		grid2.setRowcolumnY(1);
		
		assertThat(grid.equals(grid2), is(false));
	}
	
	@Test
	public void shouldCorrectlyDetectIfContainsARedPixel() {
		Grid grid = new Grid(1, 1);
		List<Pixel> pixels = new ArrayList<>();
		pixels.add(RED_PIXEL);
		pixels.add(BLACK_PIXEL);
		pixels.add(WHITE_PIXEL);
		grid.setPixels(pixels);
		
		assertThat(grid.doesContainRedPixel(), is(true));
	}
	
	@Test
	public void shouldCorrectDetectIfDoesNotContainRedPixel() {
		Grid grid = new Grid(1, 1);
		List<Pixel> pixels = new ArrayList<>();
		pixels.add(WHITE_PIXEL);
		pixels.add(BLACK_PIXEL);
		pixels.add(WHITE_PIXEL);
		grid.setPixels(pixels);
		
		assertThat(grid.doesContainRedPixel(), is(false));
	}

}
