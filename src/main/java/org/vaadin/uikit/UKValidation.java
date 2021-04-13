package org.vaadin.uikit;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.HasValidation;

public interface UKValidation extends HasValidation, HasElement {

	static String INVALID_CLASS = "uk-form-danger";
	static String ERROR_ATTRIBUTE = "uk-tooltip";

	@Override
	public default void setErrorMessage(String errorMessage) {
		if (errorMessage != null && !errorMessage.isEmpty()) {
			getElement().setAttribute(ERROR_ATTRIBUTE, errorMessage);
		} else {
			getElement().removeAttribute(ERROR_ATTRIBUTE);
		}
	}

	@Override
	public default String getErrorMessage() {
		return getElement().getAttribute(ERROR_ATTRIBUTE);
	}

	@Override
	public default void setInvalid(boolean invalid) {
		if (invalid) {
			getElement().getClassList().add(INVALID_CLASS);
		} else {
			getElement().getClassList().remove(INVALID_CLASS);
			getElement().removeAttribute(ERROR_ATTRIBUTE);
		}
	}

	@Override
	public default boolean isInvalid() {
		return getElement().getClassList().contains(INVALID_CLASS);
	}
}
