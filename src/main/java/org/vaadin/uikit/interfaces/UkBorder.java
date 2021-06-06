package org.vaadin.uikit.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkBorder extends HasElement {

    public enum BorderStyle {
        SHARP(null),
        ROUNDED("uk-border-rounded"),
        PILL("uk-border-pill"),
        CIRCLE("uk-border-circle");

        private final String border;

        BorderStyle(String border) {
            this.border = border;
        }

        public String getBorder() {
            return border;
        }
    }

    default void setBorder(BorderStyle borderStyle) {
        for (BorderStyle border : BorderStyle.values()) {
            if (border.getBorder() != null) {
                getElement().getClassList().remove(border.getBorder());
            }
        }
        if (borderStyle != BorderStyle.SHARP) {
            getElement().getClassList().add(borderStyle.getBorder());
        }
    }
}
