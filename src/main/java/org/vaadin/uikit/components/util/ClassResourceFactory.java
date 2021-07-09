package org.vaadin.uikit.components.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

@SuppressWarnings("serial")
public class ClassResourceFactory implements InputStreamFactory {
    private InputStream is = null;
    private String fileName;
    private File file;

    public ClassResourceFactory(String fileName) throws FileNotFoundException {
        Objects.requireNonNull(fileName, "The fileName can't be null");
        this.fileName = fileName;
        URL resource = this.getClass().getClassLoader()
                .getResource(fileName);
        if (resource != null) {
            file = new File(resource.getFile());
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
            }
        } else {
            throw new FileNotFoundException(fileName);
        }
    }

    public StreamResource getStreamResource() {
        if (is == null) {
            return null;
        }
        StreamResource streamResource = new StreamResource(fileName,
                () -> is);
        return streamResource;
    }

    public File getFile() {
        return file;
    }
    
    @Override
    public InputStream createInputStream() {
        return is;
    }
}
