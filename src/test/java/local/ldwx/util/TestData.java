package local.ldwx.util;

import local.ldwx.model.*;

import java.time.Month;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Employee E1;
    public static final Employee E2;
    public static final Employee E3;
    public static final Employee E4;

    static {
        E1 = new Employee(UUID_1, "Name 1");
        E2 = new Employee(UUID_2, "Name 2");
        E3 = new Employee(UUID_3, "Name 3");
        E4 = new Employee(UUID_4, "Name 4");

        E1.setContact(ContactType.MAIL, "mail@mail.ru");
        E1.setContact(ContactType.MOBILE, "89548745648");

        E2.setContact(ContactType.MAIL, "asdf@mail.com");
        E2.setContact(ContactType.PHONE, "5648");

        E3.setContact(ContactType.MAIL, "mafsdff@gmail.ru");
        E4.setContact(ContactType.MOBILE, "89545487648");

        E1.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        E1.setSection(SectionType.PERSONAL, new TextSection("Personal data"));
        E1.setSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        E1.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        E1.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
        E1.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
    }
}
