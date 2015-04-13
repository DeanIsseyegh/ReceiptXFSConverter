package com.dean.receiptmaker;

public final class ConsoleMessages {

	private ConsoleMessages() {};
	
	public static final String CONSOLE_LINEBREAK = "------------------------------------\n";
	
	public static final String CONVERT_2_XFS_WRN = "WARNING: Initial value for a field is longer than field size"
			+ "\n\nDetails - ";
	public static final String NO_FIELDS_DEFINED = "You didn't define any XFS fields.";
	public static final String IMAGE_LOADED = "Image loaded.";
	public static final String FILE_SAVED_TO = "File saved under : ";
	public static final String IMAGE_CROPPED = "Image cropped and gridded.";
	public static final String WELCOME = "Welcome!\n\n1. Load receipt image\n2. Crop/Grid it\n3. Define fields\n4. Produce XFS Form";
	public static final String COULD_NOT_GRID = "Could not Grid - receipt image with border not reconigzed";
	public static final String SETTINGS_REVERTED = "Settings have been reverted back to default.";
	public static final String SETTINGS_CHANGED = "New settings have been saved.";
	public static final String SETTINGS_NOT_SAVED = "Settings were not saved.";
	public static final String SETTINGS_NOT_SAVED_INVALID = "Settings were not saved as they were invalid.";
	
	public static String getDefinedXFieldsMsg(int numOfFields) {
		return "You defined " + numOfFields + " XFS fields.\n\n"
				+ "Click \"Convert Receipt To XFS Form\" to finish, or define more fields.";
	}

}
