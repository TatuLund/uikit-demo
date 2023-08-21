package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkText extends HasElement {

    public enum TextStyle {
        // @formatter:off
        SMALL("uk-text-lead"),
        DEFAULT(null),
        LARGE("uk-text-meta");
        // @formatter:on

        private String textStyle;

        TextStyle(String textStyle) {
            this.textStyle = textStyle;
        }

        public String getTextStyle() {
            return textStyle;
        }
    }

    default void setTextStyle(TextStyle textStyle) {
        for (TextStyle style : TextStyle.values()) {
            if (style.getTextStyle() != null)
                getElement().getClassList().remove(style.getTextStyle());
        }
        if (textStyle != TextStyle.DEFAULT)
            getElement().getClassList().add(textStyle.getTextStyle());
    }

    public enum FontSize {
        // @formatter:off
        SMALL("uk-text-small"),
        DEFAULT(null),
        LARGE("uk-text-large");
        // @formatter:on

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

    public enum Transform {
        // @formatter:off
        CAPITALIZE("uk-text-capitalize"),
        UPPERCASE("uk-text-uppercase"),
        LOWERCASE("uk-text-lowercase");
        // @formatter:on

        private String transform;

        Transform(String transform) {
            this.transform = transform;
        }

        public String getTransform() {
            return transform;
        }
    }

    default void setTransform(Transform transform) {
        for (Transform trans : Transform.values()) {
            getElement().getClassList().remove(trans.getTransform());
        }
        getElement().getClassList().add(transform.getTransform());
    }

    public enum TextColor {
        // @formatter:off
        MUTED("uk-text-muted"),
        EMPHASIS("uk-text-emphasis"),
        PRIMARY("uk-text-primary"),
        SECONDARY("uk-text-secondary"),
        SUCCESS("uk-text-success"),
        WARNING("uk-text-warning"),
        BOLDER("uk-text-danger");
        // @formatter:on

        private String textColor;

        TextColor(String textColor) {
            this.textColor = textColor;
        }

        public String getTextColor() {
            return textColor;
        }
    }

    default void setTextColor(TextColor textColor) {
        for (TextColor color : TextColor.values()) {
            getElement().getClassList().remove(color.getTextColor());
        }
        getElement().getClassList().add(textColor.getTextColor());
    }

    public enum FontWeight {
        // @formatter:off
        LIGHT("uk-text-light"),
        NORMAL("uk-text-normal"),
        BOLD("uk-text-bold"),
        LIGHTER("uk-text-lighter"),
        BOLDER("uk-text-bolder");
        // @formatter:on

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

    default void setItalic(boolean italic) {
        if (italic) {
            getElement().getClassList().add("uk-text-italic");
        } else {
            getElement().getClassList().remove("uk-text-italic");
        }
    }

    public enum TextAlignment {
        // @formatter:off
        LEFT("uk-text-left"),
        RIGHT("uk-text-right"),
        CENTER("uk-text-center"),
        JUSTIFY("uk-text-justify");
        // @formatter:on

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
        // @formatter:off
        TOP("uk-text-top"),
        MIDDLE("uk-text-middle"),
        BOTTOM("uk-text-bottom"), 
        BASELINE("uk-text-baseline");
        // @formatter:on

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
