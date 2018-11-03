package local.ldwx.storage;

import local.ldwx.exception.ExistStorageException;
import local.ldwx.exception.NotExistStorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static local.ldwx.util.TestData.*;

public abstract class AbstractStorageTest {

    private Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(E1);
        storage.save(E2);
        storage.save(E3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void save() {
        storage.save(E4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(E4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(E1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.delete(UUID_1);
    }

    @Test
    public void get() {
        Assert.assertEquals(E1, storage.get(UUID_1));
        Assert.assertEquals(E2, storage.get(UUID_2));
        Assert.assertEquals(E3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}