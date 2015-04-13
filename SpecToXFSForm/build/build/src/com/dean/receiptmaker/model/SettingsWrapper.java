package com.dean.receiptmaker.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Responsible for extracting Settings from an XML file.
 * 
 * @author Dean
 *
 */
@XmlRootElement(name = "settings")
public class SettingsWrapper implements ISettings {

	private String formName;
	private String unit;
	private String unitHeader;
	private String formSize;
	private int xModifier;
	private int yModifier;
	private String font;
	private String cpi;
	
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#getFormName()
	 */
	@Override
	@XmlElement(name = "formName")
	public String getFormName() {
		return formName;
	}
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#setFormName(java.lang.String)
	 */
	@Override
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#getUnit()
	 */
	@Override
	@XmlElement(name = "unit")
	public String getUnit() {
		return unit;
	}
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#setUnit(java.lang.String)
	 */
	@Override
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#getxModifier()
	 */
	@Override
	@XmlElement(name = "xmodifier")
	public int getxModifier() {
		return xModifier;
	}
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#setxModifier(int)
	 */
	@Override
	public void setxModifier(int xModifier) {
		this.xModifier = xModifier;
	}
	
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#getyModifier()
	 */
	@Override
	@XmlElement(name = "ymodifier")
	public int getyModifier() {
		return yModifier;
	}
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#setyModifier(int)
	 */
	@Override
	public void setyModifier(int yModifier) {
		this.yModifier = yModifier;
	}
	
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#getFont()
	 */
	@Override
	@XmlElement(name = "font")
	public String getFont() {
		return font;
	}
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#setFont(java.lang.String)
	 */
	@Override
	public void setFont(String font) {
		this.font = font;
	}
	
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#getCpi()
	 */
	@Override
	@XmlElement(name = "cpi")
	public String getCpi() {
		return cpi;
	}
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#setCpi(java.lang.String)
	 */
	@Override
	public void setCpi(String cpi) {
		this.cpi = cpi;
	}
	
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#getUnitHeader()
	 */
	@Override
	@XmlElement(name = "unitHeader")
	public String getUnitHeader() {
		return unitHeader;
	}
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#setUnitHeader(java.lang.String)
	 */
	@Override
	public void setUnitHeader(String unitHeader) {
		this.unitHeader = unitHeader;
	}
	
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#getFormSize()
	 */
	@Override
	@XmlElement(name = "formSize")
	public String getFormSize() {
		return formSize;
	}
	/* (non-Javadoc)
	 * @see com.dean.receiptmaker.model.ISettingsX#setFormSize(java.lang.String)
	 */
	@Override
	public void setFormSize(String formSize) {
		this.formSize = formSize;
	}
	
}
