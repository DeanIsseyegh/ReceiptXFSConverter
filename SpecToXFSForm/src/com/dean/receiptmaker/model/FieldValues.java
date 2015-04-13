package com.dean.receiptmaker.model;

public class FieldValues {
	
	private String fieldName;
	private String horizontalJustify;
	private String initialValue;
	
	public FieldValues(String fieldName, String horizontalJustify, String initialValue) {
		this.fieldName = fieldName;
		this.horizontalJustify = horizontalJustify;
		this.initialValue = initialValue;
	}

	public FieldValues() {}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getHorizontalJustify() {
		return horizontalJustify;
	}
	public void setHorizontalJustify(String horizontalJustify) {
		this.horizontalJustify = horizontalJustify;
	}
	public String getInitialValue() {
		return initialValue;
	}
	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}
}
