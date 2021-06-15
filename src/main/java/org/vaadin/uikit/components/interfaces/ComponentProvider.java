package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.ValueProvider;

public interface ComponentProvider<T> extends ValueProvider<T, Component> {

    /**
     * Gets a caption for the {@code item}.
     *
     * @param item
     *            the item to get caption for
     * @return the caption of the item, not {@code null}
     */
    @Override
    Component apply(T item);
}
