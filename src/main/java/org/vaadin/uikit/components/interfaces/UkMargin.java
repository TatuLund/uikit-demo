package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkMargin extends HasElement {

    public enum MarginSize {
        // @formatter:off
        DEFAULT("uk-margin"),
        SMALL("uk-margin-small"),
        MEDIUM("uk-margin-medium"),
        LARGE("uk-margin-large"),
        XLARGE("uk-margin-xlarge");
        // @formatter:on

        private final String margin;

        MarginSize(String margin) {
            this.margin = margin;
        }

        public String getMargin() {
            return margin;
        }
    }

    public enum MarginSide {
        // @formatter:off
        TOP("-top"),
        BOTTOM("-bottom"),
        LEFT("-left"),
        RIGHT("-right");
        // @formatter:on

        private final String margin;

        MarginSide(String margin) {
            this.margin = margin;
        }

        public String getMarginSide() {
            return margin;
        }
    }

    default void setMargin() {
        clearMargin();
        getElement().getClassList().add(MarginSize.DEFAULT.getMargin());
    }

    default void setMargin(MarginSize marginSize) {
        clearMargin();
        getElement().getClassList().add(marginSize.getMargin());
    }

    default void setMargin(MarginSize marginSize, MarginSide marginSide) {
        getElement().getClassList().forEach(margin -> {
            if (margin.startsWith(marginSize.getMargin())) {
                getElement().getClassList().remove(margin);
            }
        });
        getElement().getClassList()
                .add(marginSize.getMargin() + marginSide.getMarginSide());
    }

    default void clearMargin() {
        getElement().getClassList().forEach(margin -> {
            if (margin.startsWith("uk-margin")) {
                getElement().getClassList().remove(margin);
            }
        });
    }
}
