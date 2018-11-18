package ru.ldwx.exception;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String uuid) {
        super("Employee " + uuid + " not exist", uuid);
    }
}
