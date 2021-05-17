package org.vaadin.uikit.components.input;

import org.vaadin.uikit.interfaces.UKTooltip;
import org.vaadin.uikit.interfaces.UKValidation;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;

@Tag(Tag.INPUT)
public class UKCheckbox extends AbstractSinglePropertyField<UKCheckbox, Boolean>
        implements UKValidation, HasStyle, Focusable<UKCheckbox>, UKTooltip {

    public UKCheckbox() {
        super("value", false, String.class, value -> Boolean.valueOf(value),
                value -> "" + value);
        getElement().addEventListener("click", event -> {
            this.setModelValue(!getValue(), true);
        });
        addClassName("uk-checkbox");
        getElement().setAttribute("type", "checkbox");
    }

    public void setDisabled(boolean disabled) {
        this.getElement().setProperty("disabled", disabled);
    }

    public boolean isDisabled() {
        return getElement().getProperty("disabled", false);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return super.isReadOnly();
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        super.setRequiredIndicatorVisible(requiredIndicatorVisible);

    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return super.isRequiredIndicatorVisible();
    }

}
