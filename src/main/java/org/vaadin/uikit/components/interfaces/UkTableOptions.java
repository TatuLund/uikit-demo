package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.HasElement;

public interface UkTableOptions extends HasElement {

    default void setStripes(boolean stripes) {
        if (stripes) {
            getElement().getClassList().add("uk-table-striped");
        } else {
            getElement().getClassList().remove("uk-table-striped");
        }
    }

    default void setDivider(boolean divider) {
        if (divider) {
            getElement().getClassList().add("uk-table-divider");
        } else {
            getElement().getClassList().remove("uk-table-divider");
        }
    }

    default void setJustify(boolean justify) {
        if (justify) {
            getElement().getClassList().add("uk-table-justify");
        } else {
            getElement().getClassList().remove("uk-table-justify");
        }
    }

    default void setMiddle(boolean middle) {
        if (middle) {
            getElement().getClassList().add("uk-table-middle");
        } else {
            getElement().getClassList().remove("uk-table-middle");
        }
    }

    default void setSmall(boolean small) {
        if (small) {
            getElement().getClassList().remove("uk-table-large");
            getElement().getClassList().add("uk-table-small");
        } else {
            getElement().getClassList().remove("uk-table-small");
            getElement().getClassList().add("uk-table-large");
        }
    }
}
