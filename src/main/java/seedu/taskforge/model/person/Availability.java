package seedu.taskforge.model.person;

/**
 * Represents the availability status of a person based on their current workload.
 */
public enum Availability {
    FREE("Free"),
    AVAILABLE("Available"),
    BUSY("Busy"),
    OVERLOADED("Overloaded");

    private final String displayName;

    Availability(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
