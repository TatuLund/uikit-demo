package org.vaadin.uikit;

public class UKTextField extends UKAbstractInput {

	public UKTextField() {
    	addClassName("uk-input");
	}

	public void setDisabled(boolean disabled) {
		this.getElement().setProperty("disabled", disabled);
	}

	public boolean isDisabled() {
		return getElement().getProperty("disabled", false);
	}
}
