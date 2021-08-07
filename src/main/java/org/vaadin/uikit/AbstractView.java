package org.vaadin.uikit;

import org.vaadin.uikit.components.layout.UkFlex;

abstract class AbstractView extends UkFlex {

    public AbstractView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setOverflow(OverflowMode.AUTO);
        setSizeFull();

    }
}
