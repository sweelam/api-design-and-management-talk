package com.sweelam.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    private final Properties properties = new Properties();

    public PropertyLoader() {
        var path = getClass().getClassLoader().getResource("application.properties").getPath();
        File file = new File(path);
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String load(String property) {
        return properties.getProperty(property);
    }
}
