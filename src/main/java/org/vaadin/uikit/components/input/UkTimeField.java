package org.vaadin.uikit.components.input;

import java.time.LocalTime;
import java.util.Optional;

import org.vaadin.uikit.components.interfaces.UkBorder;
import org.vaadin.uikit.components.interfaces.UkFormSizing;
import org.vaadin.uikit.components.interfaces.UkTooltip;
import org.vaadin.uikit.components.interfaces.UkValidation;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.function.SerializableFunction;

@SuppressWarnings("serial")
@Tag(Tag.INPUT)
public class UkTimeField
        extends AbstractSinglePropertyField<UkTimeField, LocalTime>
        implements UkValidation, HasStyle, Focusable<UkTimeField>, UkTooltip,
        UkFormSizing, UkBorder {
    private static final PropertyDescriptor<String, Optional<String>> placeholderDescriptor = PropertyDescriptors
            .optionalAttributeWithDefault("placeholder", "");

    public enum TimeResolution {
        HOUR, MINUTE, SECOND;
    }

    private TimeResolution resolution;

    public UkTimeField() {
        this(TimeResolution.MINUTE);
    }

    public UkTimeField(TimeResolution resolution) {
        super("value", LocalTime.now(), String.class,
                value -> value.isEmpty() ? null : LocalTime.parse(value),
                value -> value.toString());
        addClassName("uk-input");
        this.resolution = resolution;
        getElement().setAttribute("type", "time");
        if (resolution == TimeResolution.HOUR) {
            getElement().setAttribute("step", "3600");
        } else if (resolution == TimeResolution.MINUTE) {
            getElement().setAttribute("step", "60");
        } else if (resolution == TimeResolution.SECOND) {
            getElement().setAttribute("step", "1");
        }
        setSynchronizedEvent("change");
    }

    private static SerializableFunction<String, LocalTime> parseTime() {
        return value -> value.isEmpty() ? null : LocalTime.parse(value);
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

}
