package local.ldwx.storage;

import local.ldwx.model.Employee;
import local.ldwx.storage.sql.SqlHelper;

import java.sql.DriverManager;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbURL, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException();
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbURL, dbUser, dbPassword));
    }

    @Override
    public void clear() {

    }

    @Override
    public void update(Employee e) {

    }

    @Override
    public void save(Employee e) {

    }

    @Override
    public Employee get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public List<Employee> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
