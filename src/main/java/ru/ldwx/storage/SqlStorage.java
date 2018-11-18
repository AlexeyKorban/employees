package ru.ldwx.storage;

import ru.ldwx.exception.NotExistStorageException;
import ru.ldwx.model.ContactType;
import ru.ldwx.model.Employee;
import ru.ldwx.model.Section;
import ru.ldwx.model.SectionType;
import ru.ldwx.storage.sql.SqlHelper;
import ru.ldwx.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        sqlHelper.execute("DELETE FROM employee");
    }

    @Override
    public void update(Employee e) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE employee SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, e.getFullName());
                ps.setString(2, e.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(e.getUuid());
                }
            }
            deleteContacts(conn, e);
            deleteSections(conn, e);
            insertContacts(conn, e);
            insertSections(conn, e);
            return null;
        });
    }

    @Override
    public void save(Employee e) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO employee (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, e.getUuid());
                ps.setString(2, e.getFullName());
                ps.execute();
            }
            insertContacts(conn, e);
            insertSections(conn, e);
            return null;
        });
    }

    @Override
    public Employee get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Employee e;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM employee WHERE  uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                e = new Employee(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM  contact WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, e);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM  section WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, e);
                }
            }
            return e;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM employee WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Employee> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Employee> employeeMap = new LinkedHashMap<>();

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM employee ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    employeeMap.put(uuid, new Employee(uuid, rs.getString("full_name")));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Employee e = employeeMap.get(rs.getString("resume_uuid"));
                    addContact(rs, e);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Employee e = employeeMap.get(rs.getString("resume_uuid"));
                    addSection(rs, e);
                }
            }

            return new ArrayList<>(employeeMap.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM employee", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void addContact(ResultSet rs, Employee e) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            e.setContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Employee e) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            e.setSection(type, JsonParser.read(content, Section.class));
        }
    }

    private void deleteContacts(Connection conn, Employee e) throws SQLException {
        deleteAttributes(conn, e, "DELETE  FROM contact WHERE resume_uuid=?");
    }

    private void deleteSections(Connection conn, Employee e) throws SQLException {
        deleteAttributes(conn, e, "DELETE  FROM section WHERE resume_uuid=?");
    }

    private void deleteAttributes(Connection conn, Employee e, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getUuid());
            ps.execute();
        }
    }

    private void insertContacts(Connection conn, Employee e) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : e.getContacts().entrySet()) {
                ps.setString(1, e.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Employee e) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> entry : e.getSections().entrySet()) {
                ps.setString(1, e.getUuid());
                ps.setString(2, entry.getKey().name());
                Section section = entry.getValue();
                ps.setString(3, JsonParser.write(section, Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
