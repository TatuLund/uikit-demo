package org.vaadin.uikit;

import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.html.Input;

public class MyInput extends Input implements HasValidation {

	private boolean invalid = false;
	private String errorMessage = "";

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		if (errorMessage != null && !errorMessage.isEmpty()) {
			getElement().setAttribute("uk-tooltip", errorMessage);
		} else {
			getElement().removeAttribute("uk-tooltip");
		}
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
		if (invalid) {
			addClassName("uk-form-danger");
		} else {
			removeClassName("uk-form-danger");
			getElement().removeAttribute("uk-tooltip");
		}

	}

	@Override
	public boolean isInvalid() {
		return invalid;
	}

}
