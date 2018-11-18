package ru.ldwx.storage;

import ru.ldwx.model.Employee;

import java.util.List;

public interface Storage {

    void clear();

    void update(Employee e);

    void save(Employee e);

    Employee get(String uuid);

    void delete(String uuid);

    List<Employee> getAllSorted();

    int size();
}
