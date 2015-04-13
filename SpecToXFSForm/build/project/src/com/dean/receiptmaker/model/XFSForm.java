package com.dean.receiptmaker.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class XFSForm {
	
	private ISettings settings;
	private String formName;
	private List<XFSField> fields;
	
	public XFSForm(List<XFSField> fields, ISettings settings) {
		this.fields = fields;
		this.settings = settings;
		formName = settings.getFormName();
	}
	
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public List<XFSField> getFields() {
		return fields;
	}

	public void setFields(List<XFSField> fields) {
		this.fields = fields;
	}
	
	public String toString() {
		String pad = "    ";
		String unitHeader = settings.getUnitHeader();
		String formSize = settings.getFormSize();
		String allFields = "";
		int counter = 0;
		for (XFSField field : fields) {
			++counter;
			if (counter == fields.size()) {
				allFields += field + "\n";
			} else {
				allFields += field + "\n\n";
			}
		}
		String xfsForm = 
				"XFSFORM        \"" + getFormName() + "\""
				+ "\nBEGIN\n"
				+ pad + "UNIT			" + unitHeader + "\n"
				+ pad + "SIZE           " + formSize + "\n"
				+ pad + "VERSION         0, 0, \"\", \"\"\n"
				+ pad + "LANGUAGE	     0000\n\n"
				+ allFields
				+ "END";
		return xfsForm;
	}

	public File writeToFile() throws FileNotFoundException, UnsupportedEncodingException {
		String fileName = getFormName() + ".flc";
		File file = new File(fileName);
		PrintWriter writer = null;
		writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		writer.println(this);
		writer.close();
		return file;
	}
}
