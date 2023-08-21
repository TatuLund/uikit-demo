package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkSizing extends HasElement {

    public enum FixedWidth {
        // @formatter:off
        SMALL("uk-width-small"),
        MEDIUM("uk-width-medium"),
        LARGE("uk-width-large"),
        XLARGE("uk-width-xlarge"),
        XXLARGE("uk-width-2xlarge");
        // @formatter:on

        private final String width;

        FixedWidth(String width) {
            this.width = width;
        }

        public String getWidthName() {
            return width;
        }
    }

    default void setWidth(FixedWidth width) {
        clearWidth();
        getElement().getClassList().add(width.getWidthName());
    }

    default void clearWidth() {
        getElement().getClassList().forEach(w -> {
            if (w.startsWith("uk-width")) {
                getElement().getClassList().remove(w);
            }
        });
    }

    default void setWidth(int share, int max) {
        clearWidth();
        if (max % share == 0) {
            max = max / share;
            share = 1;
        }
        getElement().getClassList().add("uk-width-" + share + "-" + max);
    }

    public enum FixedHeight {
        // @formatter:off
        HEIGHT100("uk-height-1-1"),
        SMALL("uk-height-small"),
        MEDIUM("uk-height-medium"),
        LARGE("uk-height-large");
        // @formatter:on

        private final String height;

        FixedHeight(String height) {
            this.height = height;
        }

        public String getHeightName() {
            return height;
        }
    }

    default void setHeight(FixedHeight height) {
        clearHeight();
        getElement().getClassList().add(height.getHeightName());
    }

    default void clearHeight() {
        getElement().getClassList().forEach(height -> {
            if (height.startsWith("uk-height")) {
                getElement().getClassList().remove(height);
            }
        });
    }
}
