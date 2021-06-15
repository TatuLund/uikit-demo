package org.vaadin.uikit;

import org.vaadin.uikit.components.UkAlert;
import org.vaadin.uikit.components.UkButton;
import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkIcon;
import org.vaadin.uikit.components.UkIcons;
import org.vaadin.uikit.components.UkNotification;
import org.vaadin.uikit.components.UkOffCanvas;
import org.vaadin.uikit.components.UkTile;
import org.vaadin.uikit.components.UkAlert.AlertVariant;
import org.vaadin.uikit.components.UkCard.CardVariant;
import org.vaadin.uikit.components.UkDropdown;
import org.vaadin.uikit.components.UkNotification.Position;
import org.vaadin.uikit.components.UkNotification.Status;
import org.vaadin.uikit.components.UkOffCanvas.AnimationMode;
import org.vaadin.uikit.components.UkTile.TileVariant;
import org.vaadin.uikit.components.input.UkCheckbox;
import org.vaadin.uikit.components.input.UkDateField;
import org.vaadin.uikit.components.input.UkRange;
import org.vaadin.uikit.components.interfaces.UkFormSizing.FieldSize;
import org.vaadin.uikit.components.interfaces.UkFormSizing.FieldWidth;
import org.vaadin.uikit.components.layout.UkFlex;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@PageTitle("Miscellaneous")
@Route(value = "", layout = MainLayout.class)
public class MainView extends UkFlex {

    public MainView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setSizeFull();

        UkAlert alert = new UkAlert("Demo app loaded", AlertVariant.SUCCESS);
        alert.addAlertHiddenListener(event -> {
            UkNotification.show("Ack!");
        });
        add(alert);

        UkDropdown dropdown = new UkDropdown(true); 
        dropdown.setCaption("Dropdown");
        dropdown.addDropdownHiddenListener(event -> {
            UkNotification.show("Dropdown hidden");
        });
        add(dropdown);
        
        UkCard card = new UkCard();
        card.setWidth("500px");
        card.setHeight("200px");
        card.setTitle("Default");
        String html = "<p>Lorem ipsum <a href=\"#\">dolor</a> sit amet, <b>consectetur</b> adipiscing elit, sed do eiusmod <i>tempor</i> incididunt ut labore et dolore magna aliqua.</p>";
        card.setContent(html);
        card.setHoverEffect(true);
        card.setBadge("Badge");
        card.setVariant(CardVariant.SECONDARY);
        dropdown.add(card);

        UkRange range = new UkRange(1, 10, 0.1);
        range.setTooltip("Range slider");
        range.setValue(5d);
        range.addValueChangeListener(event -> {
            UkNotification.show("Value: " + event.getValue());
        });
        range.setWidth(FieldWidth.MEDIUM);
        range.setSize(FieldSize.SMALL);
        add(range);

        UkDateField dateField = new UkDateField();
        dateField.setWidth(FieldWidth.MEDIUM);
        dateField.setSize(FieldSize.SMALL);
        dateField.addValueChangeListener(event -> {
            UkNotification.show("Date: " + event.getValue());
        });
        add(dateField);
        
        UkCheckbox check = new UkCheckbox();
        check.addValueChangeListener(event -> {
            UkNotification.show("Value: " + event.getValue());
        });
        add(check);

        // UnorderedList breadcrumb = new UnorderedList();
        // breadcrumb.addClassName("uk-breadcrumb");
        // ListItem item1 = new ListItem();
        // item1.add(new Anchor("#","Home"));
        // ListItem item2 = new ListItem();
        // item2.add(new Anchor("#","Linked Category"));
        // ListItem item3 = new ListItem();
        // item3.addClassName("uk-disabled");
        // item3.add(new Anchor("#","Disabled Category"));
        // ListItem item4 = new ListItem();
        // item4.add(new Span("Lorem ipsum dolor sit amet, consectetur
        // adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
        // magna aliqua."));
        // breadcrumb.add(item1,item2,item3,item4);
        // add(breadcrumb);

        UkIcon icon = UkIcons.CHECK.create();
        icon.getElement().setAttribute("uk-tooltip", "This is an icon");
        add(icon);

        UkButton button = new UkButton("Click me");
        button.addClickListener(event -> {
            UkNotification notification = new UkNotification();
            notification.withPosition(Position.BOTTOM_CENTER)
                    .withStatus(Status.SUCCESS)
                    .view("Notification message " + range.getValue());
        });
        add(button);

        UkOffCanvas offcanvas = new UkOffCanvas(AnimationMode.SLIDE);
        UkButton openButton = new UkButton("Open drawer");
        openButton.setTooltip("Open off-canvas");
        openButton.addClickListener(event -> {
            offcanvas.show();
        });

        offcanvas.setTitle("Title");
        offcanvas.setFlip(true);
        offcanvas.setCloseButtonVisible(false);
        offcanvas.setEscClose(false);
        UkTile tile = new UkTile(new Paragraph(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."));
        tile.setPadding(PaddingSize.SMALL);
        tile.setVariant(TileVariant.PRIMARY);
        offcanvas.setContent(tile);
        add(offcanvas, openButton);
        offcanvas.addOffCanvasHiddenListener(event -> {
            UkNotification.show("Good bye!");
            offcanvas.setFlip(false);
        });

    }
}
