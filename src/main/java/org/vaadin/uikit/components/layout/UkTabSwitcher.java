package org.vaadin.uikit.components.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkSizing;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.shared.Registration;

@SuppressWarnings("serial")
public class UkTabSwitcher extends Composite<Div>
        implements UkSizing, UkMargin, UkPadding {

    public enum TabPlacement {
        TOP, BOTTOM;
    }

    public enum TabAlignment {
        LEFT("uk-flex-left"), CENTER("uk-flex-center"), RIGHT(
                "uk-flex-right"), EXPAND("uk-child-width-expand");

        private final String alignment;

        TabAlignment(String alignment) {
            this.alignment = alignment;
        }

        public String getAlignment() {
            return alignment;
        }
    }

    public class UkTabItem implements Serializable {
        ListItem tabItem = new ListItem();
        ListItem contentItem = new ListItem();
        Anchor caption = new Anchor("#");
        Div wrapper = new Div();
        String captionText;

        public UkTabItem(String captionText) {
            this(captionText, new Div());
            wrapper.removeAll();
        }

        public UkTabItem(String captionText, Component... components) {
            setCaption(captionText);
            wrapper.add(components);
            tabItem.add(caption);
            contentItem.add(wrapper);
        }

        public void add(Component... components) {
            wrapper.add(components);
        }

        public void remove(Component... components) {
            wrapper.remove(components);
        }

        public void removeAll() {
            wrapper.removeAll();
        }

        public void setCaption(String captionText) {
            this.captionText = captionText;
            caption.setText(captionText);
        }

        public String getCaption() {
            return captionText;
        }

        protected ListItem getTab() {
            return tabItem;
        }

        protected ListItem getContent() {
            return contentItem;
        }
    }

    Div wrapper = new Div();
    UnorderedList tabNav = new UnorderedList();
    UnorderedList tabContainer = new UnorderedList();
    List<UkTabItem> items = new ArrayList<>();
    boolean first = true;

    public UkTabItem addItem(String titleText) {
        return addItem(titleText, new Div());
    }

    public UkTabItem addItem(String titleText, Component... components) {
        UkTabItem item = new UkTabItem(titleText, components);
        item.getContent().getElement().addEventListener("shown", event -> {
            fireEvent(new TabItemShownEvent(this, item, true));
        });
        item.getContent().getElement().addEventListener("hidden", event -> {
            fireEvent(new TabItemHiddenEvent(this, item, true));
        });
        items.add(item);
        tabNav.add(item.getTab());
        tabContainer.add(item.getContent());
        return item;
    }

    public Registration addItemHiddenListener(
            ComponentEventListener<TabItemHiddenEvent> listener) {
        return addListener(TabItemHiddenEvent.class, listener);
    }

    public Registration addItemShownListener(
            ComponentEventListener<TabItemShownEvent> listener) {
        return addListener(TabItemShownEvent.class, listener);
    }

    public void show(UkTabItem item) {
        int index = items.indexOf(item);
        if (index != -1) {
            show(index);
        }
    }

    public void show(int index) {
        tabNav.getElement().executeJs("UIkit.switcher($0).show($1)",
                tabNav.getElement(), index);
    }

    public void setTabPlacement(TabPlacement tabPlacement) {
        if (tabPlacement == TabPlacement.BOTTOM) {
            tabNav.addClassName("uk-tab-bottom");
            getElement().getStyle().set("flex-direction", "column-reverse");
        } else {
            tabNav.removeClassName("uk-tab-bottom");
            getElement().getStyle().set("flex-direction", "column");
        }
    }

    public void setTabAlignment(TabAlignment tabAlignment) {
        for (TabAlignment alignment : TabAlignment.values()) {
            tabNav.removeClassName(alignment.getAlignment());
        }
        tabNav.addClassName(tabAlignment.getAlignment());
    }

    @Override
    protected Div initContent() {
        wrapper.getStyle().set("display", "flex");
        wrapper.getStyle().set("flex-direction", "column");
        tabNav.getElement().setAttribute("uk-tab", true);
        tabContainer.addClassName("uk-switcher");
        wrapper.add(tabNav, tabContainer);
        return wrapper;
    }

    abstract class TabSwitcherEvent extends ComponentEvent<UkTabSwitcher> {

        private UkTabItem item;

        public TabSwitcherEvent(UkTabSwitcher source, UkTabItem item,
                boolean fromClient) {
            super(source, fromClient);
            this.item = item;
        }

        public UkTabItem getItem() {
            return item;
        }

        public int getIndex() {
            return items.indexOf(item);
        }
    }

    public class TabItemHiddenEvent extends TabSwitcherEvent {
        public TabItemHiddenEvent(UkTabSwitcher source, UkTabItem item,
                boolean fromClient) {
            super(source, item, fromClient);
        }
    }

    public class TabItemShownEvent extends TabSwitcherEvent {
        public TabItemShownEvent(UkTabSwitcher source, UkTabItem item,
                boolean fromClient) {
            super(source, item, fromClient);
        }
    }
}
