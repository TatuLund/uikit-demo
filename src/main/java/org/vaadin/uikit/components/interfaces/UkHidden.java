package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.dom.Element;

/**
 * Use responsive visibility classes to hide elements on different devices.
 *
 */
public interface UkHidden extends HasElement {

    public enum Hidden {
        // @formatter:off
        /**
         * Hides the element on any device. This is more of a legacy class. The recommended way to do this, is to use the hidden attribute.
         */
        HIDDEN("uk-hidden"),
        /**
         * Hides the element without removing it from the document flow.
         */
        INVISIBLE("uk-invisible"),
        /**
         * Hide when for device widths of 640px and larger.
         */
        VISIBLE_S("uk-visible@s"),
        /**
         * Show when for device widths of 960px and larger.
         */
        VISIBLE_M("uk-visible@m"),
        /**
         * Show when for device widths of 1200px and larger.
         */
        VISIBLE_L("uk-visible@l"),
        /**
         * Show when for device widths of 1600px and larger.
         */
        VISIBLE_XL("uk-visible@xl"),
        /**
         * Set the component hidden if the parent is not hovered
         */
        HIDDEN_HOVER("uk-hidden-hover"),
        /**
         * Set the component invisible if the parent is not hovered
         */
        INVISIBLE_HOVER("uk-invisible-hover");
        // @formatter:on

        private final String hidden;

        Hidden(String hidden) {
            this.hidden = hidden;
        }

        public String getHidden() {
            return hidden;
        }
    }

    default void setHidden(Hidden hidden) {
        clearHidden();
        getElement().getClassList().add(hidden.getHidden());
        if (hidden == Hidden.HIDDEN_HOVER || hidden == Hidden.INVISIBLE_HOVER) {
            getElement().getNode().runWhenAttached(ui -> {
                Element parent = getElement().getParent();
                parent.getClassList().add("uk-visible-toggle");
                parent.setAttribute("tabindex", "0");
            });
        }
    }

    default void clearHidden() {
        for (Hidden hidden : Hidden.values()) {
            getElement().getClassList().remove(hidden.getHidden());
        }
    }
}
