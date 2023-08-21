package org.vaadin.uikit.components.input;

import org.vaadin.uikit.components.interfaces.UkFormSizing;
import org.vaadin.uikit.components.interfaces.UkHidden;
import org.vaadin.uikit.components.interfaces.UkTooltip;
import org.vaadin.uikit.components.interfaces.UkValidation;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Tag;

@SuppressWarnings("serial")
@Tag(Tag.INPUT)
public class UkRange extends AbstractSinglePropertyField<UkRange, Double>
        implements HasStyle, Focusable<UkRange>, UkField {

    private static final PropertyDescriptor<String, String> typeDescriptor = PropertyDescriptors
            .attributeWithDefault("type", "range");

    public UkRange() {
        this(0d, 100d, 1.0d);
    }

    public UkRange(double min, double max, double step) {
        super("value", 0d, String.class, value -> Double.valueOf(value),
                value -> "" + value);
        addClassName("uk-range");
        getElement().setAttribute("type", "range");
        getElement().synchronizeProperty("value", "click");
        setMin(min);
        setMax(max);
        setStep(step);
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

    public void setMin(double min) {
        getElement().setAttribute("min", "" + min);
    }

    public void setMax(double max) {
        getElement().setAttribute("max", "" + max);
    }

    public void setStep(double step) {
        getElement().setAttribute("step", "" + step);
    }

}
