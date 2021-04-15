package org.vaadin.uikit;

import org.vaadin.uikit.UKFlex.Direction;
import org.vaadin.uikit.UKFlex.HorizontalAlignment;
import org.vaadin.uikit.UKFlex.VerticalAlignment;
import org.vaadin.uikit.UKGrid.GapModifier;

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
    	add(accordion);
//    	UKGrid grid = createGrid();
//    	add(grid);
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
