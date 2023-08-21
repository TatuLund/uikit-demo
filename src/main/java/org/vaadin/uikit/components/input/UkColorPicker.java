package org.vaadin.uikit.components.input;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JavaScript;

@SuppressWarnings("serial")
@Tag(Tag.INPUT)
@JavaScript("context://canvas-color.js")
public class UkColorPicker
        extends AbstractSinglePropertyField<UkColorPicker, String>
        implements HasStyle, Focusable<UkColorPicker>, UkField {

    public UkColorPicker() {
        super("value", "", false);
        getElement().setAttribute("type", "color");
        addClassNames("uk-input");
        setWidth(FieldWidth.SMALL);
        // By default AbstractSinglePropertyField listens to a "value-changed"
        // event,
        // but input type=color fires "change"
        setSynchronizedEvent("change");
    }

    @Override
    public void setValue(String value) {
        if (value.matches("#......")) {
            super.setValue(value);
        } else {
            getElement().executeJs("return colorToHex($0)", value)
                    .then(String.class, result -> {
                        super.setValue(result);
                    });
        }
    }

    public void setDisabled(boolean disabled) {
        this.getElement().setProperty("disabled", disabled);
    }

    public boolean isDisabled() {
        return getElement().getProperty("disabled", false);
    }
}
