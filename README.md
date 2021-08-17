# UIkit demo with Vaadin

Vaadin in addition its own components has Java API for standard html elements. This makes possible to use standard html markup based toolkits such as Bootstrap or UIkit. This demo app is a proof of concept how to use UIkit with Vaadin.

The whole concept of this demo application is about creating Java API abstraction to the library.

## Overview

### Getting started

Add style imports on MainLayout

    @StyleSheet("context://uikit.min.css")

Or

    @StyleSheet("context://custom.css")


And the needed JavaScript imports to MainLayout

    @JavaScript("context://uikit.min.js")
    @JavaScript("context://uikit-icons.min.js")

The simplest starting point is to extend UkAppLayout class that builds a simple application frame with a menu.

    @StyleSheet("context://uikit.min.css")
    @JavaScript("context://uikit.min.js")
    @JavaScript("context://uikit-icons.min.js")
    @NoTheme
    public class MainLayout extends UkAppLayout implements RouterLayout {

        public MainLayout() {
           setMenu();
           setLogo("Demo application");
        }
    }

The `@NoTheme` disables Vaadin's own Lumo theme. Use this if you use only the UIkit and do not need Lumo for Vaadin's own components.

### It is a Vaadin's Flow framework

This demonstrator shows the flexibility of the Vaadin's core framework, which is called Flow. Vaadin has modular architecture, which means that design system, including its components is a separate module. In this demo application we actually use only the core framework, but not the components.

### Packages

* org.vaadin.uikit - Some demo views

* org.vaadin.uikit.components - Different kind of components, mostly about viewing data or wrapping content

* org.vaadin.uikit.components.layouts - Components purposed to be used as layouts

* org.vaadin.uikit.components.input - Set of field type components that can be used with Flow's Binder

* org.vaadin.uikit.components.interfaces - Generic API not specific to a single component has been defined in interfaces, which are implemented by many components.

* org.vaadin.uikit.components.navigation - Vertical and horizontal menu / navigation components

### Components

There is about 50 components in the collection now.

#### UkAlert

A component with `SUCCESS`,`PRIMARY`, `WARNING` and `DANGER` styles, which typically has a text and disappears automatically after set timeout or clicking integrated close button. Has event listener for closing event.

#### UkArticle

A component to show an article or document. The content is wrapped in `article` html tag. There is fluent API to construct the API from heading, lead, paragraphs and citations etc. The paragraphs can be read from a `File` or `String`. There some styling options, like enable responsive columns between 1-5. 

#### UkBadge

A `span` with a Badge style.

#### UkBeanTable<T>

Variant of html `table` based component, which is populated by beans from `DataProvider`. The component supports paging and if you use `DataProvider` from callbacks, the pages are lazy loaded from the backend. You can populate table's cells with `String`, `Component` etc. There are is alternative construtor to autogenerate columns from bean properties by introspection. There is also `UkTable` component that has different kind of Java API purposed for table as a layout use case. The both are using the same UIkit styles for html `table`.

#### UkButton

A button component with `DEFAULT`,`PRIMARY`, `SECONDARY`, `DANGER`, `LINK` and `TEXT` styles. Uses native html `button` element. Caption can contain text and/or icon. Has click listener for click event. Also used in in UkDropdown, and paging buttons of `UkBeanTable` and `UkDescriptionList`.

#### UkCard

A single component (or text) containing component with title, optional badge. Has  `DEFAULT`,`PRIMARY` and `SECONDARY` variants.

#### UkDescriptionList<T>

List of term and description pairs properly constructed with `dl`, `dt` and `dd` html elements. The list is populated by beans from `DataProvider`. The component supports paging and if you use `DataProvider` from callbacks, the pages are lazy loaded from the backend. The description can be populated by text `String` or `Component`.

#### UkDropdown

A component that wraps a `UkButton` and dropdown that opens when the button is clicked. The dropdown can be populated by a `Component`.

#### UkIcon

A `span` containing icon from `UkIcons` collection. The size can be defined.

#### UkLabel

A `span` with a Label style. Not to be mixed with proper html `label`. Use `UkFormItem` of `UkForm` to wrap proper label with a field component.

### Other sources

* /src/main/scss/custom.scss - Example of how to customize UIkit. Use `mvn -Puikit` to compile the theme.
* /src/uikit - SASS source code of the UIkit 

## Notes

* This is not a best practice example, just proof of concept how to build Java API for another frontend stack
* You can't use all Vaadin normal components fully, in order to keep the UI compatible with UIkit, I would assume z-planes go differently with modals, notifications etc.
* This is work in progress, I will probably re-package this as re-usable add-on library later

## Running the app

* Run using `mvn jetty:run` and open [http://localhost:8080](http://localhost:8080) in the browser.

* If you want to run the application with custom UIkit theme, run `mvn jetty:run -Puikit`, which will include additional SASS compilation step.

* If you want to run your app locally in the production mode, run `mvn jetty:run -Pproduction`.
(In production mode ~700 kilobytes of static resources are loaded when app starts)
