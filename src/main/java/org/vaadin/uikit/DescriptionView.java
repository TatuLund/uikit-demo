package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkDescriptionList;
import org.vaadin.uikit.components.UkTile;
import org.vaadin.uikit.components.UkTile.TileVariant;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.components.layout.UkFlex.Direction;
import org.vaadin.uikit.components.layout.UkFlex.HorizontalAlignment;
import org.vaadin.uikit.components.layout.UkFlex.VerticalAlignment;
import org.vaadin.uikit.interfaces.UkBorder.BorderStyle;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = "description", layout = MainLayout.class)
public class DescriptionView extends UkFlex {

    String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

    public DescriptionView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setOverflow(OverflowMode.AUTO);
        setSizeFull();

        UkCard card = new UkCard();
        
        String[] array = { "Front end development", "Back end development", "CI/CD experience", "Java programming", "JavaScript coding", "SQL queries", "Integration tests",
                "Unit tests", "Selenium", "Maven" };
        List<Term> terms = new ArrayList<>();
        for (String term : array) {
            terms.add(new Term(term));
        }
        
        UkDescriptionList<Term> list = new UkDescriptionList<>(Term::getTerm,Term::getDescription);
        list.setComponentProvider(term -> {
            UkTile tile = new UkTile(term.getDescription());
            tile.setVariant(TileVariant.SECONDARY);
            tile.setBorder(BorderStyle.ROUNDED);
            return tile;
        });
        list.setItems(terms);
        list.setMargin();
        list.setTermMargin();

        card.setContent(list);

        add(card);
    }

    public class Term {
        private String term;
        private String description;

        public Term(String term) {
            this.term = term;
            this.description = loremIpsum;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
