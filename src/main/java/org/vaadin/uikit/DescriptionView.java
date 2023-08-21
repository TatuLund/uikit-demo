package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkDescriptionList;
import org.vaadin.uikit.components.UkTile;
import org.vaadin.uikit.components.UkTile.TileVariant;
import org.vaadin.uikit.components.interfaces.UkBorder.BorderStyle;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Descriptions")
@Route(value = "description", layout = MainLayout.class)
public class DescriptionView extends AbstractView {

    String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

    public DescriptionView() {
        UkCard card = new UkCard();
        card.setTitle("Descriptions");
        String[] array = { "Front end development", "Back end development",
                "CI/CD experience", "Java programming", "JavaScript coding",
                "SQL queries", "Integration tests", "Unit tests", "Selenium",
                "Maven" };
        List<Term> terms = new ArrayList<>();
        for (String term : array) {
            terms.add(new Term(term));
        }

        UkDescriptionList<Term> list = new UkDescriptionList<>(Term::getTerm,
                Term::getDescription, 3);
        list.setButtonBorder(BorderStyle.PILL);
        list.setComponentProvider(term -> {
            UkTile tile = new UkTile(term.getDescription());
            tile.setVariant(TileVariant.MUTED);
            tile.setBorder(BorderStyle.ROUNDED);
            tile.setPadding(PaddingSize.SMALL);
            return tile;
        });
        list.setTermMargin();
        list.setMargin();
        list.setItems(terms);

        card.setContent(list);
        card.setOverflow(OverflowMode.AUTO);

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
