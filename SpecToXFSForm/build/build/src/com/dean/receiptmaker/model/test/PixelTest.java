package com.dean.receiptmaker.model.test;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.dean.receiptmaker.model.Pixel;

public class PixelTest {

	@Test
	public void shouldCorrectDetectIfRed() {
		Pixel pixel = new Pixel(255, 0, 0);
		assertThat(pixel.isRed(), is(true));
	}
	
	@Test
	public void shouldCorrectDetectIfDark() {
		Pixel pixel = new Pixel(0, 0, 0);
		assertThat(pixel.isDark(), is(true));
	}
	
	@Test
	public void shouldCorrectDetectIfNotRed() {
		Pixel pixel = new Pixel(0, 0, 0);
		assertThat(pixel.isRed(), is(false));
	}
	
	@Test
	public void shouldCorrectDetectIfNotDark() {
		Pixel pixel = new Pixel(255, 255, 255);
		assertThat(pixel.isDark(), is(false));
	}
}
