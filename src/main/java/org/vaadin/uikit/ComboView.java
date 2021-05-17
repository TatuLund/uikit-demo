package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.components.UKNotification;
import org.vaadin.uikit.components.input.UKCombo;
import org.vaadin.uikit.components.layout.UKFlex;
import org.vaadin.uikit.components.layout.UKFlex.Direction;
import org.vaadin.uikit.components.layout.UKFlex.HorizontalAlignment;
import org.vaadin.uikit.components.layout.UKFlex.VerticalAlignment;
import org.vaadin.uikit.interfaces.UKFormSizing.FieldSize;
import org.vaadin.uikit.interfaces.UKFormSizing.FieldWidth;

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

        UKCombo<Item> select = new UKCombo<>();
        String[] array = { "Zero", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine" };
        List<Item> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            for (String item : array)
                list.add(new Item(i + "-" + item));
        }
        select.setItems(list);
        select.setSize(FieldSize.DEFAULT);
        select.setWidth(FieldWidth.MEDIUM);
        select.setTooltip("Select one of these");
        select.addValueChangeListener(event -> {
            UKNotification.show("Name: " + select.getValue().getName()
                    + " Number " + select.getValue().getNumber());
        });
        select.setItemLabelGenerator(item -> item.getName());
        select.setPlaceholder("number");
        select.focus();
        add(select);
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
