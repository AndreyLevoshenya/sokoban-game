package utils;

import java.io.*;

import static utils.Constants.CONFIG_FILE_IS_NOT_EXISTS;
import static utils.Constants.CONFIG_PATH;

public class Utils {
    public static InputStream getResource(String name) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(name);
    }

    public static void serialize(Config config) {
        try (FileOutputStream file = new FileOutputStream(CONFIG_PATH);
             ObjectOutputStream out = new ObjectOutputStream(file)) {

            out.writeObject(config);
        } catch (FileNotFoundException e) {
            try {
                new File(CONFIG_PATH).createNewFile();
                serialize(config);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config deserialize() {
        Config config = null;
        try (FileInputStream file = new FileInputStream(CONFIG_PATH);
             ObjectInputStream in = new ObjectInputStream(file)) {

            config = (Config) in.readObject();
        } catch (IOException ex) {
            System.out.println(CONFIG_FILE_IS_NOT_EXISTS);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return config;
    }
}
