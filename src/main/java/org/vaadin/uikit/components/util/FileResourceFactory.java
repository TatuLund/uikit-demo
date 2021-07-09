package org.vaadin.uikit.components.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

@SuppressWarnings("serial")
public class FileResourceFactory implements InputStreamFactory {
    private String fileName;
    private InputStream is = null;

    public FileResourceFactory(File file) throws FileNotFoundException {
        Objects.requireNonNull(file, "The file can't be null");
        fileName = file.getName();
        is = new FileInputStream(file);
    }

    public StreamResource getStreamResource() {
        if (is == null) {
            return null;
        }
        StreamResource streamResource = new StreamResource(fileName,
                () -> is);
        return streamResource;
    }

    @Override
    public InputStream createInputStream() {
        return is;
    }
}
