package utils;

import java.io.InputStream;

public class Utils {
    public static InputStream getResource(String name) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(name);
    }
}
