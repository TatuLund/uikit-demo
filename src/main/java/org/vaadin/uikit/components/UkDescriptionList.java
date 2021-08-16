package org.vaadin.uikit.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.vaadin.uikit.components.interfaces.ComponentProvider;
import org.vaadin.uikit.components.interfaces.StringProvider;
import org.vaadin.uikit.components.interfaces.UkBorder.BorderStyle;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkSizing;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.DescriptionList;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.DescriptionList.Description;
import com.vaadin.flow.component.html.DescriptionList.Term;
import com.vaadin.flow.data.binder.HasDataProvider;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.KeyMapper;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
public class UkDescriptionList<T> extends Composite<DescriptionList>
        implements HasDataProvider<T>, UkMargin, UkPadding, UkSizing {

    private DescriptionList list = new DescriptionList();

    private final KeyMapper<T> keyMapper = new KeyMapper<>(this::getItemId);
    private DataProvider<T, ?> dataProvider = DataProvider.ofItems();
    private Registration dataProviderListenerRegistration;
    private StringProvider<T> descriptionProvider;
    private StringProvider<T> termProvider;
    private ComponentProvider<T> componentProvider;
    private List<DescriptionItem<T>> rows = new ArrayList<>();

    private int pageLength = -1;
    private int currentPage = 0;
    private Object filter;
    private SerializableComparator<T> inMemorySorting;

    private final ArrayList<QuerySortOrder> backEndSorting = new ArrayList<>();
    private int dataProviderSize = -1;
    private BorderStyle borderStyle = BorderStyle.SHARP;

    private MarginSize termMargin;
    private MarginSize descriptionMargin;
    private PaddingSize descriptionPadding;
    private PaddingSize termPadding;

    class UkTerm extends Term implements UkMargin, UkPadding {

        public UkTerm() {
            super();
            settings();
        }

        public UkTerm(String text) {
            super(text);
            settings();
        }

        public UkTerm(Component component) {
            super(component);
            settings();
        }

        private void settings() {
            if (termMargin != null) setMargin(termMargin);
            if (termPadding != null) setPadding(termPadding);
        }
    }

    class UkDescription extends Description implements UkMargin, UkPadding {

        public UkDescription() {
            super();
            settings();
        }

        public UkDescription(String text) {
            super(text);
            settings();
        }

        public UkDescription(Component component) {
            super(component);
            settings();
        }        

        private void settings() {
            if (descriptionMargin != null) setMargin(descriptionMargin);
            if (descriptionPadding != null) setPadding(descriptionPadding);
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
        this(termProvider,componentProvider,-1);
    }
    
    public UkDescriptionList(StringProvider<T> termProvider,
            ComponentProvider<T> componentProvider, int pageLength) {
        this.termProvider = termProvider;
        this.componentProvider = componentProvider;
        this.pageLength = pageLength;
    }

    public UkDescriptionList(StringProvider<T> termProvider,
            StringProvider<T> descriptionProvider) {
        this(termProvider,descriptionProvider,-1);
    }
    
    public UkDescriptionList(StringProvider<T> termProvider,
            StringProvider<T> descriptionProvider, int pageLength) {
        this.termProvider = termProvider;
        this.descriptionProvider = descriptionProvider;
        this.pageLength = pageLength;
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
        list.removeAll();
        keyMapper.removeAll();

        Query query = null;
        if (pageLength < 0) {
            query = new Query<>();
        } else {
            dataProviderSize = dataProvider.size(new Query(filter));
            int offset = pageLength * currentPage;
            query = new Query<>(offset, pageLength, backEndSorting,
                    inMemorySorting, filter);
        }
        
        getDataProvider().fetch(query).map(item -> createDescriptionItem((T) item))
                .forEach(descriptionItem -> addDescriptionItem((DescriptionItem<T>) descriptionItem));

        if (pageLength > 0) {
            addFooter();
        }

    }

    private void addDescriptionItem(DescriptionItem<T> descriptionItem) {
        rows.add(descriptionItem);
        list.add(descriptionItem.getTerm());
        list.add(descriptionItem.getDescription());
    }

    private void addFooter() {
        list.add(new UkTerm(""));
        list.add(new UkDescription(createPaging()));
    }
    
    private Div createPaging() {
        UkButton first = new UkButton(UkIcons.CHEVRON_DOUBLE_LEFT.create());
        first.setBorder(borderStyle);
        first.setMargin(MarginSize.SMALL, MarginSide.RIGHT);
        UkButton previous = new UkButton(UkIcons.CHEVRON_LEFT.create());
        previous.setBorder(borderStyle);
        UkButton next = new UkButton(UkIcons.CHEVRON_RIGHT.create());
        next.setBorder(borderStyle);
        next.setMargin(MarginSize.SMALL, MarginSide.RIGHT);
        UkButton last = new UkButton(UkIcons.CHEVRON_DOUBLE_RIGHT.create());
        last.setBorder(borderStyle);
        int lastPage = dataProviderSize % pageLength == 0
                ? (dataProviderSize / pageLength) - 1
                : (dataProviderSize / pageLength);
        first.addClickListener(event -> {
            if (currentPage != 0) {
                currentPage = 0;
                dataProvider.refreshAll();
            }
        });
        next.addClickListener(event -> {
            if (currentPage < lastPage) {
                currentPage++;
                dataProvider.refreshAll();
            }
        });
        previous.addClickListener(event -> {
            if (currentPage > 0) {
                currentPage--;
                dataProvider.refreshAll();
            }
        });
        last.addClickListener(event -> {
            if (currentPage != lastPage) {
                currentPage = lastPage;
                dataProvider.refreshAll();
            }
        });
        Div div = new Div();
        div.getStyle().set("width", "100%");
        div.getStyle().set("display", "flex");
        div.getStyle().set("flex-direction", "row");
        Div spacer = new Div();
        spacer.getStyle().set("flex-grow", "1");
        spacer.getStyle().set("text-align", "center");
        spacer.setText((currentPage + 1) + "/" + (lastPage + 1));
        div.add(first, previous, spacer, next, last);
        return div;
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
        setTermMargin(MarginSize.DEFAULT);
    }

    public void setTermMargin(MarginSize marginSize) {
        this.termMargin = marginSize;
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getTerm().setMargin(marginSize));
    }

    public void setDescriptionMargin() {
        setDescriptionMargin(MarginSize.DEFAULT);
    }

    public void setDescriptionMargin(MarginSize marginSize) {
        this.descriptionMargin = marginSize;
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getDescription().setMargin(marginSize));
    }

    public void setTermPadding() {
        setTermPadding(PaddingSize.DEFAULT);
    }

    public void setTermPadding(PaddingSize paddingSize) {
        this.termPadding = paddingSize;
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getTerm().setPadding(paddingSize));
    }

    public void setDescriptionPadding() {
        setDescriptionPadding(PaddingSize.DEFAULT);
    }

    public void setDescriptionPadding(PaddingSize paddingSize) {
        this.descriptionPadding = paddingSize;
        getDescriptionItems().forEach(descriptionItem -> descriptionItem.getDescription().setPadding(paddingSize));
    }

    public void setButtonBorder(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }
}
