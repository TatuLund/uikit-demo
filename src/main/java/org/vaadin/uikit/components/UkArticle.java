package org.vaadin.uikit.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.vaadin.uikit.components.interfaces.UkText.TextAlignment;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Article;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;

@SuppressWarnings("serial")
public class UkArticle extends Composite<Article> {

    public enum ArticleHeaderSpan {
        WITH_LEAD, WITHOUT_LEAD;
    }

    public enum ArticleFileOption {
        LEAD, DROPCAP_FIRST, DROPCAP_ALL, DROPCAP_LEAD, NONE
    }

    private H1 heading = null;
    private Paragraph lead;
    private Paragraph meta;
    private Article article = new Article();
    private List<Paragraph> paragraphs = new ArrayList<>();
    private boolean first;
    private boolean dropcap;

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
        return withLead(leadText, false);
    }

    public UkArticle withLead(String leadText, boolean dropcap) {
        lead = new Paragraph();
        lead.setText(leadText);
        lead.addClassName("uk-text-lead");
        if (dropcap) {
            lead.addClassName("uk-dropcap");
        }
        return this;
    }

    public UkArticle withMeta(String metaText) {
        meta = new Paragraph();
        meta.setText(metaText);
        meta.addClassName("uk-article-meta");
        return this;
    }

    public UkArticle withParagraph(String text) {
        return withParagraph(text, false);
    }

    public UkArticle withParagraph(String text, boolean dropcap) {
        Paragraph p = new Paragraph();
        p.setText(text);
        if (dropcap) {
            p.addClassName("uk-dropcap");
        }
        paragraphs.add(p);
        return this;
    }

    public UkArticle withCitation(String text, boolean columnSpan) {
        Paragraph p = new Paragraph();
        p.setText(text);
        p.addClassNames("uk-text-emphasis", "uk-text-italic", "uk-margin-left");
        if (columnSpan) {
            p.addClassName("uk-column-span");
        }
        paragraphs.add(p);
        return this;
    }

    public UkArticle withParagraph(Component component) {
        return withParagraph(component, false);
    }

    public UkArticle withParagraph(Component component, boolean dropcap) {
        Paragraph p = new Paragraph();
        p.add(component);
        if (dropcap) {
            p.addClassName("uk-dropcap");
        }
        paragraphs.add(p);
        return this;
    }

    public UkArticle withFile(File file) throws IOException {
        return withFile(file, ArticleFileOption.NONE);
    }

    public UkArticle withFile(File file, ArticleFileOption... options)
            throws IOException {
        InputStream is = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        first = Stream.of(options)
                .anyMatch(option -> option == ArticleFileOption.LEAD);
        dropcap = Stream.of(options)
                .anyMatch(option -> option == ArticleFileOption.DROPCAP_FIRST);
        reader.lines().forEach(line -> {
            if (first) {
                withLead(line, Stream.of(options).anyMatch(
                        option -> option == ArticleFileOption.DROPCAP_LEAD));
                first = false;
            } else {
                if (dropcap) {
                    withParagraph(line, true);
                    dropcap = false;
                } else {
                    withParagraph(line, Stream.of(options).anyMatch(
                            option -> option == ArticleFileOption.DROPCAP_ALL));
                }
            }
        });
        reader.close();
        return this;
    }

    public UkArticle withText(String text) {
        return withText(text, false);
    }

    public UkArticle withText(String text, boolean lead) {
        first = lead;
        for (String line : text.split("\n")) {
            if (first) {
                withLead(line);
                first = false;
            } else {
                withParagraph(line);
            }
        }
        return this;
    }

    public UkArticle withColumns(int columns) {
        article.getClassNames().forEach(className -> {
            if (className.startsWith("uk-column-1")) {
                article.removeClassName(className);
            }
        });
        if (columns == 5) {
            article.addClassNames("uk-column-1-2@s", "uk-column-1-3@m",
                    "uk-column-1-4@l", "uk-column-1-5@xl");
        } else if (columns == 4) {
            article.addClassNames("uk-column-1-2@s", "uk-column-1-3@m",
                    "uk-column-1-4@l");
        } else if (columns == 3) {
            article.addClassNames("uk-column-1-2@m", "uk-column-1-3@l");
        } else if (columns == 2) {
            article.addClassNames("uk-column-1-2@l");
        }
        return this;
    }

    public UkArticle withTextAlignment(TextAlignment textAlignment) {
        for (TextAlignment alignment : TextAlignment.values()) {
            article.removeClassName(alignment.getAlignment());
        }
        article.addClassName(textAlignment.getAlignment());
        return this;
    }

    public UkArticle withColumns() {
        return withColumns(4);
    }

    public UkArticle withColumnDivider() {
        article.addClassName("uk-column-divider");
        return this;
    }

    public UkArticle withHeaderSpan(ArticleHeaderSpan articleHeaderSpan,
            TextAlignment alignment) {
        heading.addClassName("uk-column-span");
        heading.addClassName(alignment.getAlignment());
        meta.addClassName("uk-column-span");
        meta.addClassName(alignment.getAlignment());
        if (articleHeaderSpan == ArticleHeaderSpan.WITH_LEAD) {
            lead.addClassName("uk-column-span");
            lead.addClassName(alignment.getAlignment());
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
            first = false;
        });
    }

    @Override
    protected Article initContent() {
        return article;
    }
}
