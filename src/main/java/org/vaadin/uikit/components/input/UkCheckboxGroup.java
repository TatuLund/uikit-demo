package org.vaadin.uikit.components.input;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.vaadin.uikit.components.interfaces.StringProvider;
import org.vaadin.uikit.components.interfaces.UkFormSizing;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkTooltip;
import org.vaadin.uikit.components.interfaces.UkValidation;
import org.vaadin.uikit.components.interfaces.UkBorder.BorderStyle;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.binder.HasDataProvider;
import com.vaadin.flow.data.binder.HasItemsAndComponents;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.KeyMapper;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.data.selection.MultiSelectionEvent;
import com.vaadin.flow.data.selection.MultiSelectionListener;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.shared.Registration;

import elemental.json.Json;
import elemental.json.JsonArray;

@SuppressWarnings("serial")
@Tag(Tag.DIV)
public class UkCheckboxGroup<T> extends SelectBase<UkCheckboxGroup<T>, Set<T>>
        implements HasItemsAndComponents<T>, HasSize, HasValidation,
        MultiSelect<UkCheckboxGroup<T>, T>, HasDataProvider<T>, UkValidation,
        UkTooltip, UkFormSizing, UkMargin, UkPadding {

    private static final String VALUE = "value";

    private final KeyMapper<T> keyMapper = new KeyMapper<>(this::getItemId);

    private DataProvider<T, ?> dataProvider = DataProvider.ofItems();

    private boolean isReadOnly;

    private SerializablePredicate<T> itemEnabledProvider = item -> isEnabled();

    private StringProvider<T> itemLabelGenerator = String::valueOf;

    private Registration dataProviderListenerRegistration;

    private BorderStyle borderStyle = BorderStyle.SHARP;

    private class CheckboxItem<R> extends Composite<Div>
            implements ItemComponent<T> {

        private final R item;
        private Div div = new Div();
        private Span text = new Span();
        private UkCheckbox checkbox = new UkCheckbox();

        private CheckboxItem(String id, R item) {
            this.item = item;
            checkbox.getElement().setProperty(VALUE, id);
            checkbox.setBorder(borderStyle);
            text.addClassName("uk-margin-small-left");
            checkbox.addValueChangeListener(event -> {
                if (event.getValue()) {
                    JsonArray value = (JsonArray) UkCheckboxGroup.this
                            .getElement().getPropertyRaw("value");
                    Set<R> set = (Set<R>) presentationToModel(
                            UkCheckboxGroup.this, value);
                    set.add(item);
                    JsonArray newValue = modelToPresentation(
                            UkCheckboxGroup.this, (Set<T>) set);
                    UkCheckboxGroup.this.getElement().setPropertyJson("value",
                            newValue);
                } else {
                    JsonArray value = (JsonArray) UkCheckboxGroup.this
                            .getElement().getPropertyRaw("value");
                    Set<R> set = (Set<R>) presentationToModel(
                            UkCheckboxGroup.this, value);
                    set.remove(item);
                    JsonArray newValue = modelToPresentation(
                            UkCheckboxGroup.this, (Set<T>) set);
                    UkCheckboxGroup.this.getElement().setPropertyJson("value",
                            newValue);
                }
            });
        }

        public boolean getValue() {
            return checkbox.getValue();
        }

        public void setValue(boolean value) {
            checkbox.setValue(value);
        }

        public void setLabel(String label) {
            text.setText(label);
        }

        public UkCheckbox getCheckbox() {
            return checkbox;
        }

        public void setDisabled(boolean disabled) {
            checkbox.setDisabled(disabled);
        }

        @Override
        protected Div initContent() {
            div.add(checkbox, text);
            return div;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T getItem() {
            return (T) item;
        }

    }

    public UkCheckboxGroup() {
        this(-1);
    }

    public UkCheckboxGroup(int columns) {
        super(Collections.emptySet(), Collections.emptySet(), JsonArray.class,
                UkCheckboxGroup::presentationToModel,
                UkCheckboxGroup::modelToPresentation);
        if (columns > 0) {
            getElement().setAttribute("uk-grid", true);
            addClassNames("uk-flex-left", "uk-grid-collapse",
                    "uk-child-width-1-" + columns);
        }
    }

    private static <T> Set<T> presentationToModel(UkCheckboxGroup<T> group,
            JsonArray presentation) {
        JsonArray array = presentation;
        Set<T> set = new HashSet<>();
        for (int i = 0; i < array.length(); i++) {
            set.add(group.keyMapper.get(array.getString(i)));
        }
        return set;
    }

    private static <T> JsonArray modelToPresentation(UkCheckboxGroup<T> group,
            Set<T> model) {
        JsonArray array = Json.createArray();
        if (model.isEmpty()) {
            return array;
        }

        model.stream().map(group.keyMapper::key)
                .forEach(key -> array.set(array.length(), key));
        return array;
    }

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
                        T otherItem = ((DataChangeEvent.DataRefreshEvent<T>) event)
                                .getItem();
                        this.getCheckboxItems()
                                .filter(item -> Objects.equals(
                                        getItemId(item.item),
                                        getItemId(otherItem)))
                                .findFirst().ifPresent(this::updateCheckbox);
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

    public void setItemLabelGenerator(
            StringProvider<T> itemLabelGenerator) {
        Objects.requireNonNull(itemLabelGenerator,
                "The item label generator can not be null");
        this.itemLabelGenerator = itemLabelGenerator;
        reset();
    }

    private void reset() {
        // Cache helper component before removal
        keyMapper.removeAll();
        removeAll();
        clear();

        getDataProvider().fetch(new Query<>()).map(this::createCheckbox)
                .forEach(this::add);
    }

    @Override
    public void updateSelection(Set<T> addedItems, Set<T> removedItems) {
        Set<T> value = new HashSet<>(getValue());
        value.addAll(addedItems);
        value.removeAll(removedItems);
        setValue(value);
    }

    @Override
    public Set<T> getSelectedItems() {
        return getValue();
    }

    @Override
    public Registration addSelectionListener(
            MultiSelectionListener<UkCheckboxGroup<T>, T> listener) {
        return addValueChangeListener(event -> listener
                .selectionChange(new MultiSelectionEvent<>(this, this,
                        event.getOldValue(), event.isFromClient())));
    }

    public DataProvider<T, ?> getDataProvider() {
        return dataProvider;
    }

    @Override
    public void onEnabledStateChanged(boolean enabled) {
        if (isReadOnly()) {
            setDisabled(true);
        } else {
            setDisabled(!enabled);
        }
        getCheckboxItems().forEach(this::updateEnabled);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
        if (isEnabled()) {
            setDisabled(readOnly);
            refreshCheckboxes();
        }
    }    

    @Override
    public boolean isReadOnly() {
        return isReadOnly;
    }

    public SerializablePredicate<T> getItemEnabledProvider() {
        return itemEnabledProvider;
    }

    private void refreshCheckboxes() {
        getCheckboxItems().forEach(this::updateCheckbox);
    }

    public void setItemEnabledProvider(
            SerializablePredicate<T> itemEnabledProvider) {
        this.itemEnabledProvider = Objects.requireNonNull(itemEnabledProvider);
        refreshCheckboxes();
    }

    @SuppressWarnings("unchecked")
    private Stream<CheckboxItem<T>> getCheckboxItems() {
        return getChildren().filter(CheckboxItem.class::isInstance)
                .map(child -> (CheckboxItem<T>) child);
    }

    private CheckboxItem<T> createCheckbox(T item) {
        CheckboxItem<T> checkbox = new CheckboxItem<>(keyMapper.key(item),
                item);
        updateCheckbox(checkbox);
        return checkbox;
    }

    public StringProvider<T> getItemLabelGenerator() {
        return itemLabelGenerator;
    }

    public void setCheckboxBorder(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
        getCheckboxItems().forEach(checkbox -> checkbox.getCheckbox().setBorder(borderStyle));
    }

    private void updateCheckbox(CheckboxItem<T> checkbox) {
        checkbox.setLabel(getItemLabelGenerator().apply(checkbox.getItem()));
        checkbox.setValue(getValue().stream().anyMatch(
                selectedItem -> Objects.equals(getItemId(selectedItem),
                        getItemId(checkbox.getItem()))));
        updateEnabled(checkbox);
    }

    private void updateEnabled(CheckboxItem<T> checkbox) {
        boolean disabled = isDisabledBoolean()
                || !getItemEnabledProvider().test(checkbox.getItem());
        Serializable rawValue = checkbox.getCheckbox().getElement()
                .getPropertyRaw("disabled");
        if (rawValue instanceof Boolean) {
            // convert the boolean value to a String to force update the
            // property value. Otherwise since the provided value is the same as
            // the current one the update don't do anything.
            checkbox.getElement().setProperty("disabled",
                    disabled ? Boolean.TRUE.toString() : null);
        } else {
            checkbox.setDisabled(disabled);
        }
    }

    private Object getItemId(T item) {
        if (getDataProvider() == null) {
            return item;
        }
        return getDataProvider().getId(item);
    }
}
