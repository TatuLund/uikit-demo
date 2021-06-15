package org.vaadin.uikit.components.layout;

import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkSizing;
import org.vaadin.uikit.components.interfaces.UkTooltip;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.dom.Element;

@Tag("form")
public class UkForm extends HtmlContainer
        implements HasStyle, HasElement, UkSizing, UkMargin, UkPadding {

    public class UkFormItem extends Composite<Div> implements UkTooltip {
        Label label = new Label();
        Div div = new Div();
        Div fieldWrapper = new Div();

        public UkFormItem(String labelText, Component field) {
            label.setText(labelText);
            fieldWrapper.add(field);
            field.getId().ifPresent(
                    id -> label.getElement().setAttribute("for", id));
            field.getElement().addPropertyChangeListener("required", event -> {
                if (field.getElement().getProperty("required").equals("true")) {
                    label.setText(labelText + " *");
                } else {
                    label.setText(labelText);
                }
            });
        }

        @Override
        protected Div initContent() {
            label.setClassName("uk-form-label");
            fieldWrapper.setClassName("uk-form-controls");
            div.add(label, fieldWrapper);
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

    Element fieldSet = new Element("fieldset");
    Element legend = new Element("legend");

    public UkForm() {
        fieldSet.getClassList().add("uk-fieldset");
        legend.getClassList().add("uk-legend");
        fieldSet.appendChild(legend);
        getElement().appendChild(fieldSet);
        addClassName("uk-form-stacked");

    }

    public void setLegend(String legendText) {
        legend.setText(legendText);
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
        UkFormItem item = new UkFormItem(label, field);
        add(item);
    }

    public void add(UkFormItem... formItems) {
        for (UkFormItem formItem : formItems) {
            fieldSet.appendChild(formItem.getElement());
        }
    }
}
