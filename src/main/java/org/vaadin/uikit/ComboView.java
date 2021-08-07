package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkCard.CardVariant;
import org.vaadin.uikit.components.UkNotification;
import org.vaadin.uikit.components.input.UkCheckboxGroup;
import org.vaadin.uikit.components.input.UkCombo;
import org.vaadin.uikit.components.input.UkRadioGroup;
import org.vaadin.uikit.components.input.UkSelect;
import org.vaadin.uikit.components.interfaces.UkBorder.BorderStyle;
import org.vaadin.uikit.components.interfaces.UkFormSizing.FieldSize;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.components.layout.UkForm;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Selections")
@Route(value = "combo", layout = MainLayout.class)
public class ComboView extends AbstractView {

    int itemCount = 0;

    public ComboView() {
        UkCombo<Item> combo = createCombo();

        UkSelect<Item> select = createSelect();

        UkRadioGroup<Item> radioGroup = createRadioGroup();
        
        UkCheckboxGroup<Item> checkboxGroup = createCheckboxGroup();

        UkCard card = new UkCard();
        
        card.setTitle("Selection components");
        UkForm form = new UkForm();
        form.add("Combo" , combo);
        form.add("Select" , select);
        form.add("Select one" , radioGroup);
        form.add("Select all that apply" , checkboxGroup);
        form.setWidth(FixedWidth.LARGE);
        card.setContent(form);
        card.setBorder(BorderStyle.ROUNDED);
        card.setVariant(CardVariant.SECONDARY);
        add(card);
    }

    private UkCheckboxGroup<Item> createCheckboxGroup() {
        UkCheckboxGroup<Item> checkboxes = new UkCheckboxGroup<>(2);
        String[] array = { "Front end development", "Back end development", "CI/CD experience", "Java programming", "JavaScript coding", "SQL queries", "Integration tests",
                "Unit tests", "Selenium", "Maven" };
        List<Item> list = new ArrayList<>();
        for (String item : array)
            list.add(new Item(item));
        checkboxes.setItems(list);
//        radios.setSize(FieldSize.DEFAULT);
//        radios.setTooltip("Select one of these");
        checkboxes.addValueChangeListener(event -> {
            UkNotification.show("Radios: Name: " + getNames(checkboxes.getValue()));
        });
        checkboxes.setItemLabelGenerator(item -> item.getName());
        checkboxes.setCheckboxBorder(BorderStyle.ROUNDED);
        return checkboxes;
    }

    private String getNames(Set<Item> nameSet) {
        String names="";
        for (Item name : nameSet) {
            names+=" "+name.getName();
        }
        return names;
    }

    private UkRadioGroup<Item> createRadioGroup() {
        UkRadioGroup<Item> radios = new UkRadioGroup<>(4);
        String[] array = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine" };
        List<Item> list = new ArrayList<>();
        for (String item : array)
            list.add(new Item(item));
        radios.setItems(list);
        radios.setSize(FieldSize.MEDIUM);
        radios.setTooltip("Select one of these");
        radios.addValueChangeListener(event -> {
            UkNotification.show("Radios: Name: " + radios.getValue().getName()
                    + " Number " + radios.getValue().getNumber());
        });
        radios.setItemLabelGenerator(item -> item.getName());
        return radios;
    }

    private UkSelect<Item> createSelect() {
        UkSelect<Item> select = new UkSelect<>();
        String[] array = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine" };
        List<Item> list = new ArrayList<>();
        for (String item : array)
            list.add(new Item(item));
        select.setItems(list);
        select.setSize(FieldSize.MEDIUM);
        select.setTooltip("Select one of these");
        select.addValueChangeListener(event -> {
            UkNotification.show("Select: Name: " + select.getValue().getName()
                    + " Number " + select.getValue().getNumber());
        });
        select.setItemLabelGenerator(item -> item.getName());
        select.setBorder(BorderStyle.ROUNDED);
        return select;
    }

    private UkCombo<Item> createCombo() {
        UkCombo<Item> combo = new UkCombo<>();
        String[] array = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine" };
        List<Item> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            for (String item : array)
                list.add(new Item(i + "-" + item));
        }
        combo.setItems(list);
        combo.setSize(FieldSize.MEDIUM);
        combo.setTooltip("Select one of these");
        combo.addValueChangeListener(event -> {
            UkNotification.show("Combo: Name: " + combo.getValue().getName()
                    + " Number " + combo.getValue().getNumber());
        });
        combo.setItemLabelGenerator(item -> item.getName());
        combo.setPlaceholder("number");
        combo.setBorder(BorderStyle.ROUNDED);
        return combo;
    }

    public class Item {
        private String name;
        private Integer number;

        public Item(String name) {
            itemCount++;
            this.name = name;
            this.number = itemCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }
}
