package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkOverflow extends HasElement {

    public enum OverflowMode {
        HIDDEN("uk-overflow-hidden"), 
        AUTO("uk-overflow-auto");

        private final String overflow;

        OverflowMode(String overflow) {
            this.overflow = overflow;
        }

        public String getOverflow() {
            return overflow;
        }
    }    

    default void setOverflow(OverflowMode overflowMode) {
        clearOverflow();
        getElement().getClassList().add(overflowMode.getOverflow());
    }

    default void clearOverflow() {
        getElement().getClassList().remove(OverflowMode.HIDDEN.getOverflow());
        getElement().getClassList().remove(OverflowMode.AUTO.getOverflow());
    }
}
