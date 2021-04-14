package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;

public class UKAccordion extends Composite<UnorderedList> implements UKWidthAndHeight, HasSize {

	public class UKAccordionItem extends Composite<ListItem> {
		ListItem listItem = new ListItem();
		Anchor title = new Anchor("#");
		Div wrapper = new Div();

		public UKAccordionItem(String titleText) {
			title.setText(titleText);
		}

		public UKAccordionItem(String titleText, Component... components) {
			title.setText(titleText);
			wrapper.add(components);
		}

		public void add(Component... components) {
			wrapper.add(components);
		}

		public void remove(Component... components) {
			wrapper.remove(components);
		}

		@Override
		protected ListItem initContent() {
			if (first) {
				listItem.addClassName("uk-open");
				first = false;
			}
			title.addClassName("uk-accordion-title");
			wrapper.addClassName("uk-accordion-content");
			listItem.add(title, wrapper);
			return listItem;
		}
	}

	UnorderedList accordion = new UnorderedList();
	List<UKAccordionItem> items = new ArrayList<>();
	boolean animate = true;
	boolean first = true;

	public UKAccordionItem addItem(String titleText) {
		UKAccordionItem item = new UKAccordionItem(titleText);
		items.add(item);
		accordion.add(item);
		return item;
	}

	public UKAccordionItem addItem(String titleText, Component... components) {
		UKAccordionItem item = new UKAccordionItem(titleText, components);
		items.add(item);
		accordion.add(item);
		return item;
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
}
