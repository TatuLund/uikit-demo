package org.vaadin.uikit;

import org.vaadin.uikit.components.UkAlert;
import org.vaadin.uikit.components.UkButton;
import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkIcon;
import org.vaadin.uikit.components.UkIcons;
import org.vaadin.uikit.components.UkInline;
import org.vaadin.uikit.components.UkModal;
import org.vaadin.uikit.components.UkNotification;
import org.vaadin.uikit.components.UkOffCanvas;
import org.vaadin.uikit.components.UkProgress;
import org.vaadin.uikit.components.UkSpinner;
import org.vaadin.uikit.components.UkTile;
import org.vaadin.uikit.components.UkAlert.AlertVariant;
import org.vaadin.uikit.components.UkButton.ButtonVariant;
import org.vaadin.uikit.components.UkCard.CardVariant;
import org.vaadin.uikit.components.UkDropdown;
import org.vaadin.uikit.components.UkNotification.Position;
import org.vaadin.uikit.components.UkNotification.Status;
import org.vaadin.uikit.components.UkOffCanvas.AnimationMode;
import org.vaadin.uikit.components.UkTile.TileVariant;
import org.vaadin.uikit.components.input.UkCheckbox;
import org.vaadin.uikit.components.input.UkDateField;
import org.vaadin.uikit.components.input.UkRange;
import org.vaadin.uikit.components.input.UkTextArea;
import org.vaadin.uikit.components.input.UkTextField;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.components.layout.UkForm;
import org.vaadin.uikit.interfaces.UkFormSizing.FieldSize;
import org.vaadin.uikit.interfaces.UkFormSizing.FieldWidth;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
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

        UkProgress progress = new UkProgress();
        progress.setWidth(FixedWidth.MEDIUM);
        progress.setTooltip("Progress bar");
        progress.setValue(73);
        add(progress);

        UkSpinner spinner = new UkSpinner();
        add(spinner);

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

        UkModal dialog = new UkModal();
        dialog.setWidth(FixedWidth.XLARGE);
        dialog.setHeight(FixedHeight.LARGE);

        UkForm form = new UkForm();
        form.setHorizontal(true);

        UkTextField nameField = new UkTextField();
        UkInline inline = new UkInline(UkIcons.USER, nameField);
        inline.setIconFlip(true);
        nameField.setPlaceholder("name");
        UkTextField ageField = new UkTextField();
        ageField.setPlaceholder("age");
        UkCheckbox acceptField = new UkCheckbox();
        UkTextArea storyField = new UkTextArea();
        storyField.setPlaceholder("story");

        form.add("Name", inline);
        form.add("Age", ageField);
        form.add("Story", storyField);
        form.add("Accept", acceptField);

        dialog.add(form);
        // dialog.add(nameField,ageField,acceptField,storyField);

        UkButton cancelButton = new UkButton("Cancel");
        cancelButton.addClickListener(event -> {
            dialog.hide();
        });
        UkButton saveButton = new UkButton("Save");
        saveButton.setVariant(ButtonVariant.PRIMARY);

        dialog.addToFooter(cancelButton, saveButton);

        Person person = new Person();
        Binder<Person> binder = new Binder<>();
        binder.forField(nameField).asRequired("Required")
                .withValidator(new StringLengthValidator("Too long", 0, 20))
                .bind(Person::getName, Person::setName);
        binder.forField(ageField)
                .withConverter(new StringToIntegerConverter("Not a number"))
                .withValidator(
                        new IntegerRangeValidator("Minimum 18", 18, null))
                .bind(Person::getAge, Person::setAge);
        binder.forField(acceptField).bind(Person::isAccept, Person::setAccept);
        binder.forField(storyField).bind(Person::getStory, Person::setStory);

        UkButton openDialog = new UkButton("Edit");
        openDialog.addClickListener(event -> {
            dialog.show();
        });

        add(openDialog, dialog);
        openDialog.addClickListener(event -> {
            binder.readBean(person);
        });
        saveButton.addClickListener(event -> {
            try {
                binder.writeBean(person);
                dialog.hide();
                UkNotification note = new UkNotification();
                note.withStatus(Status.SUCCESS)
                        .view("Saved: " + person.getName() + " "
                                + person.getAge() + " " + person.getStory());

            } catch (ValidationException e) {
                UkNotification note = new UkNotification();
                note.withStatus(Status.DANGER).view("Person not valid");
            }
        });

        dialog.addModalHiddenListener(event -> {
            UkNotification.show("Thanks!");
        });
    }

    public class Person {
        private String name;
        private int age;
        private boolean accept;
        private String story;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isAccept() {
            return accept;
        }

        public void setAccept(boolean accept) {
            this.accept = accept;
        }

        public String getStory() {
            return story;
        }

        public void setStory(String story) {
            this.story = story;
        }
    }
}
