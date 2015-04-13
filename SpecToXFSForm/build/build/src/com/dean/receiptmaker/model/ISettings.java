package com.dean.receiptmaker.model;


public interface ISettings {

	public abstract String getFormName();

	public abstract void setFormName(String formName);

	public abstract String getUnit();

	public abstract void setUnit(String unit);

	public abstract int getxModifier();

	public abstract void setxModifier(int xModifier);

	public abstract int getyModifier();

	public abstract void setyModifier(int yModifier);

	public abstract String getFont();

	public abstract void setFont(String font);

	public abstract String getCpi();

	public abstract void setCpi(String cpi);

	public abstract String getUnitHeader();

	public abstract void setUnitHeader(String unitHeader);

	public abstract String getFormSize();

	public abstract void setFormSize(String formSize);

}