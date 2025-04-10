package my.study.animal.component;

public enum Gender {
    FEMALE("암컷"),
    MALE("숫컷"),
    NONE("무성");

    private final String displayText;

    Gender(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }
}
