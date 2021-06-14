package org.vaadin.uikit;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkLabel;
import org.vaadin.uikit.components.UkNotification;
import org.vaadin.uikit.components.layout.UkAccordion;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.components.layout.UkGrid;
import org.vaadin.uikit.components.layout.UkTable;
import org.vaadin.uikit.components.layout.UkGrid.GapModifier;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Accordion")
@Route(value = "accordion", layout = MainLayout.class)
public class AccordionView extends UkFlex {

    String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

    public AccordionView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setOverflow(OverflowMode.AUTO);
        setSizeFull();

        UkAccordion accordion = new UkAccordion();
        accordion.addItem("Tab 1", new Paragraph(loremIpsum));
        accordion.addItem("Tab 2", createGrid());
        accordion.addItem("Tab 3", createTable());
        accordion.addItem("Tab 4", new Paragraph(loremIpsum));
        accordion.setWidth(2, 3);
        accordion.addItemHiddenListener(event -> {
            UkNotification.show("Item " + event.getIndex() + " '"
                    + event.getItem().getCaption() + "' hidden");
            if (event.getIndex() == 0) {
                event.getItem().removeAll();
                event.getItem().add(new UkCard("Ipsum",
                        new Paragraph(loremIpsum.toUpperCase())));
            }
        });
        add(accordion);
    }

    Html createChart(String color) {
        Random random = new Random();
        List<Integer> data = random.ints(300, -100, 100).boxed()
                .collect(Collectors.toList());
        String svg = "<div><svg class=\"uk-animation-stroke\" style=\"width: 100%; height: 100%; --uk-animation-stroke: 100000;\" preserveAspectRatio=\"none\" viewBox=\"0 -100 600 200\"><polyline points=\"";
        int index = 0;
        for (int number : data) {
            svg += index + "," + number + " ";
            index += 2;
        }
        svg += "\" style=\"stroke-width: 1;fill:none;stroke:" + color
                + "\"></polyline></svg></div>";
        Html chart = new Html(svg);
        chart.getElement().getStyle().set("height", "100px");
        return chart;
    }

    UkGrid createGrid() {
        UkGrid grid = new UkGrid();
        grid.withRow(6).withCell(2, new UkCard("card", new UkLabel("Cell 2-6")))
                .withCell(1, new UkCard("card", new UkLabel("Cell 1-6")))
                .withCell(3, new UkCard("card", new UkLabel("Cell 3-6")))
                .withRow(4)
                .withCell(2, new UkCard("card", new UkLabel("Cell 2-4")))
                .withCell(2, new UkCard("card", new UkLabel("Cell 2-4")))
                .withRow(3)
                .withCell(2, new UkCard("card", new UkLabel("Cell 1-3")))
                .withCell(1, new UkCard("card", new UkLabel("Cell 2-3")))
                .build();
        grid.setGapModifier(GapModifier.COLLAPSE);
        return grid;
    }

    UkTable createTable() {
        UkTable table = new UkTable();
        table
                // .withHeaderRow()
                // .withCell("Title")
                // .withCell("Column 1")
                // .withCell("Column 2")
                .withRow()
                .withCell(1, 2, new UkCard("chart", createChart("black")))
                .withCell(new UkCard("card", new UkLabel("Cell 1,2")))
                .withCell(new UkCard("card", new UkLabel("Cell 2,2"))).withRow()
                .withCell(2, 1, loremIpsum).withRow().withCell("cell 1,3")
                .withCell("cell 2,3").withCell("cell 3,3")
                // .withFooterRow()
                // .withCell(3,1,"footer")
                .build();
        table.setDivider(true);
        table.setMiddle(true);
        return table;
    }
}
