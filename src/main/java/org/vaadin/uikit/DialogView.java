package org.vaadin.uikit;

import org.vaadin.uikit.components.UkButton;
import org.vaadin.uikit.components.UkIcons;
import org.vaadin.uikit.components.UkInline;
import org.vaadin.uikit.components.UkModal;
import org.vaadin.uikit.components.UkNotification;
import org.vaadin.uikit.components.UkButton.ButtonVariant;
import org.vaadin.uikit.components.UkNotification.Status;
import org.vaadin.uikit.components.input.UkCheckbox;
import org.vaadin.uikit.components.input.UkTextArea;
import org.vaadin.uikit.components.input.UkTextField;
import org.vaadin.uikit.components.layout.UkForm;
import org.vaadin.uikit.components.layout.UkFlex;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Dialog")
@Route(value = "dialog", layout = MainLayout.class)
public class DialogView extends AbstractView {

    public DialogView() {
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
