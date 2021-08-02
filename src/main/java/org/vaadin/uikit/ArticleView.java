package org.vaadin.uikit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.vaadin.uikit.components.UkArticle;
import org.vaadin.uikit.components.UkArticle.ArticleHeaderSpan;
import org.vaadin.uikit.components.layout.UkContainer;
import org.vaadin.uikit.components.layout.UkContainer.ContainerMaxWidth;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.components.util.ClassResourceFactory;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Article")
@Route(value = "article", layout = MainLayout.class)
public class ArticleView extends UkFlex {

    public ArticleView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setOverflow(OverflowMode.AUTO);
        setSizeFull();
        
        UkArticle article = new UkArticle();        
        UkContainer container = new UkContainer();
        container.setMaxWidth(ContainerMaxWidth.EXPAND);
        container.setOverflow(OverflowMode.AUTO);
        add(container);
        try {
            File file = new ClassResourceFactory("lorem_ipsum.txt").getFile();
            article
                .withFile(file)
                .withHeading("Lorem Ipsum")
                .withMeta("Meta information")
                .withColumns()
                .withHeaderSpan(ArticleHeaderSpan.WITHOUT_LEAD)
                .withColumnDivider()
                .build();
            container.add(article);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
}
