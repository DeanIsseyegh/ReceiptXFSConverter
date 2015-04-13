package com.dean.receiptmaker;

public class DialogMessages {
	
	private DialogMessages() {};

	public static final String ALERT_XFS_ERROR_MSG = "There was an issue writing the XFS field values correctly.\n\n"
			+ "Please make sure you set the field values in the GUI editor!\n\n" + "Closing down. Ciao.";
	public static final String ALERT_ERROR_TITLE = "Error";
	
	public static final String ALERT_OOPS_TITLE = "Oops!";
	public static final String ALERT_OOPS_HEADER = "Oops....Somethings gone wrong!";
	
	public static final String ALERT_INVALID_GRID_HEADER = "Oops, looks like you selected an invalid grid!";
	public static final String ALERT_INVALID_GRID_STD_MSG = "Please make sure you select a valid grid.";
	public static final String ALERT_INVALID_GRID_X_MSG = "Please make sure you define your grids from left to right.";
	public static final String ALERT_INVALID_GRID_Y_MSG = "Please make sure you have finished defining your last XFS field.";
	
	public static final String ALERT_SETTINGS_NOT_SAVED_HEADER = "Settings not saved.";
	public static final String ALERT_SETTINGS_NOT_SAVED_MSG = "Settings were not saved due invalid field entries.";
	
	public static final String BTN_REVERT_LABEL = "Revert to Default";
	public static final String BTN_SAVE_LABEL = "Save";
	
	public static final String ABOUT_TITLE = "About";
	public static final String ABOUT_MSG = "Receipt Spec to XFS form converter\n\nVersion 1.0\n\nBy Dean Isseyegh";
	
	public static final String SETTINGS_TITLE = "Settings";
	public static final String SETTINGS_HEADER = "Modify Settings";
	public static final String SETTINGS_MSG = "Please modify settings";
	
}
