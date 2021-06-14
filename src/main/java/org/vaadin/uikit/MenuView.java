package org.vaadin.uikit;

import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkIcons;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.navigation.UkNavItem;
import org.vaadin.uikit.navigation.UkDropdownNav;
import org.vaadin.uikit.navigation.UkNav;
import org.vaadin.uikit.navigation.UkNavbar;
import org.vaadin.uikit.navigation.UkNavbarItem;
import org.vaadin.uikit.navigation.UkSubNav;
import org.vaadin.uikit.navigation.UkNavbar.Alignment;
import org.vaadin.uikit.navigation.UkNavbar.Mode;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Menues")
@Route(value = "menu", layout = MainLayout.class)
public class MenuView extends UkFlex {

    public MenuView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setOverflow(OverflowMode.AUTO);
        setSizeFull();

        UkCard navbarCard = new UkCard("Navbar");
        UkNavbar navbar = new UkNavbar(Mode.CLICK, Alignment.CENTER);
        navbar.setWidth(FixedWidth.XXLARGE);
        navbar.addNavbarItem("Accordion", AccordionView.class);
        navbar.addNavbarItem("Combo", ComboView.class);
        navbar.addNavbarItem("Table", TableView.class);
        UkNavbarItem item = navbar.addNavbarItem("Drop");
        UkDropdownNav drop = item.addDropDown();
        drop.addMenuItem("Accordion", AccordionView.class);
        drop.addDivider();
        drop.addMenuItem("Combo", ComboView.class);
        drop.addMenuItem("Table", TableView.class);

        navbarCard.setContent(navbar);
        
        UkCard navCard = createNavCard();

        add(navbarCard,navCard);
    }

    private UkCard createNavCard() {
        UkNav menu = new UkNav();
        menu.setWidth(FixedWidth.LARGE);
        menu.setPadding();
        menu.setPrimary();
        menu.addHeader("Menu");
        menu.addDivider();
        menu.addMenuItem("Main 1",MainView.class);
        UkNavItem item1 = menu.addMenuItem("Main 2");
        item1.setIcon(UkIcons.MENU.create());
        item1.setTooltip("Toggle sub menu");
        menu.setAccordion(true);
        UkSubNav sub1 = item1.addSubMenu();
        sub1.setDivider();
        sub1.addMenuItem("Sub 1 - 1",AccordionView.class);
        sub1.addMenuItem("Sub 1 - 2",AccordionView.class);
        sub1.addMenuItem("Sub 1 - 3",AccordionView.class);
        UkNavItem item2 = menu.addMenuItem("Main 3");
        item2.setIcon(UkIcons.GRID.create());
        UkSubNav sub2 = item2.addSubMenu();
        sub2.setDivider();
        sub2.setAccordion(false);
        sub2.addMenuItem("Sub 2 - 1",ComboView.class);
        sub2.addMenuItem("Sub 2 - 2",ComboView.class);
        UkNavItem subItem = sub2.addMenuItem("Sub 2 - 3");
        UkSubNav subsub = subItem.addSubMenu();
        subsub.setDivider();
        subsub.addMenuItem("Subsub 1",TableView.class);
        subsub.addMenuItem("Subsub 2",TableView.class);
        menu.addMenuItem("Main 4",MainView.class);

        UkCard card = new UkCard("Menu");
        card.setContent(menu);
        return card;
    }
}
