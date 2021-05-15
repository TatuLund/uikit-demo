package org.vaadin.uikit;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.vaadin.uikit.UKCard.CardVariant;
import org.vaadin.uikit.UKGrid.GapModifier;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("accordion")
@StyleSheet("context://uikit.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.js")
public class AccordionView extends UKFlex {

	String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

	public AccordionView() {
    	setDirection(Direction.COLUMN);
    	setVerticalAlignment(VerticalAlignment.MIDDLE);
    	setHorizontalAlignment(HorizontalAlignment.AROUND);
    	setSizeFull();

    	UKAccordion accordion = new UKAccordion();
    	accordion.addItem("Tab 1", new Paragraph(loremIpsum));
    	accordion.addItem("Tab 2", createGrid());
    	accordion.addItem("Tab 3", createTable());
    	accordion.addItem("Tab 4", new Paragraph(loremIpsum));
    	accordion.setWidth(2, 3);
    	accordion.addItemHiddenListener(event -> {
    		UKNotification.show("Item "+event.getIndex()+" '"+event.getItem().getCaption()+"' hidden");
    		if (event.getIndex() == 0) {
    			event.getItem().removeAll();
    			event.getItem().add(new UKCard("Ipsum",new Paragraph(loremIpsum.toUpperCase())));
    		}
    	});
    	TextArea area = new TextArea();
    	area.setValue(loremIpsum);
    	area.setWidth("200px");
    	area.setMaxHeight("100px");
    	area.getElement().executeJs("$0.inputElement.disabled = true", area.getElement());
    	add(accordion,area,createTabSwitcher());
    }
	
	Html createChart(String color) {
		Random random = new Random();
		List<Integer> data = random.ints(300,-100,100).boxed().collect(Collectors.toList());
		String svg = "<div><svg class=\"uk-animation-stroke\" style=\"width: 100%; height: 100%; --uk-animation-stroke: 100000;\" preserveAspectRatio=\"none\" viewBox=\"0 -100 600 200\"><polyline points=\"";
		int index = 0;
		for (int number : data) {
			svg+=index+","+number+" ";
			index+=2;
		}
		svg+="\" style=\"stroke-width: 1;fill:none;stroke:"+color+"\"></polyline></svg></div>";
		Html chart = new Html(svg);
		chart.getElement().getStyle().set("height", "100px");
		return chart;
	}
	
	UKTabSwitcher createTabSwitcher() {
		UKTabSwitcher tabSwitcher = new UKTabSwitcher();
		UKCard card1 = new UKCard("Card 1", new Paragraph(loremIpsum));
		card1.setVariant(CardVariant.DEFAULT);
		UKCard card2 = new UKCard("Card 2", new Paragraph(loremIpsum));
		card2.setVariant(CardVariant.SECONDARY);
		UKCard card3 = new UKCard("Card 3: Chart", createChart("white"));
		card3.setVariant(CardVariant.PRIMARY);
		tabSwitcher.addItem("Tab 1", card1);
		tabSwitcher.addItem("Tab 2", card2);
		tabSwitcher.addItem("Tab 3", card3);
		tabSwitcher.addItem("Tab 4", createGrid());
//		tabSwitcher.setTabAlignment(TabAlignment.RIGHT);
//		tabSwitcher.setTabPlacement(TabPlacement.BOTTOM);
    	tabSwitcher.setWidth(2, 3);
    	tabSwitcher.addItemShownListener(event -> {
    		UKNotification.show("Item "+event.getIndex()+" '"+event.getItem().getCaption()+"' shown");    		
    	});
		return tabSwitcher;
	}
	
	UKGrid createGrid() {
		UKGrid grid = new UKGrid();
		grid
			.withRow(6)
			.withCell(2, new UKCard("card",new UKLabel("Cell 2-6")))
			.withCell(1, new UKCard("card",new UKLabel("Cell 1-6")))
			.withCell(3, new UKCard("card",new UKLabel("Cell 3-6")))
			.withRow(4)
			.withCell(2,new UKCard("card",new UKLabel("Cell 2-4")))
			.withCell(2,new UKCard("card",new UKLabel("Cell 2-4")))
			.withRow(3)
			.withCell(2,new UKCard("card",new UKLabel("Cell 1-3")))
			.withCell(1,new UKCard("card",new UKLabel("Cell 2-3")))
			.build();
		grid.setGapModifier(GapModifier.COLLAPSE);
		return grid;
	}

	UKTable createTable() {
		UKTable table = new UKTable();
		table
//			.withHeaderRow()
//			.withCell("Title")
//			.withCell("Column 1")
//			.withCell("Column 2")
			.withRow()
			.withCell(1, 2, new UKCard("chart",createChart("black")))
			.withCell(new UKCard("card",new UKLabel("Cell 1,2")))
			.withCell(new UKCard("card",new UKLabel("Cell 2,2")))
			.withRow()
			.withCell(2
					,1,loremIpsum)
			.withRow()
			.withCell("cell 1,3")
			.withCell("cell 2,3")
			.withCell("cell 3,3")
//			.withFooterRow()
//			.withCell(3,1,"footer")
			.build();
		table.setDivider(true);
		table.setMiddle(true);
		return table;
	}
}
