package seedu.taskforge.model.person.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InvalidPersonTaskTypeExceptionTest {

    @Test
    public void constructor_defaultMessage() {
        InvalidPersonTaskTypeException exception = new InvalidPersonTaskTypeException();
        assertEquals("Only PersonTask objects are allowed in person task list!", exception.getMessage());
    }
}
