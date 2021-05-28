package org.vaadin.uikit.navigation;

import org.vaadin.uikit.components.UKIcon;
import org.vaadin.uikit.interfaces.UKMargin;
import org.vaadin.uikit.interfaces.UKPadding;
import org.vaadin.uikit.interfaces.UKTooltip;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.router.RouterLink;

public abstract class UKMenuItemBase extends Composite<ListItem> implements UKMargin, UKPadding, UKTooltip {
    protected ListItem li = new ListItem();
    private Anchor anchor = null;
    protected boolean subMenuPossible = false;
    private String caption;
    private RouterLink link = null;

    public UKMenuItemBase(String text) {
        subMenuPossible = true;
        anchor = new Anchor("");
        anchor.setText(text);
        this.caption = text;
    }

    public UKMenuItemBase(String text,
            Class<? extends Component> navigationTarget) {
        link = new RouterLink(text, navigationTarget);
        this.caption = text;
    }

    public UKMenuItemBase(String text, String href) {
        anchor = new Anchor(href, text);
        this.caption = text;
    }

    public UKMenuItemBase(Anchor anchor) {
        this.anchor = anchor;
        this.caption = anchor.getText();
    }

    public UKMenuItemBase(RouterLink link) {
        this.link = link;
        this.caption = link.getText();
    }

    public void setIcon(UKIcon icon) {
        if (anchor != null) {
            anchor.setText("");
            anchor.add(icon);
            anchor.add(caption);
        } else {
            link.setText("");
            link.add(icon);
            link.add(caption);
        }
    }

    @Override
    protected ListItem initContent() {
        if (anchor != null) {
            li.add(anchor);
        } else if (link != null) {
            li.add(link);
        }
        return li;
    }
}