package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.UKAccordion.UKAccordionItem;
import org.vaadin.uikit.UKCard.CardVariant;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;

public class UKTabSwitcher extends Composite<Div> implements UKWidthAndHeight, UKMargin {

	public enum TabPlacement {
		TOP, BOTTOM;
	}

	public enum TabAlignment {
		LEFT("uk-flex-left"), CENTER("uk-flex-center"), RIGHT("uk-flex-right"),
		EXPAND("uk-child-width-expand");

		private final String alignment;

		TabAlignment(String alignment) {
			this.alignment = alignment;
		}

		public String getAlignment() {
			return alignment;
		}		
	}

	public class UKTabItem {
		ListItem tabItem = new ListItem();
		ListItem contentItem = new ListItem();
		Anchor title = new Anchor("#");
		Div wrapper = new Div();

		public UKTabItem(String titleText) {
			this(titleText, new Div());
			wrapper.removeAll();
		}

		public UKTabItem(String titleText, Component... components) {
			title.setText(titleText);
			wrapper.add(components);
			tabItem.add(title);
			contentItem.add(wrapper);
			
//			if (first) {
//				tabItem.addClassName("uk-active");
//				first = false;
//			}
		}

		public void add(Component... components) {
			wrapper.add(components);
		}

		public void remove(Component... components) {
			wrapper.remove(components);
		}		

		protected ListItem getTab( ) {
			return tabItem;
		}
		
		protected ListItem getContent() {
			return contentItem;
		}
	}
	
	Div wrapper = new Div();
	UnorderedList tabNav = new UnorderedList();
	UnorderedList tabContainer = new UnorderedList();
	List<UKTabItem> items = new ArrayList<>();
	boolean first = true;

	public UKTabItem addItem(String titleText) {
		UKTabItem item = new UKTabItem(titleText);
		items.add(item);
		tabNav.add(item.getTab());
		tabContainer.add(item.getContent());
		return item;
	}

	public UKTabItem addItem(String titleText, Component... components) {
		UKTabItem item = new UKTabItem(titleText, components);
		items.add(item);
		tabNav.add(item.getTab());
		tabContainer.add(item.getContent());
		return item;
	}
	
	public void show(UKTabItem item) {
		int index = items.indexOf(item);
		if (index != -1) {
			show(index);
		}
	}

	public void show(int index) {
		tabNav.getElement().executeJs("UIkit.switcher($0).show($1)", index, tabNav.getElement());
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
		wrapper.add(tabNav,tabContainer);
		return wrapper;
	}
}
