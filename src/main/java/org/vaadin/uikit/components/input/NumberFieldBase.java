package org.vaadin.uikit.components.input;

import java.util.Optional;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.function.SerializableFunction;

@SuppressWarnings("serial")
@Tag(Tag.INPUT)
public abstract class NumberFieldBase<T extends Number>
        extends AbstractSinglePropertyField<NumberFieldBase<T>, T>
        implements HasStyle, Focusable<NumberFieldBase<T>>, UkField {

    private static final PropertyDescriptor<String, Optional<String>> placeholderDescriptor = PropertyDescriptors
            .optionalAttributeWithDefault("placeholder", "");

    public NumberFieldBase() {
        super("value", null, true);
    }

    public <P> NumberFieldBase(String propertyName, T defaultValue,
            Class<P> elementPropertyType,
            SerializableFunction<P, T> presentationToModel,
            SerializableFunction<T, P> modelToPresentation) {
        super(propertyName, defaultValue, elementPropertyType, presentationToModel, modelToPresentation);
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

    public void setMin(T min) {
        getElement().setAttribute("min", "" + min);
    }

    public void setMax(T max) {
        getElement().setAttribute("max", "" + max);
    }

    public void setStep(T step) {
        getElement().setAttribute("step", "" + step);
    }

}
