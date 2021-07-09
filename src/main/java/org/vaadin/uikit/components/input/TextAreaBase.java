package org.vaadin.uikit.components.input;

import java.util.Optional;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;

@SuppressWarnings("serial")
@Tag(Tag.TEXTAREA)
public abstract class TextAreaBase
        extends AbstractSinglePropertyField<TextAreaBase, String> implements
        Focusable<TextAreaBase>, HasSize, HasStyle, HasValueChangeMode {

    private static final PropertyDescriptor<String, Optional<String>> placeholderDescriptor = PropertyDescriptors
            .optionalAttributeWithDefault("placeholder", "");

    private int valueChangeTimeout = DEFAULT_CHANGE_TIMEOUT;

    private ValueChangeMode currentMode;

    /**
     * Creates a new input without any specific type, with
     * {@link ValueChangeMode#ON_CHANGE ON_CHANGE} value change mode.
     */
    public TextAreaBase() {
        this(ValueChangeMode.ON_CHANGE);
    }

    /**
     * Creates a new input without any specific type.
     *
     * @param valueChangeMode
     *            initial value change mode, or <code>null</code> to disable the
     *            value synchronization
     */
    public TextAreaBase(ValueChangeMode valueChangeMode) {
        super("value", "", false);
        setValueChangeMode(valueChangeMode);
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

    @Override
    public ValueChangeMode getValueChangeMode() {
        return currentMode;
    }

    @Override
    public void setValueChangeMode(ValueChangeMode valueChangeMode) {
        currentMode = valueChangeMode;
        setSynchronizedEvent(
                ValueChangeMode.eventForMode(valueChangeMode, "input"));
        applyChangeTimeout();
    }

    @Override
    public void setValueChangeTimeout(int valueChangeTimeout) {
        this.valueChangeTimeout = valueChangeTimeout;
        applyChangeTimeout();
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default value is {@link HasValueChangeMode#DEFAULT_CHANGE_TIMEOUT}.
     */
    @Override
    public int getValueChangeTimeout() {
        return valueChangeTimeout;
    }

    private void applyChangeTimeout() {
        ValueChangeMode.applyChangeTimeout(currentMode, valueChangeTimeout,
                getSynchronizationRegistration());
    }
}
