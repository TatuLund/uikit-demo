package org.vaadin.uikit.components.input;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

import org.vaadin.uikit.interfaces.UKFormSizing;
import org.vaadin.uikit.interfaces.UKMargin;
import org.vaadin.uikit.interfaces.UKPadding;
import org.vaadin.uikit.interfaces.UKTooltip;
import org.vaadin.uikit.interfaces.UKValidation;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.data.binder.HasDataProvider;
import com.vaadin.flow.data.binder.HasItemsAndComponents;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.KeyMapper;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.shared.Registration;

@Tag(Tag.SELECT)
public class UKSelect<T> extends SelectBase<UKSelect<T>, T>
        implements HasItemsAndComponents<T>, SingleSelect<UKSelect<T>, T>,
        Focusable<UKSelect<T>>, HasDataProvider<T>, UKValidation, UKTooltip,
        UKFormSizing, UKMargin, UKPadding {

    private final KeyMapper<T> keyMapper = new KeyMapper<>();

    private DataProvider<T, ?> dataProvider = DataProvider.ofItems();

    private Registration dataProviderListenerRegistration;

    private SerializablePredicate<T> itemEnabledProvider = item -> isEnabled();

    private ItemLabelGenerator<T> itemLabelGenerator = String::valueOf;

    private boolean isReadOnly;

    private static <T> T presentationToModel(UKSelect<T> select,
            String presentation) {
        if (!select.keyMapper.containsKey(presentation)) {
            return null;
        }
        return select.keyMapper.get(presentation);
    }

    private static <T> String modelToPresentation(UKSelect<T> select, T model) {
        if (!select.keyMapper.has(model)) {
            return null;
        }
        return select.keyMapper.key(model);
    }

    public UKSelect() {
        super(null, null, String.class, UKSelect::presentationToModel,
                UKSelect::modelToPresentation);
        getElement().synchronizeProperty("value", "change");
        addClassName("uk-select");
    }

    public SerializablePredicate<T> getItemEnabledProvider() {
        return itemEnabledProvider;
    }

    public void setItemEnabledProvider(
            SerializablePredicate<T> itemEnabledProvider) {
        this.itemEnabledProvider = Objects.requireNonNull(itemEnabledProvider);
        refreshOptions();
    }

    private void refreshOptions() {
        getOptions().forEach(this::updateOption);
    }

    @SuppressWarnings("unchecked")
    private Stream<Option<T>> getOptions() {
        return getChildren().filter(Option.class::isInstance)
                .map(child -> (Option<T>) child);
    }

    private void updateOption(Option<T> option) {
        String label = getItemLabelGenerator().apply(option.getItem());
        option.setLabel(label);
        updateEnabled(option);
    }

    private void updateEnabled(Option<T> option) {
        boolean disabled = isDisabledBoolean()
                || !getItemEnabledProvider().test(option.getItem());
        Serializable rawValue = option.getElement().getPropertyRaw("disabled");
        if (rawValue instanceof Boolean) {
            // convert the boolean value to a String to force update the
            // property value. Otherwise since the provided value is the same as
            // the current one the update don't do anything.
            option.getElement().setProperty("disabled",
                    disabled ? Boolean.TRUE.toString() : null);
        } else {
            option.setDisabled(disabled);
        }
    }

    @Override
    public void onEnabledStateChanged(boolean enabled) {
        if (isReadOnly()) {
            setDisabled(true);
        } else {
            setDisabled(!enabled);
        }
        refreshOptions();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
        if (isEnabled()) {
            setDisabled(readOnly);
            refreshOptions();
        }
    }

    @Override
    public boolean isReadOnly() {
        return isReadOnly;
    }

    @Override
    public void setDataProvider(DataProvider<T, ?> dataProvider) {
        this.dataProvider = dataProvider;
        reset();

        setupDataProviderListener(dataProvider);
    }

    private void setupDataProviderListener(DataProvider<T, ?> dataProvider) {
        if (dataProviderListenerRegistration != null) {
            dataProviderListenerRegistration.remove();
        }
        dataProviderListenerRegistration = dataProvider
                .addDataProviderListener(event -> {
                    if (event instanceof DataChangeEvent.DataRefreshEvent) {
                        resetOption(
                                ((DataChangeEvent.DataRefreshEvent<T>) event)
                                        .getItem());
                    } else {
                        reset();
                    }
                });
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if (getDataProvider() != null
                && dataProviderListenerRegistration == null) {
            setupDataProviderListener(getDataProvider());
        }
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        if (dataProviderListenerRegistration != null) {
            dataProviderListenerRegistration.remove();
            dataProviderListenerRegistration = null;
        }
        super.onDetach(detachEvent);
    }

    private void resetOption(T item) {
        getOptions()
                .filter(radioButton -> getDataProvider()
                        .getId(radioButton.getItem())
                        .equals(getDataProvider().getId(item)))
                .findFirst().ifPresent(this::updateOption);
    }

    private void reset() {
        keyMapper.removeAll();
        removeAll();
        clear();

        getDataProvider().fetch(new Query<>()).map(this::createOption)
                .forEach(this::add);
    }

    public void setItemLabelGenerator(
            ItemLabelGenerator<T> itemLabelGenerator) {
        Objects.requireNonNull(itemLabelGenerator,
                "The item label generator can not be null");
        this.itemLabelGenerator = itemLabelGenerator;
        reset();
    }

    public ItemLabelGenerator<T> getItemLabelGenerator() {
        return itemLabelGenerator;
    }

    private Component createOption(T item) {
        Option<T> option = new Option<>(keyMapper.key(item), item);
        updateOption(option);
        return option;
    }

    public DataProvider<T, ?> getDataProvider() {
        return dataProvider;
    }

    @Tag(Tag.OPTION)
    class Option<R> extends HtmlComponent implements ItemComponent<R> {
        private R item;

        Option(String key, R item) {
            this.item = item;
            getElement().setProperty("value", key);
        }

        public void setLabel(String label) {
            getElement().setText(label);
        }

        @Override
        public R getItem() {
            return item;
        }

        protected boolean isDisabledBoolean() {
            return getElement().getProperty("disabled", false);
        }

        protected void setDisabled(boolean disabled) {
            getElement().setProperty("disabled", disabled);
        }
    }
}
