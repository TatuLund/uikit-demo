package org.vaadin.uikit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

@Tag("form")
public class UKForm extends HtmlContainer implements HasStyle, HasElement, UKWidthAndHeight, UKMargin {

	public class UKFormItem extends Composite<Div> implements UKTooltip {
		Label label = new Label();
		Div div = new Div();
		Div fieldWrapper = new Div();

		public UKFormItem(String labelText, Component field) {
			label.setText(labelText);
			fieldWrapper.add(field);
		}

		@Override
		protected Div initContent() {
			label.setClassName("uk-form-label");
			fieldWrapper.setClassName("uk-form-controls");
			div.add(label,fieldWrapper);
			return div;
		}
		
		public void setMargin(boolean margin) {
			if (margin) {
				div.addClassName("uk-margin");
			} else {
				div.removeClassName("uk-margin");
			}
		}
	}

	public UKForm() {
		addClassName("uk-form-stacked");
	}

	public void setHorizontal(boolean horizontal) {
		if (horizontal) {
			addClassName("uk-form-horizontal");
		} else {
			removeClassName("uk-form-horizontal");
		}
	}

	public void setMargin(boolean margin) {
		if (margin) {
			addClassName("uk-margin");
		} else {
			removeClassName("uk-margin");
		}
	}

	public void add(String label, Component field) {
		UKFormItem item = new UKFormItem(label,field);
		add(item);
	}
	
	public void add(UKFormItem...formItems) {
		for (UKFormItem formItem : formItems) {
			getElement().appendChild(formItem.getElement());
		}
	}
}
