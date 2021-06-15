package org.vaadin.uikit.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.vaadin.uikit.components.interfaces.ComponentProvider;
import org.vaadin.uikit.components.interfaces.StringProvider;
import org.vaadin.uikit.components.interfaces.UkFloat;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkSizing;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.DescriptionList;
import com.vaadin.flow.component.html.DescriptionList.Description;
import com.vaadin.flow.component.html.DescriptionList.Term;
import com.vaadin.flow.data.binder.HasDataProvider;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.KeyMapper;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.shared.Registration;

public class UkDescriptionList<T> extends Composite<DescriptionList>
        implements HasDataProvider<T>, UkMargin, UkPadding, UkSizing {

    DescriptionList list = new DescriptionList();

    private final KeyMapper<T> keyMapper = new KeyMapper<>(this::getItemId);
    private DataProvider<T, ?> dataProvider = DataProvider.ofItems();
    private Registration dataProviderListenerRegistration;
    private StringProvider<T> descriptionProvider;
    private StringProvider<T> termProvider;
    private ComponentProvider<T> componentProvider;
    private List<DescriptionItem<T>> rows = new ArrayList<>();

    class UkTerm extends Term implements UkMargin, UkPadding {

        public UkTerm() {
            super();
        }

        public UkTerm(String text) {
            super(text);
        }

        public UkTerm(Component component) {
            super(component);
        }
    }

    class UkDescription extends Description implements UkMargin, UkPadding {

        public UkDescription() {
            super();
        }

        public UkDescription(String text) {
            super(text);
        }

        public UkDescription(Component component) {
            super(component);
        }        
    }

    class DescriptionItem<R> {
        private UkTerm term;
        private UkDescription desc;
        private R item;

        DescriptionItem(String id, R item) {
            this.item = item;
            applyItem(item);
        }

        void applyItem(R item) {
            String termText = getTermProvider().apply((T) item);
            term = new UkTerm(termText);
            if (getComponentProvider() != null) {
                Component descriptionComponent = getComponentProvider()
                        .apply((T) item);
                desc = new UkDescription(descriptionComponent);
            } else {
                String descriptionText = getDescriptionProvider()
                        .apply((T) item);
                desc = new UkDescription(descriptionText);
            }
        }

        R getItem() {
            return (R) item;
        }

        UkTerm getTerm() {
            return term;
        }

        UkDescription getDescription() {
            return desc;
        }

        void setItem(R item) {
            this.item = item;
            applyItem(item);
        }
    }

    public UkDescriptionList(StringProvider<T> termProvider,
            ComponentProvider<T> componentProvider) {
        this.termProvider = termProvider;
        this.componentProvider = componentProvider;
    }

    public UkDescriptionList(StringProvider<T> termProvider,
            StringProvider<T> descriptionProvider) {
        this.termProvider = termProvider;
        this.descriptionProvider = descriptionProvider;
    }

    public ComponentProvider<T> getComponentProvider() {
        return componentProvider;
    }

    public void setComponentProvider(ComponentProvider<T> componentProvider) {
        Objects.requireNonNull(componentProvider,
                "The component provider can not be null");
        this.componentProvider = componentProvider;
        reset();
    }

    public StringProvider<T> getDescriptionProvider() {
        return descriptionProvider;
    }

    public void setDescriptionProvider(StringProvider<T> descriptionProvider) {
        Objects.requireNonNull(descriptionProvider,
                "The description provider can not be null");
        this.descriptionProvider = descriptionProvider;
        reset();
    }

    public StringProvider<T> getTermProvider() {
        return termProvider;
    }

    public void setTermProvider(StringProvider<T> termProvider) {
        Objects.requireNonNull(termProvider,
                "The term provider can not be null");
        this.termProvider = termProvider;
        reset();
    }

    public void setDivider(boolean divider) {
        if (divider) {
            list.addClassName("uk-description-list-divider");
        } else {
            list.removeClassName("uk-description-list-divider");
        }
    }

    @Override
    public DescriptionList initContent() {
        list.addClassName("uk-description-list");
        return list;
    }

    public DataProvider<T, ?> getDataProvider() {
        return dataProvider;
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
                        this.getDescriptionItems()
                                .filter(item -> Objects.equals(
                                        getItemId(item.item),
                                        getItemId(otherItem)))
                                .findFirst()
                                .ifPresent(descItem -> updateDescriptionItem(
                                        descItem, otherItem));
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

    @SuppressWarnings("unchecked")
    private Stream<DescriptionItem<T>> getDescriptionItems() {
        return rows.stream();
    }

    private void reset() {
        rows = new ArrayList<>();
        keyMapper.removeAll();
        list.removeAll();

        getDataProvider().fetch(new Query<>()).map(this::createDescriptionItem)
                .forEach(this::addDescriptionItem);
    }

    private void addDescriptionItem(DescriptionItem<T> descriptionItem) {
        rows.add(descriptionItem);
        list.add(descriptionItem.getTerm());
        list.add(descriptionItem.getDescription());
    }

    private void updateDescriptionItem(DescriptionItem<T> descriptionItem,
            T item) {
        descriptionItem.setItem(item);
    }

    private DescriptionItem<T> createDescriptionItem(T item) {
        DescriptionItem<T> descriptionItem = new DescriptionItem<>(
                keyMapper.key(item), item);
        return descriptionItem;
    }

    private Object getItemId(T item) {
        if (getDataProvider() == null) {
            return item;
        }
        return getDataProvider().getId(item);
    }

    public void setTermMargin() {
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getTerm().setMargin());
    }

    public void setTermMargin(MarginSize marginSize) {
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getTerm().setMargin(marginSize));
    }

    public void setDescriptionMargin() {
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getDescription().setMargin());
    }

    public void setDescriptionMargin(MarginSize marginSize) {
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getDescription().setMargin(marginSize));
    }

    public void setTermPadding() {
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getTerm().setPadding());
    }

    public void setTermPadding(PaddingSize paddingSize) {
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getTerm().setPadding(paddingSize));
    }

    public void setDescriptionPadding() {
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getDescription().setPadding());
    }

    public void setDescriptionPadding(PaddingSize paddingSize) {
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getDescription().setPadding(paddingSize));
    }

}
