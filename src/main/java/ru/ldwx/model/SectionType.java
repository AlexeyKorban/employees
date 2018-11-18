package ru.ldwx.model;

public enum SectionType {
    OBJECTIVE("Должность"),
    PERSONAL("Личные качества"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
