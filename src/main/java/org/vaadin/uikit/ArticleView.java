package org.vaadin.uikit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import org.vaadin.uikit.components.UkArticle;
import org.vaadin.uikit.components.UkArticle.ArticleHeaderSpan;
import org.vaadin.uikit.components.UkArticle.ArticleFileOption;
import org.vaadin.uikit.components.interfaces.UkText.TextAlignment;
import org.vaadin.uikit.components.UkImage;
import org.vaadin.uikit.components.layout.UkContainer;
import org.vaadin.uikit.components.layout.UkContainer.ContainerMaxWidth;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.components.util.ClassResourceFactory;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Article")
@Route(value = "article", layout = MainLayout.class)
public class ArticleView extends AbstractView {

    String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

    public ArticleView() {
        UkArticle article = new UkArticle();        
        UkContainer container = new UkContainer();
        container.setMaxWidth(ContainerMaxWidth.EXPAND);
        container.setOverflow(OverflowMode.AUTO);
        add(container);
        try {
            File file = new ClassResourceFactory("lorem_ipsum.txt").getFile();
            article
                .withLead(loremIpsum)
                .withParagraph(new UkImage("photo.jpg","Photo"))
                .withFile(file,ArticleFileOption.DROPCAP_FIRST)
                .withHeading("Lorem ipsum dolor sit amet")
                .withTextAlignment(TextAlignment.JUSTIFY)
                .withMeta("By John Doe "+ LocalDate.now().toString())
                .withColumns(3)
                .withHeaderSpan(ArticleHeaderSpan.WITH_LEAD,TextAlignment.CENTER)
                .build();
            container.add(article);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
}
