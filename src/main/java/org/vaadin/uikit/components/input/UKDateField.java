package org.vaadin.uikit.components.input;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.WeekFields;
import java.util.Optional;

import org.vaadin.uikit.interfaces.UKFormSizing;
import org.vaadin.uikit.interfaces.UKTooltip;
import org.vaadin.uikit.interfaces.UKValidation;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.server.WebBrowser;

@Tag(Tag.INPUT)
public class UKDateField extends AbstractSinglePropertyField<UKDateField, LocalDate>
        implements UKValidation, HasStyle, Focusable<UKDateField>, UKTooltip,
        UKFormSizing {

    private static final PropertyDescriptor<String, Optional<String>> placeholderDescriptor = PropertyDescriptors
            .optionalAttributeWithDefault("placeholder", "");
    
    public enum Resolution {
        DAY, WEEK, MONTH;
    }

    private Resolution resolution;

    public UKDateField() {
        this(Resolution.DAY);
    }

    public UKDateField(Resolution resolution) {
        super("value", LocalDate.now(), String.class, parseDate(resolution),
                value -> "" + value);
        addClassName("uk-input");
        this.resolution = resolution;
        getElement().synchronizeProperty("value", "change");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        if (hasWeekMonth() && resolution == Resolution.MONTH) {
            getElement().setAttribute("type", "month");
        } else if (hasWeekMonth() && resolution == Resolution.WEEK) {
            getElement().setAttribute("type", "week");
        } else {
            getElement().setAttribute("type", "date");
        }        
    }

    private static boolean hasWeekMonth() {
        WebBrowser browser = UI.getCurrent().getSession().getBrowser();
        boolean weekMonth = (browser.isChrome() || browser.isEdge() || browser.isOpera());   
        return weekMonth;
    }

    private static SerializableFunction<String, LocalDate> parseDate(Resolution resolution) {
        if (hasWeekMonth() && resolution == Resolution.MONTH) {
            return value -> value.isEmpty() ? null : LocalDate.from(YearMonth.parse(value).atDay(1));
        } else if (hasWeekMonth() && resolution == Resolution.WEEK) {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("YYYY-'W'ww")
                    .parseDefaulting(WeekFields.ISO.dayOfWeek(), DayOfWeek.SUNDAY.getValue())
                    .toFormatter();
            return value -> value.isEmpty() ? null : LocalDate.parse(value,formatter);
        } else {
            return value -> value.isEmpty() ? null : LocalDate.parse(value);
        }
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
