package org.vaadin.uikit;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;

@Tag("input")
public class UKCheckbox extends AbstractField<UKCheckbox,Boolean> implements UKValidation, HasStyle {

	public UKCheckbox() {
		super(false);
		addClassName("uk-checkbox");
		getElement().setAttribute("type", "checkbox");
	}

//	@Override
//	public Boolean getValue() {
//		super.getValue()
//		return Boolean.valueOf(checkbox.getValue());
//	}

	@Override
	public void setReadOnly(boolean readOnly) {
		setReadOnly(readOnly);
		
	}

	@Override
	public boolean isReadOnly() {
		return isReadOnly();
	}

	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
		setRequiredIndicatorVisible(requiredIndicatorVisible);
		
	}

	@Override
	public boolean isRequiredIndicatorVisible() {
		return isRequiredIndicatorVisible();
	}

	@Override
	protected void setPresentationValue(Boolean newPresentationValue) {
//		setValue(newPresentationValue.toString());
		getElement().setProperty("value", newPresentationValue.toString());
	}

}
