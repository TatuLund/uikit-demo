package org.vaadin.uikit.components.interfaces;

import java.util.Objects;

import com.vaadin.flow.component.HasElement;

public interface UkBoxShadow extends HasElement {

    public enum BoxShadow {
        // @formatter:off
        SMALL("uk-box-shadow-small"),
        MEDIUM("uk-box-shadow-medium"),
        LARGE("uk-box-shadow-large"),
        XLARGE("uk-box-shadow-xlarge");
        // @formatter:on

        private final String shadow;

        BoxShadow(String shadow) {
            this.shadow = shadow;
        }

        public String getShadow() {
            return shadow;
        }
    }

    public enum BoxShadowHover {
        // @formatter:off
        SMALL("uk-box-shadow-hover-small"),
        MEDIUM("uk-box-shadow-hover-medium"),
        LARGE("uk-box-shadow-hover-large"),
        XLARGE("uk-box-shadow-hover-xlarge");
        // @formatter:on

        private final String shadow;

        BoxShadowHover(String shadow) {
            this.shadow = shadow;
        }

        public String getShadow() {
            return shadow;
        }
    }

    default void setBoxShadow(BoxShadow shadow) {
        Objects.requireNonNull(shadow);
        clearBoxShadow();
        getElement().getClassList().add(shadow.getShadow());
    }

    default void setBoxShadow(BoxShadowHover shadow) {
        Objects.requireNonNull(shadow);
        clearBoxShadow();
        getElement().getClassList().add(shadow.getShadow());
    }

    default void setBoxShadow(BoxShadow shadow, BoxShadowHover hoverShadow) {
        Objects.requireNonNull(shadow);
        Objects.requireNonNull(hoverShadow);
        clearBoxShadow();
        getElement().getClassList().add(shadow.getShadow());
        getElement().getClassList().add(hoverShadow.getShadow());
    }

    default void setBoxShadowBottom(boolean bottom) {
        if (bottom) {
            getElement().getClassList().add("uk-box-shadow-bottom");
        } else {
            getElement().getClassList().remove("uk-box-shadow-bottom");
        }
    }

    default void clearBoxShadow() {
        for (BoxShadow hidden : BoxShadow.values()) {
            getElement().getClassList().remove(hidden.getShadow());
        }
        for (BoxShadowHover hidden : BoxShadowHover.values()) {
            getElement().getClassList().remove(hidden.getShadow());
        }
    }
}
