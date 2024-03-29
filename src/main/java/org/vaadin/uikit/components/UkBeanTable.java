package org.vaadin.uikit.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.vaadin.uikit.components.interfaces.ComponentProvider;
import org.vaadin.uikit.components.interfaces.UkTableOptions;
import org.vaadin.uikit.components.interfaces.UkBorder.BorderStyle;
import org.vaadin.uikit.components.interfaces.UkBoxShadow;
import org.vaadin.uikit.components.interfaces.UkHidden;
import org.vaadin.uikit.components.interfaces.UkMargin.MarginSide;
import org.vaadin.uikit.components.interfaces.UkMargin.MarginSize;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.binder.BeanPropertySet;
import com.vaadin.flow.data.binder.HasDataProvider;
import com.vaadin.flow.data.binder.PropertyDefinition;
import com.vaadin.flow.data.binder.PropertySet;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.KeyMapper;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;

/**
 * This is a simple Table component backed by DataProvider. The data provider
 * populates the Table with data from the beans. The component has minimal API
 * and ultra simple design. The purpose of this component is to be a little
 * sibling to Grid. Thus there are many features intentionally left out.
 * 
 * This component does not support lazy loading of the data, thus it is purposed
 * for the small data sets only.
 * 
 * Table's cells can be populated by text, html or components.
 * 
 * Currently only minimal styling included, no scrolling, etc. provided.
 * 
 * Component has css class name "bean-table" and custom css can be applied with
 * it.
 * 
 * @author Tatu Lund
 *
 * @param <T>
 *            Bean type for the Table
 */

@SuppressWarnings("serial")
@Tag("table")
public class UkBeanTable<T> extends HtmlComponent implements HasDataProvider<T>,
        HasSize, UkTableOptions, UkHidden, UkBoxShadow {

    private final KeyMapper<T> keyMapper = new KeyMapper<>(this::getItemId);
    private DataProvider<T, ?> dataProvider = DataProvider.ofItems();
    private Registration dataProviderListenerRegistration;
    private List<Column<T>> columns = new ArrayList<>();
    private List<RowItem<T>> rows = new ArrayList<>();
    private Element headerElement;
    private Element bodyElement;
    private boolean htmlAllowed;
    private Class<T> beanType;
    private PropertySet<T> propertySet;
    private Element footerElement;
    private int pageLength = -1;
    private int currentPage = 0;
    private Object filter;
    private SerializableComparator<T> inMemorySorting;

    private final ArrayList<QuerySortOrder> backEndSorting = new ArrayList<>();
    private int dataProviderSize = -1;
    private BorderStyle borderStyle = BorderStyle.SHARP;

    /**
     * The column width style
     */
    public enum ColumnWidth {
        // @formatter:off
        NONE(null),
        SHIRNK("uk-table-shrink"),
        EXPAND("uk-table-expand");
        // @formatter:on

        private final String width;

        ColumnWidth(String width) {
            this.width = width;
        }

        public String getWidth() {
            return width;
        }
    }

    /**
     * The column text wrapping style
     */
    public enum ColumnWrapping {
        // @formatter:off
        WRAP(null),
        TRUNCATE("uk-text-truncate"),
        NOWRAP("uk-text-nowrap");
        // @formatter:on

        private final String wrapping;

        ColumnWrapping(String wrapping) {
            this.wrapping = wrapping;
        }

        public String getWrapping() {
            return wrapping;
        }
    }

    /**
     * Configuration class for the Columns.
     *
     * @param <R>
     *            Bean type
     */
    public class Column<R> {
        String header;
        ValueProvider<R, ?> valueProvider;
        ComponentProvider<R> componentProvider;
        private ColumnWidth width = ColumnWidth.NONE;
        private ColumnWrapping wrapping = ColumnWrapping.WRAP;

        /**
         * Constructor with header and value provider
         * 
         * @param header
         *            The header as text
         * @param valueProvider
         *            The valuprovider
         */
        public Column(String header, ValueProvider<R, ?> valueProvider) {
            this.header = header;
            this.valueProvider = valueProvider;
        }

        /**
         * Constructor without parameters
         */
        public Column() {
        }

        public Column<R> setHeader(String header) {
            this.header = header;
            return this;
        }

        public Column<R> setComponentProvider(
                ComponentProvider<R> componentProvider) {
            this.componentProvider = componentProvider;
            return this;
        }

        public ValueProvider<R, ?> getValueProvider() {
            return valueProvider;
        }

        public ComponentProvider<R> getComponentProvider() {
            return componentProvider;
        }

        public String getHeader() {
            return header;
        }

        /**
         * Set the column width style for this column.
         * 
         * @param width
         *            The column width style
         * @return The column for chaining
         */
        public Column<R> setWidth(ColumnWidth width) {
            this.width = width;
            return this;
        }

        public ColumnWidth getWidth() {
            return width;
        }

        /**
         * Set the column text wrapping style for this column.
         * 
         * @param wrapping
         *            Text wrapping style
         * @return The column for chaining
         */
        public Column<R> setWrapping(ColumnWrapping wrapping) {
            this.wrapping = wrapping;
            return this;
        }

        public ColumnWrapping getWrapping() {
            return wrapping;
        }
    }

    /**
     * Internal wrapper class for the rows with item data.
     * 
     * @param <R>
     *            Bean type
     */
    private class RowItem<R> {

        private R item;
        private Element rowElement;

        public RowItem(String id, R item) {
            this.item = item;
            rowElement = new Element("tr");
            createCells();
        }

        private void createCells() {
            columns.forEach(column -> {
                if (column.getComponentProvider() == null
                        && column.getValueProvider() == null) {
                    throw new IllegalStateException(
                            "Column is lacking eihercomponent or value provider.");
                }
                Element cell = new Element("td");
                Component component = null;
                Object value = null;
                if (column.getComponentProvider() != null) {
                    component = column.getComponentProvider().apply((T) item);
                } else {
                    value = column.getValueProvider().apply((T) item);
                }
                if (value == null)
                    value = "";
                if (component != null) {
                    cell.appendChild(component.getElement());
                } else if (htmlAllowed) {
                    Html span = new Html(
                            "<span>" + value.toString() + "</span>");
                    cell.appendChild(span.getElement());
                } else {
                    cell.setText(value.toString());
                }
                rowElement.appendChild(cell);
            });
        }

        public R getItem() {
            return item;
        }

        public Element getRowElement() {
            return rowElement;
        }

        public void setItem(R item) {
            this.item = item;
            rowElement.removeAllChildren();
            createCells();
        }

    }

    /**
     * The default constructor. This creates a BeanTable without further
     * configuration. Use {@link #addColumn(String,ValueProvider)}
     * {@link #addComponentColumn(String,ValueProvider)} to configure columns.
     */
    public UkBeanTable() {
        addClassNames("uk-table", "uk-table-responsive");
        headerElement = new Element("thead");
        footerElement = new Element("tfoot");
        bodyElement = new Element("tbody");
        getElement().appendChild(headerElement);
        getElement().appendChild(bodyElement);
        getElement().appendChild(footerElement);
    }

    /**
     * The default constructor with defined page length. Use this constructor
     * with large data sources, i.e. DataProvider.fromCallBacks(..). This
     * constructor enables paging controls in the footer row. Also this creates
     * a BeanTable without further configuration. Use
     * {@link #addColumn(String,ValueProvider)}
     * {@link #addComponentColumn(String,ValueProvider)} to configure columns.
     * 
     * @param pageLength
     *            Size of the page
     */
    public UkBeanTable(int pageLength) {
        this();
        this.pageLength = pageLength;
    }

    /**
     * Creates a new BeanTable with an initial set of columns for each of the
     * bean's properties. The property-values of the bean will be converted to
     * Strings. Full names of the properties will be used as the header
     * captions.
     * 
     * @param beanType
     *            the bean type to use, not <code>null</code>
     */
    public UkBeanTable(Class<T> beanType) {
        this(beanType, true);
    }

    /**
     * Creates a new BeanTable with an initial set of columns for each of the
     * bean's properties. The property-values of the bean will be converted to
     * Strings. Full names of the properties will be used as the header
     * captions.
     * <p>
     * Constructor with defined page length. Use this constructor with large
     * data sources, i.e. DataProvider.fromCallBacks(..). This constructor
     * enables paging controls in the footer row.
     * <p>
     * When autoCreateColumns is <code>false</code>. Use
     * {@link #setColumns(String...)} to define which properties to include and
     * in which order. You can also add a column for an individual property with
     * {@link #addColumn(String)}.
     *
     * @param beanType
     *            the bean type to use, not <code>null</code>
     * @param autoCreateColumns
     *            when <code>true</code>, columns are created automatically for
     *            the properties of the beanType
     */
    public UkBeanTable(Class<T> beanType, boolean autoCreateColumns) {
        this();
        Objects.requireNonNull(beanType, "Bean type can't be null");
        this.beanType = beanType;
        propertySet = BeanPropertySet.get(beanType);
        if (autoCreateColumns) {
            propertySet.getProperties()
                    .filter(property -> !property.isSubProperty())
                    .sorted((prop1, prop2) -> prop1.getName()
                            .compareTo(prop2.getName()))
                    .forEach(this::addColumn);
        }
    }

    /**
     * Creates a new BeanTable with an initial set of columns for each of the
     * bean's properties. The property-values of the bean will be converted to
     * Strings. Full names of the properties will be used as the header
     * captions.
     * <p>
     * When autoCreateColumns is <code>false</code>. Use
     * {@link #setColumns(String...)} to define which properties to include and
     * in which order. You can also add a column for an individual property with
     * {@link #addColumn(String)}.
     *
     * @param beanType
     *            the bean type to use, not <code>null</code>
     * @param autoCreateColumns
     *            when <code>true</code>, columns are created automatically for
     *            the properties of the beanType
     * @param pageLength
     *            Size of the page
     */
    public UkBeanTable(Class<T> beanType, boolean autoCreateColumns,
            int pageLength) {
        this(beanType, autoCreateColumns);
        this.pageLength = pageLength;
    }

    /**
     * Add column to Table with the given property.
     * 
     * @param property
     *            The property
     * 
     * @return A new column
     */
    private Column<T> addColumn(PropertyDefinition<T, ?> property) {
        String propertyName = property.getName();
        String name = formatName(propertyName);
        return addColumn(name, property.getGetter());
    }

    private String formatName(String propertyName) {
        if (propertyName == null || propertyName.isEmpty())
            return "";
        String name = propertyName.replaceAll(
                String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"),
                " ");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }

    /**
     * Add column to Table with the given property name.
     * 
     * @param propertyName
     *            The property
     */
    public void addColumn(String propertyName) {
        Objects.requireNonNull(propertySet,
                "No property set defined, use BeanTable((Class<T> beanType) constructor");
        Objects.requireNonNull("propetyName cannot be null");
        propertySet.getProperties()
                .filter(property -> property.getName().equals(propertyName))
                .findFirst().ifPresent(match -> {
                    addColumn(formatName(match.getName()), match.getGetter());
                });
    }

    /**
     * Configure BeanTable to have columns with given set of property names.
     * 
     * @param propertyNames
     *            List of property names
     */
    public void setColumns(String... propertyNames) {
        for (String propertyName : propertyNames) {
            addColumn(propertyName);
        }
    }

    /**
     * Add a column with a value provider. Value provider is a function
     * reference, e.g. getter of the bean or lambda that returns the value for
     * this column.
     * 
     * @param header
     *            The heafers as a string, can be null
     * @param valueProvider
     *            The value provider
     * 
     * @return A column
     */
    public Column<T> addColumn(String header,
            ValueProvider<T, ?> valueProvider) {
        Column<T> column = new Column<>(header, valueProvider);
        columns.add(column);
        updateHeader();
        return column;
    }

    /**
     * Add a column with component provider. Component provider is a lambda that
     * must return a new instance of a component.
     * 
     * @param header
     *            Header as string, can be null
     * @param componentProvider
     *            Component provider
     * 
     * @return A column
     */
    public Column<T> addComponentColumn(String header,
            ComponentProvider<T> componentProvider) {
        Column<T> column = new Column<>();
        column.setHeader(header);
        column.setComponentProvider(componentProvider);
        columns.add(column);
        updateHeader();
        return column;
    }

    private void updateHeader() {
        headerElement.removeAllChildren();
        Element rowElement = new Element("tr");
        columns.forEach(column -> {
            Element cell = new Element("th");
            if (column.getHeader() != null) {
                cell.setText(column.getHeader());
            }
            if (column.getWidth() != ColumnWidth.NONE) {
                cell.getClassList().add(column.getWidth().getWidth());
            }
            if (column.getWrapping() != ColumnWrapping.WRAP) {
                cell.getClassList().add(column.getWrapping().getWrapping());
            }
            rowElement.appendChild(cell);
        });
        headerElement.appendChild(rowElement);
    }

    private void updateFooter() {
        footerElement.removeAllChildren();
        if (dataProviderSize > 0) {
            Element rowElement = new Element("tr");
            Element cell = new Element("td");
            cell.setAttribute("colspan", "" + columns.size());
            rowElement.appendChild(cell);
            Div div = createPaging();
            cell.appendChild(div.getElement());
            footerElement.appendChild(rowElement);
        }
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

    @Override
    public void setDataProvider(DataProvider<T, ?> dataProvider) {
        this.dataProvider = dataProvider;
        reset(false);
        setupDataProviderListener(dataProvider);
    }

    private void setupDataProviderListener(DataProvider<T, ?> dataProvider) {
        if (dataProviderListenerRegistration != null) {
            dataProviderListenerRegistration.remove();
        }
        dataProviderListenerRegistration = dataProvider
                .addDataProviderListener(event -> {
                    if (event instanceof DataChangeEvent.DataRefreshEvent) {
                        doRefreshItem(event);
                    } else {
                        reset(false);
                    }
                });
    }

    private void doRefreshItem(DataChangeEvent<T> event) {
        T otherItem = ((DataChangeEvent.DataRefreshEvent<T>) event).getItem();
        getRowItems()
                .filter(rowItem -> Objects.equals(getItemId(rowItem.getItem()),
                        getItemId(otherItem)))
                .findFirst()
                .ifPresent(rowItem -> updateRow(rowItem, otherItem));
    }

    private RowItem<T> createRow(T item) {
        RowItem<T> rowItem = new RowItem<>(keyMapper.key(item), item);
        return rowItem;
    }

    private void addRow(RowItem<T> rowItem) {
        rows.add(rowItem);
        bodyElement.appendChild(rowItem.getRowElement());
    }

    private void reset(boolean refresh) {
        if (!refresh) {
            bodyElement.setText("");
            rows = new ArrayList<>();
        }
        keyMapper.removeAll();
        Query query = null;
        if (pageLength < 0) {
            query = new Query();
        } else {
            dataProviderSize = dataProvider.size(new Query(filter));
            updateFooter();
            int offset = pageLength * currentPage;
            query = new Query(offset, pageLength, backEndSorting,
                    inMemorySorting, filter);
        }
        getDataProvider().fetch(query).map(row -> createRow((T) row)).forEach(
                rowItem -> addRow((UkBeanTable<T>.RowItem<T>) rowItem));
    }

    /**
     * Return the currently used data provider.
     * 
     * @return A data provider
     */
    public DataProvider<T, ?> getDataProvider() {
        return dataProvider;
    }

    private Object getItemId(T item) {
        if (getDataProvider() == null) {
            return item;
        }
        return getDataProvider().getId(item);
    }

    private Stream<RowItem<T>> getRowItems() {
        return rows.stream();
    }

    private void updateRow(RowItem<T> rowItem, T item) {
        rowItem.setItem(item);
    }

    @Override
    public Element getElement() {
        return super.getElement();
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

    /**
     * Set whether cell content should allow html content or not. If this is
     * false (default), value will be set as text content of the cell. If set to
     * true the value string will be wrapped in span element and can contain
     * html.
     * 
     * @param htmlAllowed
     *            A boolean value.
     */
    public void setHtmlAllowed(boolean htmlAllowed) {
        this.htmlAllowed = htmlAllowed;
    }

    /**
     * Set the Border style used in paging buttons.
     * 
     * @param borderStyle
     *            The border style.
     */
    public void setButtonBorder(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }
}
