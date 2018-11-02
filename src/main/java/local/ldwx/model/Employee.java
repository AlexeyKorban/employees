package local.ldwx.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class Employee implements Comparable<Employee>{

    private static final Employee EMPTY = new Employee();

    static {
        EMPTY.setSection(SectionType.OBJECTIVE, TextSection.EMPTY);
        EMPTY.setSection(SectionType.PERSONAL, TextSection.EMPTY);
        EMPTY.setSection(SectionType.QUALIFICATIONS, ListSection.EMPTY);
        EMPTY.setSection(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
        EMPTY.setSection(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
    }

    private String uuid;

    private String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Employee() {
    }

    public Employee(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public void setContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public void setSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(uuid, employee.uuid) &&
                Objects.equals(fullName, employee.fullName) &&
                Objects.equals(contacts, employee.contacts) &&
                Objects.equals(sections, employee.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Employee o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}
