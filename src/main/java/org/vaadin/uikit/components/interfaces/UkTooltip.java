package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkTooltip extends HasElement {

    public enum TooltipPosition {
        // @formatter:off
        TOP("top"),
        TOP_LEFT("top-left"),
        TOP_RIGHT("top-right"),
        BOTTOM("bottom"),
        BOTTOM_LEFT("bottom-left"),
        BOTTOM_RIGHT("bottom-right"),
        LEFT("left"),
        RIGHT("right");
    	// @formatter:on

        private String tooltipPosition;

        TooltipPosition(String tooltipPosition) {
            this.tooltipPosition = tooltipPosition;
        }

        public String getToooltipPosition() {
            return tooltipPosition;
        }
    }

    public default void setTooltip(String tooltip) {
        if (tooltip != null && !tooltip.isEmpty()) {
            getElement().setAttribute("uk-tooltip", tooltip);
        } else {
            getElement().removeAttribute("uk-tooltip");
        }
    }

    public default void removeTooltip() {
        setTooltip(null);
    }

    public default void setTooltip(String tooltip, TooltipPosition position) {
        if (tooltip != null && !tooltip.isEmpty() && position != null) {
            getElement().setAttribute("uk-tooltip", "title: " + tooltip
                    + "; pos: " + position.getToooltipPosition());
        } else {
            throw new IllegalArgumentException("Missing tooltip or position");
        }

    }
}
