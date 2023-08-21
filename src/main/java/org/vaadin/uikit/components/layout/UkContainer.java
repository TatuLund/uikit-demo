package org.vaadin.uikit.components.layout;

import org.vaadin.uikit.components.interfaces.UkOverflow;
import org.vaadin.uikit.components.interfaces.UkPadding;

import com.vaadin.flow.component.html.Div;

/**
 * Use to a block element to give it a max-width and wrap the main content of
 * your website. The element will be centered and have padding on the sides,
 * that adapts automatically for large screens.
 * 
 * NOTE: The padding of nested containers will be removed to avoid unnecessary
 * spacing.
 */
@SuppressWarnings("serial")
public class UkContainer extends Div implements UkPadding, UkOverflow {

    public enum ContainerMaxWidth {
        // @formatter:off
        XSMALL("uk-container-xsmall"),
        SMALL("uk-container-small"),
        LARGE("uk-container-large"),
        XLARGE("uk-container-xlarge"),
        EXPAND("uk-container-expand");
        // @formatter:on

        private final String maxWidth;

        ContainerMaxWidth(String maxWidth) {
            this.maxWidth = maxWidth;
        }

        public String getMaxWidth() {
            return maxWidth;
        }
    }

    public UkContainer() {
        addClassName("uk-container");
    }

    public void setMaxWidth(ContainerMaxWidth maxWidth) {
        for (ContainerMaxWidth mx : ContainerMaxWidth.values()) {
            removeClassName(mx.getMaxWidth());
        }
        addClassName(maxWidth.getMaxWidth());
    }
}
