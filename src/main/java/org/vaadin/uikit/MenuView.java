package org.vaadin.uikit;

import org.vaadin.uikit.components.UKCard;
import org.vaadin.uikit.components.UKIcons;
import org.vaadin.uikit.components.layout.UKFlex;
import org.vaadin.uikit.navigation.UKNavItem;
import org.vaadin.uikit.navigation.UKDropdownNav;
import org.vaadin.uikit.navigation.UKNav;
import org.vaadin.uikit.navigation.UKNavbar;
import org.vaadin.uikit.navigation.UKNavbarItem;
import org.vaadin.uikit.navigation.UKSubNav;
import org.vaadin.uikit.navigation.UKNavbar.Alignment;
import org.vaadin.uikit.navigation.UKNavbar.Mode;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.router.Route;

@Route("menu")
@StyleSheet("context://uikit.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.js")
public class MenuView extends UKFlex {

    public MenuView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setSizeFull();

        UKCard navbarCard = new UKCard("Navbar");
        UKNavbar navbar = new UKNavbar(Mode.CLICK, Alignment.CENTER);
        navbar.setWidth(FixedWidth.XXLARGE);
        navbar.addNavbarItem("Accordion", AccordionView.class);
        navbar.addNavbarItem("Combo", ComboView.class);
        navbar.addNavbarItem("Table", TableView.class);
        UKNavbarItem item = navbar.addNavbarItem("Drop");
        UKDropdownNav drop = item.addDropDown();
        drop.addMenuItem("Accordion", AccordionView.class);
        drop.addDivider();
        drop.addMenuItem("Combo", ComboView.class);
        drop.addMenuItem("Table", TableView.class);

        navbarCard.setContent(navbar);
        
        UKCard navCard = createNavCard();

        add(navbarCard,navCard);
    }

    private UKCard createNavCard() {
        UKNav menu = new UKNav();
        menu.setWidth(FixedWidth.LARGE);
        menu.setPadding();
        menu.setPrimary();
        menu.addHeader("Menu");
        menu.addDivider();
        menu.addMenuItem("Main 1",MainView.class);
        UKNavItem item1 = menu.addMenuItem("Main 2");
        item1.setIcon(UKIcons.MENU.create());
        item1.setTooltip("Toggle sub menu");
        menu.setAccordion(true);
        UKSubNav sub1 = item1.addSubMenu();
        sub1.setDivider();
        sub1.addMenuItem("Sub 1 - 1",AccordionView.class);
        sub1.addMenuItem("Sub 1 - 2",AccordionView.class);
        sub1.addMenuItem("Sub 1 - 3",AccordionView.class);
        UKNavItem item2 = menu.addMenuItem("Main 3");
        item2.setIcon(UKIcons.GRID.create());
        UKSubNav sub2 = item2.addSubMenu();
        sub2.setDivider();
        sub2.setAccordion(false);
        sub2.addMenuItem("Sub 2 - 1",ComboView.class);
        sub2.addMenuItem("Sub 2 - 2",ComboView.class);
        UKNavItem subItem = sub2.addMenuItem("Sub 2 - 3");
        UKSubNav subsub = subItem.addSubMenu();
        subsub.setDivider();
        subsub.addMenuItem("Subsub 1",TableView.class);
        subsub.addMenuItem("Subsub 2",TableView.class);
        menu.addMenuItem("Main 4",MainView.class);

        UKCard card = new UKCard("Menu");
        card.setContent(menu);
        return card;
    }
}
