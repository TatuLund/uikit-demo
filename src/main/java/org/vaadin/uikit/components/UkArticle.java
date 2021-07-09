package org.vaadin.uikit.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Article;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;

@SuppressWarnings("serial")
public class UkArticle extends Composite<Article> {
    
    private H1 heading = null;
    private Paragraph lead;
    private Paragraph meta;
    private Article article = new Article(); 
    private List<Paragraph> paragraphs = new ArrayList<>();

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
    }

    @Override
    protected Article initContent() {
        return article;
    }
}
