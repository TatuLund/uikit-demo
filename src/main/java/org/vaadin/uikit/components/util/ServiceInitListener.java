package org.vaadin.uikit.components.util;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@SuppressWarnings("serial")
public class ServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.addRequestHandler(new ContentRangeRequestHandler());
    }
}