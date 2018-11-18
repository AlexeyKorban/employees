package ru.ldwx.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Employee " + uuid + " already exist", uuid);
    }
}
