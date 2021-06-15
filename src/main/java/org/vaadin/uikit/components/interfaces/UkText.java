package org.vaadin.uikit.components.interfaces;

import org.vaadin.uikit.components.interfaces.UkSizing.FixedWidth;

import com.vaadin.flow.component.HasElement;

public interface UkText extends HasElement {

    public enum FontSize {
        SMALL("uk-text-small"),
        DEFAULT(null),
        LARGE("uk-text-large");

        private String fontSize;

        FontSize(String fontSize) {
            this.fontSize = fontSize;
        }

        public String getFontSize() {
            return fontSize;
        }
    }

    default void setFontSize(FontSize fontSize) {
        for (FontSize size : FontSize.values()) {
            if (size.getFontSize() != null)
                getElement().getClassList().remove(size.getFontSize());
        }
        if (fontSize != FontSize.DEFAULT)
            getElement().getClassList().add(fontSize.getFontSize());
    }

    public enum FontWeight {
        LIGHT("uk-text-light"),
        NORMAL("uk-text-normal"),
        BOLD("uk-text-bold"),
        LIGHTER("uk-text-lighter"),
        BOLDER("uk-text-bolder");

        private String fontWeight;

        FontWeight(String fontWeight) {
            this.fontWeight = fontWeight;
        }

        public String getFontWeigth() {
            return fontWeight;
        }
    }

    default void setFontWeigth(FontWeight fontWeight) {
        for (FontWeight weight : FontWeight.values()) {
            getElement().getClassList().remove(weight.getFontWeigth());
        }
        getElement().getClassList().add(fontWeight.getFontWeigth());
    }

    public enum TextAlignment {
        LEFT("uk-text-left"),
        RIGH("uk-text-right"),
        CENTER("uk-text-center"),
        JUSTIFY("uk-text-justify");

        private String textAlignment;

        TextAlignment(String textAlignment) {
            this.textAlignment = textAlignment;
        }

        public String getAlignment() {
            return textAlignment;
        }
    }

    default void setTextAlignment(TextAlignment textAlignment) {
        for (TextAlignment alignment : TextAlignment.values()) {
            getElement().getClassList().remove(alignment.getAlignment());
        }
        getElement().getClassList().add(textAlignment.getAlignment());
    }

    public enum TextVerticalAlignment {
        TOP("uk-text-top"), MIDDLE("uk-text-middle"), BOTTOM(
                "uk-text-bottom"), BASELINE("uk-text-baseline");

        private String textAlignment;

        TextVerticalAlignment(String textAlignment) {
            this.textAlignment = textAlignment;
        }

        public String getAlignment() {
            return textAlignment;
        }
    }

    default void setTextVerticalAlignment(TextVerticalAlignment textAlignment) {
        for (TextVerticalAlignment alignment : TextVerticalAlignment.values()) {
            getElement().getClassList().remove(alignment.getAlignment());
        }
        getElement().getClassList().add(textAlignment.getAlignment());
    }

    default void setTruncate(boolean truncate) {
        if (truncate) {
            getElement().getClassList().add("uk-text-truncate");
        } else {
            getElement().getClassList().remove("uk-text-truncate");
        }
    }

    default void setBreakText(boolean breakText) {
        if (breakText) {
            getElement().getClassList().add("uk-text-break");
        } else {
            getElement().getClassList().remove("uk-text-break");
        }
    }

    default void setNoWrap(boolean noWrap) {
        if (noWrap) {
            getElement().getClassList().add("uk-text-nowrap");
        } else {
            getElement().getClassList().remove("uk-text-nowrap");
        }
    }
}
