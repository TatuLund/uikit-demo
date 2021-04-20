package org.vaadin.uikit;

import org.vaadin.uikit.UKCard.CardVariant;
import org.vaadin.uikit.UKFlex.Direction;
import org.vaadin.uikit.UKFlex.HorizontalAlignment;
import org.vaadin.uikit.UKFlex.VerticalAlignment;
import org.vaadin.uikit.UKGrid.GapModifier;
import org.vaadin.uikit.UKGrid.ResponsiveBreak;
import org.vaadin.uikit.UKTabSwitcher.TabAlignment;
import org.vaadin.uikit.UKTabSwitcher.TabPlacement;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
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
    	accordion.addItem("Tab 3", new Paragraph(loremIpsum));
    	accordion.addItem("Tab 4", new Paragraph(loremIpsum));
    	accordion.setWidth(2, 3);
    	add(accordion,createTabSwitcher());

    }

	UKTabSwitcher createTabSwitcher() {
		UKTabSwitcher tabSwitcher = new UKTabSwitcher();
		UKCard card1 = new UKCard("Card 1", new Paragraph(loremIpsum));
		card1.setVariant(CardVariant.DEFAULT);
		UKCard card2 = new UKCard("Card 2", new Paragraph(loremIpsum));
		card2.setVariant(CardVariant.SECONDARY);
		UKCard card3 = new UKCard("Card 3", new Paragraph(loremIpsum));
		card3.setVariant(CardVariant.PRIMARY);
		tabSwitcher.addItem("Tab 1", card1);
		tabSwitcher.addItem("Tab 2", card2);
		tabSwitcher.addItem("Tab 3", card3);
		tabSwitcher.addItem("Tab 4", createGrid());
		tabSwitcher.setTabAlignment(TabAlignment.RIGHT);
		tabSwitcher.setTabPlacement(TabPlacement.BOTTOM);
    	tabSwitcher.setWidth(2, 3);
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
}
