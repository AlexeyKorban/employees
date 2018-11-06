package local.ldwx.util;

import local.ldwx.storage.SqlStorage;
import local.ldwx.storage.Storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPS = "C:\\Projects\\employees\\src\\main\\resources\\db\\employees.properties";
    private static final Config INSTANCE = new Config();

    private final Storage storage;

    public Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(is);
            storage = new SqlStorage(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public Storage getStorage() {
        return storage;
    }
}
