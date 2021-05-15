package org.vaadin.uikit;

public class UKTextArea extends TextAreaBase implements UKValidation, UKTooltip, UKMargin, UKPadding, UKFormSizing {

	public UKTextArea() {
		addClassName("uk-textarea");
	}

	public void setResizeable(boolean resizeable) {
		if (resizeable) {
			getElement().getStyle().set("resize", "none");
		} else {
			getElement().getStyle().set("resize", "auto");
		}
	}

	public void setRows(int rows) {
		if (rows > 0) {
			getElement().setProperty("rows", "" + rows);
		}
	}

	public void setDisabled(boolean disabled) {
		this.getElement().setProperty("disabled", disabled);
	}

	public boolean isDisabled() {
		return getElement().getProperty("disabled", false);
	}
}
