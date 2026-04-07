package seedu.taskforge.model.person.exceptions;

/**
 * Signals that the operation is invalid due to the presence of an object different
 * from PersonProject types in the person's project list.
 */
public class InvalidPersonProjectTypeException extends RuntimeException {
    public InvalidPersonProjectTypeException() {
        super("Only PersonProject objects are allowed in person project list!");
    }
}
