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
    public class MainLayout extends UkAppLayout implements RouterLayout {

        public MainLayout() {
           setMenu();
           setLogo("Demo application");
        }
    }

### It is a Flow framework

This demonstrator shows the flexibility of the Vaadin's core framework, which is called Flow. Vaadin has modular architecture, which means that design system, including its components is a separate module. In this demo application we actually use only the core framework, but not the components.

### Packages

* org.vaadin.uikit - Some demo views

* org.vaadin.uikit.components - Different kind of components, mostly about viewing data or wrapping content

* org.vaadin.uikit.components.layouts - Components purposed to be used as layouts

* org.vaadin.uikit.components.input - Set of field type components that can be used with Flow's Binder

* org.vaadin.uikit.components.interfaces - Generic API not specific to a single component has been defined in interfaces, which are implemented by many components.

* org.vaadin.uikit.components.navigation - Vertical and horizontal menu / navigation components

### Other sources

* /src/main/scss/custom.scss - Example of how to customize UIkit 
* /src/uikit - SASS source code of the UIkit 

## Notes

* This is not a best practice example, just proof of concept how to build Java API for another frontend stack
* You can't use all Vaadin normal components fully, in order to keep the UI compatible with UIkit, I would assume z-planes go differently with modals, notifications etc.
* This is work in progress, I will probably re-package this as re-usable add-on library later

## Running the app

* Run using `mvn jetty:run` and open [http://localhost:8080](http://localhost:8080) in the browser.

* If you want to run the application with custom UIkit theme, run `mvn jetty:run -Puikit`.

* If you want to run your app locally in the production mode, run `mvn jetty:run -Pproduction`.
(In production mode ~700 kilobytes of static resources are loaded when app starts)
