package org.vaadin.uikit.components.layout;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.interfaces.UkMargin;
import org.vaadin.uikit.interfaces.UkSizing;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.shared.Registration;

public class UkAccordion extends Composite<UnorderedList>
        implements UkSizing, UkMargin, HasSize {

    public class UkAccordionItem extends Composite<ListItem> {
        ListItem listItem = new ListItem();
        Anchor caption = new Anchor("#");
        Div wrapper = new Div();
        String captionText;

        public UkAccordionItem(String captionText) {
            setCaption(captionText);
        }

        public UkAccordionItem(String titleText, Component... components) {
            setCaption(titleText);
            wrapper.add(components);
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

        @Override
        protected ListItem initContent() {
            if (first) {
                listItem.addClassName("uk-open");
                first = false;
            }
            caption.addClassName("uk-accordion-title");
            wrapper.addClassName("uk-accordion-content");
            listItem.add(caption, wrapper);
            return listItem;
        }
    }

    UnorderedList accordion = new UnorderedList();
    List<UkAccordionItem> items = new ArrayList<>();
    boolean animate = true;
    boolean first = true;

    public UkAccordionItem addItem(String captionText) {
        Div div = new Div();
        return addItem(captionText, div);
    }

    public UkAccordionItem addItem(String captionText,
            Component... components) {
        UkAccordionItem item = new UkAccordionItem(captionText, components);
        item.getElement().addEventListener("shown", event -> {
            fireEvent(new AccordionItemShownEvent(this, item, true));
        });
        item.getElement().addEventListener("hidden", event -> {
            fireEvent(new AccordionItemHiddenEvent(this, item, true));
        });
        items.add(item);
        accordion.add(item);
        return item;
    }

    public Registration addItemHiddenListener(
            ComponentEventListener<AccordionItemHiddenEvent> listener) {
        return addListener(AccordionItemHiddenEvent.class, listener);
    }

    public Registration addItemShownListener(
            ComponentEventListener<AccordionItemShownEvent> listener) {
        return addListener(AccordionItemShownEvent.class, listener);
    }

    public void toggleItem(UkAccordionItem item) {
        int index = items.indexOf(item);
        if (index != -1) {
            toggleItem(index);
        }
    }

    public void toggleItem(int index) {
        accordion.getElement().executeJs("UIkit.accordion($0).toggle($1,$2)",
                index, animate, accordion.getElement());
    }

    @Override
    protected UnorderedList initContent() {
        accordion.getElement().setAttribute("uk-accordion",
                "collapsible: false");
        accordion.addClassNames("uk-accordion");
        return accordion;
    }

    abstract class AccordionEvent extends ComponentEvent<UkAccordion> {

        private UkAccordionItem item;

        public AccordionEvent(UkAccordion source, UkAccordionItem item,
                boolean fromClient) {
            super(source, fromClient);
            this.item = item;
        }

        public UkAccordionItem getItem() {
            return item;
        }

        public int getIndex() {
            return items.indexOf(item);
        }
    }

    public class AccordionItemHiddenEvent extends AccordionEvent {
        public AccordionItemHiddenEvent(UkAccordion source,
                UkAccordionItem item, boolean fromClient) {
            super(source, item, fromClient);
        }
    }

    public class AccordionItemShownEvent extends AccordionEvent {
        public AccordionItemShownEvent(UkAccordion source, UkAccordionItem item,
                boolean fromClient) {
            super(source, item, fromClient);
        }
    }
}
