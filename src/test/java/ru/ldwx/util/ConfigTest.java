package ru.ldwx.util;

import ru.ldwx.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void getStorage() {
        Storage storage = Config.get().getStorage();
        Assert.assertNotEquals(null, storage);
    }
}