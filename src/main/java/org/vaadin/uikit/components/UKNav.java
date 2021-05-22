package org.vaadin.uikit.components;

import java.util.List;
import java.util.stream.Collectors;

import org.vaadin.uikit.interfaces.UKMargin;
import org.vaadin.uikit.interfaces.UKPadding;
import org.vaadin.uikit.interfaces.UKTooltip;
import org.vaadin.uikit.interfaces.UKWidthAndHeight;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.html.Anchor;

public class UKNav extends Composite<UnorderedList>
        implements UKWidthAndHeight, UKMargin, UKPadding {

    public class UKNavSub extends UKNav {
        public UKNavSub() {
            setSub();
        }

        protected void setSub() {
            getElement().getClassList().add("uk-nav-sub");
        }
    }

    public class UKMenuItem extends Composite<ListItem> implements UKMargin, UKPadding, UKTooltip {
        private ListItem li = new ListItem();
        private Anchor anchor = null;
        private boolean subMenuPossible = false;
        private String caption;
        private RouterLink link = null;

        public UKMenuItem(String text) {
            subMenuPossible = true;
            anchor = new Anchor("");
            anchor.setText(text);
            this.caption = text;
        }

        public UKMenuItem(String text,
                Class<? extends Component> navigationTarget) {
            link = new RouterLink(text, navigationTarget);
            this.caption = text;
        }

        public UKMenuItem(String text, String href) {
            anchor = new Anchor(href, text);
            this.caption = text;
        }

        public UKMenuItem(Anchor anchor) {
            this.anchor = anchor;
            this.caption = anchor.getText();
        }

        public UKMenuItem(RouterLink link) {
            this.link = link;
            this.caption = link.getText();
        }

        public UKNavSub addSubMenu() {
            if (!subMenuPossible) {
                throw new IllegalStateException(
                        "Sub menu not possible in MenuItem having a link");
            }
            UKNavSub subMenu = new UKNavSub();
            li.addClassName("uk-parent");
            li.add(subMenu);
            return subMenu;
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

    private UnorderedList ul = new UnorderedList();

    public void setAccordion(boolean multiple) {
        if (multiple) {
            ul.getElement().setAttribute("uk-nav", "multiple: true");
        } else {
            ul.getElement().setAttribute("uk-nav", true);
        }
        ul.getElement().getClassList().add("uk-nav-parent-icon");
    }

    public UKMenuItem addMenuItem(String text) {
        UKMenuItem menuItem = new UKMenuItem(text);
        ul.add(menuItem);
        return menuItem;
    }

    public UKMenuItem addMenuItem(String text,
            Class<? extends Component> navigationTarget) {
        UKMenuItem menuItem = new UKMenuItem(text, navigationTarget);
        ul.add(menuItem);
        return menuItem;
    }

    public UKMenuItem addMenuItem(String text, String href) {
        UKMenuItem menuItem = new UKMenuItem(text, href);
        ul.add(menuItem);
        return menuItem;
    }

    public UKMenuItem addMenuItem(RouterLink link) {
        UKMenuItem menuItem = new UKMenuItem(link);
        ul.add(menuItem);
        return menuItem;
    }

    public UKMenuItem addMenuItem(Anchor anchor) {
        UKMenuItem menuItem = new UKMenuItem(anchor);
        ul.add(menuItem);
        return menuItem;
    }

    public void addDivider() {
        ListItem li = new ListItem();
        li.addClassName("uk-nav-divider");
        ul.add(li);
    }

    public void setDivider() {
        ul.addClassName("uk-nav-divider");
    }

    public void addHeader(String header) {
        ListItem li = new ListItem();
        li.setText(header);
        li.addClassName("uk-nav-header");
        ul.add(li);
    }

    public void setPrimary() {
        ul.removeClassName("uk-nav-default");
        ul.addClassName("uk-nav-primary");
    }

    public void toggle(UKMenuItem item) {
        List<Component> items = ul.getChildren().collect(Collectors.toList());
        int index = items.indexOf(item)+1;
        ul.getElement().executeJs("UIkit.nav($0).toggle($1)",
                ul.getElement(),index);
    }

    @Override
    protected UnorderedList initContent() {
        ul.addClassNames("uk-nav", "uk-nav-default");
        return ul;
    }
}
