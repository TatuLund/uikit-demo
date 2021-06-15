package org.vaadin.uikit.components.interfaces;

import com.vaadin.flow.function.ValueProvider;

@FunctionalInterface
public interface StringProvider<T> extends ValueProvider<T, String> {

    /**
     * Gets a caption for the {@code item}.
     *
     * @param item
     *            the item to get caption for
     * @return the caption of the item, not {@code null}
     */
    @Override
    String apply(T item);
}