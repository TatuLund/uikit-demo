package org.vaadin.uikit;

import org.vaadin.uikit.UKAlert.AlertVariant;
import org.vaadin.uikit.UKButton.ButtonVariant;
import org.vaadin.uikit.UKCard.CardVariant;
import org.vaadin.uikit.UKNotification.Position;
import org.vaadin.uikit.UKNotification.Status;
import org.vaadin.uikit.UKOffCanvas.AnimationMode;
import org.vaadin.uikit.UKWidthAndHeight.FixedHeight;
import org.vaadin.uikit.UKWidthAndHeight.FixedWidth;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBooleanConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
@StyleSheet("context://uikit.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.js")
public class MainView extends UKFlex {

    public MainView() {
    	setDirection(Direction.COLUMN);
    	setVerticalAlignment(VerticalAlignment.MIDDLE);
    	setHorizontalAlignment(HorizontalAlignment.AROUND);
    	setSizeFull();
    	
    	add(new UKAlert("Demo app loaded",AlertVariant.SUCCESS));

    	UKCard card = new UKCard();
    	card.setWidth("500px");
    	card.setHeight("200px");
    	card.setTitle("Default");
    	String html = "<p>Lorem ipsum <a href=\"#\">dolor</a> sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>";
    	card.setContent(html);
    	card.setHoverEffect(true);
    	card.setBadge("Badge");
    	card.setVariant(CardVariant.SECONDARY);
    	card.setTooltip("Tooltip");
    	add(card);

    	UKProgress progress = new UKProgress();
    	progress.setWidth(FixedWidth.MEDIUM);
    	progress.setTooltip("Progress bar");
    	progress.setValue(73);
    	add(progress);

    	UnorderedList breadcrumb = new UnorderedList();
    	breadcrumb.addClassName("uk-breadcrumb");
    	ListItem item1 = new ListItem();
    	item1.add(new Anchor("#","Home"));
    	ListItem item2 = new ListItem();
    	item2.add(new Anchor("#","Linked Category"));
    	ListItem item3 = new ListItem();
    	item3.addClassName("uk-disabled");
    	item3.add(new Anchor("#","Disabled Category"));
    	ListItem item4 = new ListItem();
    	item4.add(new Span("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
    	breadcrumb.add(item1,item2,item3,item4);
    	add(breadcrumb);
    	
    	UKIcon icon = UKIcons.CHECK.create();
    	icon.getElement().setAttribute("uk-tooltip", "This is an icon");
    	add(icon);

    	UKButton button = new UKButton("Click me");
    	button.addClickListener(event -> {
    		UKNotification notification = new UKNotification();
    		notification
    			.withPosition(Position.BOTTOM_CENTER)
    			.withStatus(Status.SUCCESS)
    			.view("Notification message");
    	});
    	add(button);

    	UKOffCanvas offcanvas = new UKOffCanvas(AnimationMode.SLIDE);
    	UKButton openButton = new UKButton("Open drawer");
    	openButton.setTooltip("Open off-canvas");
    	openButton.addClickListener(event -> {
    		offcanvas.show();
    	});
    	
    	offcanvas.setTitle("Title");
    	offcanvas.setFlip(true);
    	offcanvas.setCloseButtonVisible(false);
    	offcanvas.setEscClose(false);
    	Paragraph p = new Paragraph("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
    	offcanvas.setContent(p);
    	add(offcanvas,openButton);
    	offcanvas.getElement().addEventListener("hide", event -> {
    		System.out.println("Drawer closed");
    	});
    	
    	UKModal dialog = new UKModal();
    	dialog.setWidth(FixedWidth.XLARGE);
    	dialog.setHeight(FixedHeight.LARGE);
    	
    	UKForm form = new UKForm();
    	form.setHorizontal(true);

    	UKTextField nameField = new UKTextField();
    	UKInline inline = new UKInline(UKIcons.USER, nameField);
    	nameField.setPlaceholder("name");
    	UKTextField ageField = new UKTextField();
    	ageField.setPlaceholder("age");
    	UKCheckbox acceptField = new UKCheckbox();
    	UKTextArea storyField = new UKTextArea();
    	storyField.setPlaceholder("story");
    	
    	form.add("Name",inline);
    	form.add("Age",ageField);
    	form.add("Story",storyField);
    	form.add("Accept",acceptField);

    	dialog.add(form);
//    	dialog.add(nameField,ageField,acceptField,storyField);

    	UKButton cancelButton = new UKButton("Cancel");
    	cancelButton.addClickListener(event -> {
    		dialog.hide();
    	});
    	UKButton saveButton = new UKButton("Save");
    	saveButton.setVariant(ButtonVariant.PRIMARY);

    	dialog.addToFooter(cancelButton,saveButton);

    	Person person = new Person();
    	Binder<Person> binder = new Binder<>();
    	binder.forField(nameField)
    		.asRequired("Required")
    		.withValidator(new StringLengthValidator("Too long",0,20))
    		.bind(Person::getName,Person::setName);
    	binder.forField(ageField)
    		.withConverter(new StringToIntegerConverter("Not a number"))
    		.withValidator(new IntegerRangeValidator("Minimum 18",18,null))
    		.bind(Person::getAge,Person::setAge);
    	binder.forField(acceptField).bind(Person::isAccept,Person::setAccept);
    	binder.forField(storyField).bind(Person::getStory,Person::setStory);

    	UKButton openDialog = new UKButton("Edit");
    	openDialog.addClickListener(event -> {
    		dialog.show();
    	});

    	add(openDialog,dialog);
    	openDialog.addClickListener(event -> {
    		binder.readBean(person);
    	});
    	saveButton.addClickListener(event -> {
    		try {
				binder.writeBean(person);
				dialog.hide();
				UKNotification note = new UKNotification();
				note
					.withStatus(Status.SUCCESS)
					.view("Saved: "+person.getName()+" "+person.getAge()+" "+person.getStory());
				
			} catch (ValidationException e) {
				UKNotification note = new UKNotification();
				note
					.withStatus(Status.DANGER)
					.view("Person not valid");
			}
    	});
    	dialog.getElement().addEventListener("hide", event -> {
    		System.out.println("Dialog closed");
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
