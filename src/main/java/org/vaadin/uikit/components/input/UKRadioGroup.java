package org.vaadin.uikit.components.input;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
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
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.shared.Registration;

@Tag(Tag.DIV)
public class UKRadioGroup<T> extends SelectBase<UKRadioGroup<T>, T>
        implements HasItemsAndComponents<T>, SingleSelect<UKRadioGroup<T>, T>,
        Focusable<UKRadioGroup<T>>, HasDataProvider<T>, UKValidation, UKTooltip,
        UKFormSizing, UKMargin, UKPadding {

    private final KeyMapper<T> keyMapper = new KeyMapper<>();

    private DataProvider<T, ?> dataProvider = DataProvider.ofItems();

    private Registration dataProviderListenerRegistration;

    private SerializablePredicate<T> itemEnabledProvider = item -> isEnabled();

    private ItemLabelGenerator<T> itemLabelGenerator = String::valueOf;

    private boolean isReadOnly;

    private Random random = new Random();

    private String name = "combo-" + random.nextInt();

    private static <T> T presentationToModel(UKRadioGroup<T> select,
            String presentation) {
        if (!select.keyMapper.containsKey(presentation)) {
            return null;
        }
        return select.keyMapper.get(presentation);
    }

    private static <T> String modelToPresentation(UKRadioGroup<T> select,
            T model) {
        if (!select.keyMapper.has(model)) {
            return null;
        }
        return select.keyMapper.key(model);
    }

    public UKRadioGroup() {
        this(-1);
    }

    public UKRadioGroup(int columns) {
        super(null, null, String.class, UKRadioGroup::presentationToModel,
                UKRadioGroup::modelToPresentation);
        if (columns > 0) {
            getElement().setAttribute("uk-grid", true);
            addClassNames("uk-flex-left","uk-grid-collapse","uk-child-width-1-"+columns);
        }
    }

    public SerializablePredicate<T> getItemEnabledProvider() {
        return itemEnabledProvider;
    }

    public void setItemEnabledProvider(
            SerializablePredicate<T> itemEnabledProvider) {
        this.itemEnabledProvider = Objects.requireNonNull(itemEnabledProvider);
        refreshRadios();
    }

    private void refreshRadios() {
        getRadios().forEach(this::updateRadio);
    }

    @SuppressWarnings("unchecked")
    private Stream<Radio<T>> getRadios() {
        return getChildren().filter(Radio.class::isInstance)
                .map(child -> (Radio<T>) child);
    }

    private void updateRadio(Radio<T> radio) {
        String label = getItemLabelGenerator().apply(radio.getItem());
        radio.setLabel(label);
        updateEnabled(radio);
    }

    private void updateEnabled(Radio<T> radio) {
        boolean disabled = isDisabledBoolean()
                || !getItemEnabledProvider().test(radio.getItem());
        Serializable rawValue = radio.getElement().getPropertyRaw("disabled");
        if (rawValue instanceof Boolean) {
            // convert the boolean value to a String to force update the
            // property value. Otherwise since the provided value is the same as
            // the current one the update don't do anything.
            radio.getElement().setProperty("disabled",
                    disabled ? Boolean.TRUE.toString() : null);
        } else {
            radio.setDisabled(disabled);
        }
    }

    @Override
    public void setValue(T value) {
        if (value != null) {
            super.setValue(value);
            getRadios().forEach(
                    rb -> rb.setChecked(Objects.equals(rb.getItem(), value)));
        }
    }

    @Override
    public void onEnabledStateChanged(boolean enabled) {
        if (isReadOnly()) {
            setDisabled(true);
        } else {
            setDisabled(!enabled);
        }
        refreshRadios();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
        if (isEnabled()) {
            setDisabled(readOnly);
            refreshRadios();
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
                        resetRadio(((DataChangeEvent.DataRefreshEvent<T>) event)
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

    private void resetRadio(T item) {
        getRadios()
                .filter(radioButton -> getDataProvider()
                        .getId(radioButton.getItem())
                        .equals(getDataProvider().getId(item)))
                .findFirst().ifPresent(this::updateRadio);
    }

    private void reset() {
        keyMapper.removeAll();
        removeAll();
        clear();

        getDataProvider().fetch(new Query<>()).map(this::createRadio)
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

    private Component createRadio(T item) {
        Radio<T> option = new Radio<>(keyMapper.key(item), item);
        updateRadio(option);
        return option;
    }

    public DataProvider<T, ?> getDataProvider() {
        return dataProvider;
    }

    protected String getName() {
        return name;
    }

    @Tag(Tag.DIV)
    class Radio<T> extends HtmlComponent implements ItemComponent<T> {
        private T item;
        Element label = new Element(Tag.LABEL);
        Element input = createInput();

        Radio(String key, T item) {
            this.item = item;
            label.getClassList().add("uk-margin-small-left");
            getElement().appendChild(input);
            getElement().appendChild(label);
            input.addEventListener("click", event -> {
                UKRadioGroup.this.getElement().setProperty("value", key);
            });
        }

        protected void setChecked(boolean checked) {
            input.setProperty("checked", checked);
        }

        protected boolean isChecked() {
            return input.getProperty("checked", false);
        }

        private Element createInput() {
            Element input = new Element(Tag.INPUT);
            input.getClassList().add("uk-radio");
            input.setAttribute("type", "radio");
            input.setProperty("name", getName());
            return input;
        }

        protected void setLabel(String labelText) {
            label.setText(labelText);
        }

        @Override
        public T getItem() {
            return item;
        }

        protected boolean isDisabledBoolean() {
            return input.getProperty("disabled", false);
        }

        protected void setDisabled(boolean disabled) {
            input.setProperty("disabled", disabled);
        }
    }
}
