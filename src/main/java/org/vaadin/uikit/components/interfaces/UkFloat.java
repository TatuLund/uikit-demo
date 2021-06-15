package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkFloat extends HasElement {

    public enum FloatStyle {
        NONE(null),
        RIGHT("uk-float-right"),
        LEFT("uk-float-left");

        private final String floatStyle;

        FloatStyle(String floatStyle) {
            this.floatStyle = floatStyle;
        }

        public String getFloat() {
            return floatStyle;
        }
    }

    default void setFloat(FloatStyle floatStyle) {
        for (FloatStyle fs : FloatStyle.values()) {
            if (fs.getFloat() != null) {
                getElement().getClassList().remove(fs.getFloat());
            }
        }
        if (floatStyle != FloatStyle.NONE) {
            getElement().getClassList().add(floatStyle.getFloat());
        }
    }
}
