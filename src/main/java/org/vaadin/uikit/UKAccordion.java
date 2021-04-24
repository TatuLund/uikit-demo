package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;

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

public class UKAccordion extends Composite<UnorderedList> implements UKWidthAndHeight, UKMargin, HasSize {

	public class UKAccordionItem extends Composite<ListItem> {
		ListItem listItem = new ListItem();
		Anchor caption = new Anchor("#");
		Div wrapper = new Div();
		String captionText;
		
		public UKAccordionItem(String captionText) {
			setCaption(captionText);
		}

		public UKAccordionItem(String titleText, Component... components) {
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
	List<UKAccordionItem> items = new ArrayList<>();
	boolean animate = true;
	boolean first = true;

	public UKAccordionItem addItem(String captionText) {
		Div div = new Div();
		return addItem(captionText,div);
	}

	public UKAccordionItem addItem(String captionText, Component... components) {
		UKAccordionItem item = new UKAccordionItem(captionText, components);
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
	
	public void toggleItem(UKAccordionItem item) {
		int index = items.indexOf(item);
		if (index != -1) {
			toggleItem(index);
		}
	}

	public void toggleItem(int index) {
		accordion.getElement().executeJs("UIkit.accordion($0).toggle($1,$2)", index, animate, accordion.getElement());
	}

	@Override
	protected UnorderedList initContent() {
		accordion.getElement().setAttribute("uk-accordion", "collapsible: false");
		accordion.addClassNames("uk-accordion");
		return accordion;
	}
	
	abstract class AccordionEvent extends ComponentEvent<UKAccordion> {

        private UKAccordionItem item;

		public AccordionEvent(UKAccordion source,  UKAccordionItem item, 
                boolean fromClient) {
            super(source, fromClient);
            this.item = item;
        }

        public UKAccordionItem getItem() {
        	return item;
        }
        
        public int getIndex() {
        	return items.indexOf(item);
        }
	}

    public class AccordionItemHiddenEvent extends AccordionEvent {
		public AccordionItemHiddenEvent(UKAccordion source, UKAccordionItem item, boolean fromClient) {
			super(source, item, fromClient);
		}
    }

    public class AccordionItemShownEvent extends AccordionEvent {
		public AccordionItemShownEvent(UKAccordion source, UKAccordionItem item, boolean fromClient) {
			super(source, item, fromClient);
		}
    }
}
