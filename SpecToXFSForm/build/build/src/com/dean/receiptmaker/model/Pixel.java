package com.dean.receiptmaker.model;

import java.awt.Color;

public class Pixel extends Color {

	private static final long serialVersionUID = 1L;
	
	private Coordinate posXY;

	public Pixel(int rgb) {
		super(rgb);
	}
	
	public Pixel(int r, int g, int b) {
        super(r,g,b);
    }

	/**
	 * Checks to see if this pixel is closer to white or black, and returns
	 * true if black.
	 * 
	 * @return isDarkPixel
	 */
    public boolean isDark() {
 	   int luminance = getLuminance(getRGB());
 	   if (luminance < 129) {
 		   return true;
 	   } else {
 		   return false;
 	   }
    }
    
    /**
     * Use lumincance forumula to convert argb into grayscale
     * 
     * @param argb
     * @return luminanceValue
     */
    public static int getLuminance(int argb) {
	    int lum= (   77  * ((argb>>16)&255) 
	               + 150 * ((argb>>8)&255) 
	               + 29  * ((argb)&255))>>8;
	    return lum;
	}
    
    public boolean isRed() {
    	if (getRed() > 245 &&
				getBlue() < 10 &&
				getGreen() < 10) {
			return true;
		} else {
			return false;
		}
    }

	public Coordinate getCoordinate() {
		return posXY;
	}

	public void setPosXY(Coordinate posXY) {
		this.posXY = posXY;
	}
	
}
