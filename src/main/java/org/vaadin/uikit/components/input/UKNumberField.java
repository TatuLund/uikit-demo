package org.vaadin.uikit.components.input;

import java.util.Optional;

import org.vaadin.uikit.interfaces.UkBorder;
import org.vaadin.uikit.interfaces.UkFormSizing;
import org.vaadin.uikit.interfaces.UkTooltip;
import org.vaadin.uikit.interfaces.UkValidation;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Tag;

@Tag(Tag.INPUT)
public class UkNumberField extends AbstractSinglePropertyField<UkNumberField, Double>
        implements UkValidation, HasStyle, Focusable<UkNumberField>, UkTooltip,
        UkFormSizing, UkBorder {

    private static final PropertyDescriptor<String, Optional<String>> placeholderDescriptor = PropertyDescriptors
            .optionalAttributeWithDefault("placeholder", "");
    
    public UkNumberField() {
        this(0d, 100d, 1.0d);
    }

    public UkNumberField(double min, double max, double step) {
        super("value", 0d, String.class, value -> Double.valueOf(value),
                value -> "" + value);
        addClassName("uk-input");
        getElement().setAttribute("type", "number");
        getElement().synchronizeProperty("value", "change");
        setMin(min);
        setMax(max);
        setStep(step);
    }

    /**
     * Sets the placeholder text that is shown if the input is empty.
     *
     * @param placeholder
     *            the placeholder text to set, or <code>null</code> to remove
     *            the placeholder
     */
    public void setPlaceholder(String placeholder) {
        set(placeholderDescriptor, placeholder);
    }

    /**
     * Gets the placeholder text.
     *
     * @see #setPlaceholder(String)
     *
     * @return an optional placeholder, or an empty optional if no placeholder
     *         has been set
     */
    public Optional<String> getPlaceholder() {
        return get(placeholderDescriptor);
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
