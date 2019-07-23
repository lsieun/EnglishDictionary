package lsieun.dict.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    private static final String rootPath;
    private static final Properties props;

    static {
        rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String configPath = rootPath + "config.properties";
        System.out.println("READ Config File: " + configPath);
        props = new Properties();
        try {
            props.load(new FileInputStream(configPath));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static String getRootPath() {
        return rootPath;
    }

    public static String getProperty(String key) {
        String value = props.getProperty(key);
        System.out.println(key + " = " + value);
        return value;
    }

    public static void main(String[] args) throws IOException {
        String value = PropertyUtils.getProperty("english.dictionary.filepath");
    }
}
