package local.ldwx.model;

public enum  ContactType {
    PHONE("Внутренний номер"),
    MOBILE("Мобильный"),
    SKYPE("Skype"),
    MAIL("Почта");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
