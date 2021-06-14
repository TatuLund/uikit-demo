package org.vaadin.uikit;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkLabel;
import org.vaadin.uikit.components.UkNotification;
import org.vaadin.uikit.components.UkCard.CardVariant;
import org.vaadin.uikit.components.layout.UkTabSwitcher;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.components.layout.UkGrid;
import org.vaadin.uikit.components.layout.UkGrid.GapModifier;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("TabSwitcher")
@Route(value = "tab", layout = MainLayout.class)
public class TabView extends UkFlex {

    String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

    public TabView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setSizeFull();
        add(createTabSwitcher());
    }

    UkTabSwitcher createTabSwitcher() {
        UkTabSwitcher tabSwitcher = new UkTabSwitcher();
        UkCard card1 = new UkCard("Card 1", new Paragraph(loremIpsum));
        card1.setVariant(CardVariant.DEFAULT);
        UkCard card2 = new UkCard("Card 2", new Paragraph(loremIpsum));
        card2.setVariant(CardVariant.SECONDARY);
        UkCard card3 = new UkCard("Card 3: Chart", createChart("white"));
        card3.setVariant(CardVariant.PRIMARY);
        tabSwitcher.addItem("Tab 1", card1);
        tabSwitcher.addItem("Tab 2", card2);
        tabSwitcher.addItem("Tab 3", card3);
        tabSwitcher.addItem("Tab 4", createGrid());
        // tabSwitcher.setTabAlignment(TabAlignment.RIGHT);
        // tabSwitcher.setTabPlacement(TabPlacement.BOTTOM);
        tabSwitcher.setWidth(2, 3);
        tabSwitcher.addItemShownListener(event -> {
            UkNotification.show("Item " + event.getIndex() + " '"
                    + event.getItem().getCaption() + "' shown");
        });
        return tabSwitcher;
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

}
