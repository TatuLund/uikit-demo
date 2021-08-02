package org.vaadin.uikit.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Article;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;

@SuppressWarnings("serial")
public class UkArticle extends Composite<Article> {
    
    public enum ArticleHeaderSpan {
        WITH_LEAD, WITHOUT_LEAD;
    }

    private H1 heading = null;
    private Paragraph lead;
    private Paragraph meta;
    private Article article = new Article(); 
    private List<Paragraph> paragraphs = new ArrayList<>();
    private boolean first;

    public UkArticle() {
        article.addClassName("uk-article");
    }

    public UkArticle withHeading(String headingText) {
        heading = new H1();
        heading.setText(headingText);
        heading.addClassName("uk-article-title");
        return this;
    }

    public UkArticle withLead(String leadText) {
        lead = new Paragraph();
        lead.setText(leadText);
        lead.addClassName("uk-text-lead");
        return this;
    }

    public UkArticle withMeta(String metaText) {
        meta = new Paragraph();
        meta.setText(metaText);
        meta.addClassName("uk-article-meta");
        return this;
    }

    public UkArticle withParagraph(String text) {
        Paragraph p = new Paragraph();
        p.setText(text);
        paragraphs.add(p);
        return this;
    }

    public UkArticle withFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        first = true;
        reader.lines().forEach(line -> {
            if (first) {
                withLead(line);
                first = false;
            } else {
                withParagraph(line);
            }
        });
        reader.close();
        return this;
    }

    public UkArticle withColumns() {
        article.addClassNames("uk-column-1-2@s","uk-column-1-3@m","uk-column-1-4@l");
        return this;
    }

    public UkArticle withColumnDivider() {
        article.addClassName("uk-column-divider");
        return this;
    }

    public UkArticle withHeaderSpan(ArticleHeaderSpan articleHeaderSpan) {
        heading.addClassName("uk-column-span");
        meta.addClassName("uk-column-span");
        if (articleHeaderSpan == ArticleHeaderSpan.WITH_LEAD) {
            lead.addClassName("uk-column-span");
        }
        return this;
    }

    public void build() {
        if (heading != null) {
            article.add(heading);
        }
        if (meta != null) {
            article.add(meta);
        }
        if (lead != null) {
            article.add(lead);
        }
        paragraphs.forEach(paragraph -> {
            article.add(paragraph);
        });
    }

    @Override
    protected Article initContent() {
        return article;
    }
}
