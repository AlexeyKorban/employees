package local.ldwx.util;

import local.ldwx.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {

    @Test
    public void getStorage() {
        Storage storage = Config.get().getStorage();
        Assert.assertNotEquals(null, storage);
    }
}