package org.vaadin.uikit.components.layout;

import org.vaadin.uikit.components.UkButton;
import org.vaadin.uikit.components.UkIcons;
import org.vaadin.uikit.components.UkOffCanvas;
import org.vaadin.uikit.components.UkOffCanvas.AnimationMode;
import org.vaadin.uikit.components.layout.UkFlex.Direction;
import org.vaadin.uikit.components.layout.UkFlex.HorizontalAlignment;
import org.vaadin.uikit.components.layout.UkFlex.VerticalAlignment;
import org.vaadin.uikit.interfaces.UkSizing.FixedHeight;
import org.vaadin.uikit.navigation.UkNav;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;

@StyleSheet("context://uikit.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.js")
public class UkAppLayout extends Composite<UkFlex> {

    UkFlex flex = new UkFlex();
    UkFlex content = new UkFlex();
    Div topBar = new Div();
    UkNav nav = new UkNav();
    UkOffCanvas offCanvas = new UkOffCanvas(AnimationMode.SLIDE);
    
    public UkAppLayout() {
        
    }

    public UkNav getNav() {
        return nav;
    }

    @Override
    protected UkFlex initContent() {
//        topBar.setHeight(FixedHeight.SMALL);
//        topBar.setDirection(Direction.ROW);
        UkButton menu = new UkButton(UkIcons.MENU.create());
        Div container = new Div();
        container.add(menu);
        container.addClassNames("uk-container","uk-container-expand");
        topBar.addClassNames("uk-section","uk-section-primary","uk-section-xsmall","uk-width-1-1");
        topBar.add(container);
        menu.addClickListener(event -> {
           offCanvas.show(); 
        });
        flex.setDirection(Direction.COLUMN);
        flex.setVerticalAlignment(VerticalAlignment.TOP);
        flex.add(topBar,content);
        flex.setSizeFull();
        offCanvas.setTitle("Menu");
        offCanvas.setContent(nav);
        flex.add(offCanvas);
        return flex;
    }
    
}
