package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.vaadin.uikit.components.UKCard;
import org.vaadin.uikit.components.UKNotification;
import org.vaadin.uikit.components.UKTile;
import org.vaadin.uikit.components.UKTile.TileVariant;
import org.vaadin.uikit.components.input.UKCheckboxGroup;
import org.vaadin.uikit.components.input.UKCombo;
import org.vaadin.uikit.components.input.UKRadioGroup;
import org.vaadin.uikit.components.input.UKSelect;
import org.vaadin.uikit.components.layout.UKFlex;
import org.vaadin.uikit.components.layout.UKForm;
import org.vaadin.uikit.interfaces.UKFormSizing.FieldSize;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.router.Route;

@Route("combo")
@StyleSheet("context://uikit.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.js")
public class ComboView extends UKFlex {

    int itemCount = 0;

    public ComboView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setSizeFull();

        UKCombo<Item> combo = createCombo();

        UKSelect<Item> select = createSelect();

        UKRadioGroup<Item> radioGroup = createRadioGroup();
        
        UKCheckboxGroup<Item> checkboxGroup = createCheckboxGroup();

        UKCard card = new UKCard();
        
        card.setTitle("Selection components");
        UKForm form = new UKForm();
        form.add("Combo" , combo);
        form.add("Select" , select);
        form.add("Radios" , radioGroup);
        form.add("Checkboxes" , checkboxGroup);
        form.setWidth(FixedWidth.MEDIUM);
        card.setContent(form);
        add(card);
    }

    private UKCheckboxGroup<Item> createCheckboxGroup() {
        UKCheckboxGroup<Item> radios = new UKCheckboxGroup<>(2);
        String[] array = { "Zero Nine Six Eight And Four And I Do Not Know Why", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine" };
        List<Item> list = new ArrayList<>();
        for (String item : array)
            list.add(new Item(item));
        radios.setItems(list);
//        radios.setSize(FieldSize.DEFAULT);
//        radios.setTooltip("Select one of these");
        radios.addValueChangeListener(event -> {
            UKNotification.show("Radios: Name: " + getNames(radios.getValue()));
        });
        radios.setItemLabelGenerator(item -> item.getName());
        return radios;
    }

    private String getNames(Set<Item> nameSet) {
        String names="";
        for (Item name : nameSet) {
            names+=" "+name.getName();
        }
        return names;
    }

    private UKRadioGroup<Item> createRadioGroup() {
        UKRadioGroup<Item> radios = new UKRadioGroup<>(4);
        String[] array = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine" };
        List<Item> list = new ArrayList<>();
        for (String item : array)
            list.add(new Item(item));
        radios.setItems(list);
        radios.setSize(FieldSize.DEFAULT);
        radios.setTooltip("Select one of these");
        radios.addValueChangeListener(event -> {
            UKNotification.show("Radios: Name: " + radios.getValue().getName()
                    + " Number " + radios.getValue().getNumber());
        });
        radios.setItemLabelGenerator(item -> item.getName());
        return radios;
    }

    private UKSelect<Item> createSelect() {
        UKSelect<Item> select = new UKSelect<>();
        String[] array = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine" };
        List<Item> list = new ArrayList<>();
        for (String item : array)
            list.add(new Item(item));
        select.setItems(list);
        select.setSize(FieldSize.DEFAULT);
        select.setTooltip("Select one of these");
        select.addValueChangeListener(event -> {
            UKNotification.show("Select: Name: " + select.getValue().getName()
                    + " Number " + select.getValue().getNumber());
        });
        select.setItemLabelGenerator(item -> item.getName());
        return select;
    }

    private UKCombo<Item> createCombo() {
        UKCombo<Item> combo = new UKCombo<>();
        String[] array = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine" };
        List<Item> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            for (String item : array)
                list.add(new Item(i + "-" + item));
        }
        combo.setItems(list);
        combo.setSize(FieldSize.DEFAULT);
        combo.setTooltip("Select one of these");
        combo.addValueChangeListener(event -> {
            UKNotification.show("Combo: Name: " + combo.getValue().getName()
                    + " Number " + combo.getValue().getNumber());
        });
        combo.setItemLabelGenerator(item -> item.getName());
        combo.setPlaceholder("number");
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
