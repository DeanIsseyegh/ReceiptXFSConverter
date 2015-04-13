package com.dean.receiptmaker.model;

import com.dean.receiptmaker.MainApp;

/**
 * Simple class to hold XFS field values
 * 
 * @author Dean
 *
 */
public class XFSField {

	private String fieldName;
	private String horizontalJustify;
	private String initialValue;

	private int posX;
	private int posY;
	private int length;
	private int height = 1;
	private int xModifier =  MainApp.SETTINGS.getxModifier();
	private int yModifier = MainApp.SETTINGS.getyModifier();
	
	public XFSField(ISettings settings) {
		xModifier =  settings.getxModifier();
		yModifier = settings.getyModifier();
	}

	public int getPosX() {
		return posX * xModifier;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY * yModifier;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getLength() {
		return (length + 1) * xModifier;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}
	
	public int getHeight() {
		return height * yModifier;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getHorizontalJustify() {
		if (horizontalJustify == null || horizontalJustify.equalsIgnoreCase("LEFT")) {
			return null;
		} else {
			return horizontalJustify;
		}
	}

	public void setHorizontalJustify(String horizontalJustify) {
		this.horizontalJustify = horizontalJustify;
	}

	public String toString() {
		String pad = "    ";
		String pad2 = pad + "";
		String initialValue = ((getInitialValue() == null || getInitialValue().equals("")) ? "" : (pad2 + "    INITIALVALUE   \"" + getInitialValue() + "\"\n"));
		String horizontalJustify = ((getHorizontalJustify() == null) ? "" : (pad2 + "    HORIZONTAL     " + getHorizontalJustify().toUpperCase() + "\n"));
		String str = 
				  pad + "XFSFIELD        \"" + getFieldName() + "\"\n"
				+ pad + "BEGIN\n"
				+ pad2 + "    POSITION       " + getPosX() + "," + getPosY() + "\n"
				+ pad2 + "    SIZE           " + getLength() + "," + getHeight() + "\n"
				+ pad2 + "    OVERFLOW       TRUNCATE\n"
				+ pad2 + "    FONT           \"" + MainApp.SETTINGS.getFont() + "\"\n"
				+ pad2 + "    CPI            " + MainApp.SETTINGS.getCpi() + "\n"
				+ horizontalJustify
				+ initialValue
				+ pad + "END";
		return str;
	}

/*	XFSFIELD        "TRAN_ID_LABEL"
	BEGIN
	    POSITION       0,4
		SIZE           56,4
		OVERFLOW       TRUNCATE
		FONT           "Consolas"
		CPI            14
		INITIALVALUE   "TRAN ID:"
	END*/

}
