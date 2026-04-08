package seedu.taskforge.model.person.exceptions;

/**
 * Signals that the operation is invalid due to the presence of an object different
 * from PersonTask types in the person's task list.
 */
public class InvalidPersonTaskTypeException extends RuntimeException {
    public InvalidPersonTaskTypeException() {
        super("Only PersonTask objects are allowed in person task list!");
    }
}
