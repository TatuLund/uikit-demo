package org.vaadin.uikit.components;

import org.vaadin.uikit.components.interfaces.UkBoxShadow;
import org.vaadin.uikit.components.interfaces.UkHidden;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;

import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.AbstractStreamResource;

@SuppressWarnings("serial")
public class UkImage extends Image implements UkHidden, UkBoxShadow, UkPadding, UkMargin {

    private static final PropertyDescriptor<String, String> srcDescriptor = PropertyDescriptors
            .attributeWithDefault("data-src", "");

    public UkImage() {
        getElement().setAttribute("uk-img", true);
    }

    public UkImage(String src, String alt) {
        this();
        setSrc(src);
        setAlt(alt);
    }

    public UkImage(AbstractStreamResource src, String alt) {
        this();
        setSrc(src);
        setAlt(alt);
    }

    @Override
    public void setSrc(String src) {
        set(srcDescriptor, src);
    }

    @Override
    public void setSrc(AbstractStreamResource src) {
        getElement().setAttribute("data-src", src);
    }

    public void setWidth(int width) {
        getElement().setAttribute("width", "" + width);
    }

    public void setHeight(int height) {
        getElement().setAttribute("height", "" + height);
    }

    public void setSizes(boolean sizes) {
        getElement().setAttribute("sizes", sizes);
    }
}
