package seedu.taskforge.model.person.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InvalidPersonProjectTypeExceptionTest {

    @Test
    public void constructor_defaultMessage() {
        InvalidPersonProjectTypeException exception = new InvalidPersonProjectTypeException();
        assertEquals("Only PersonProject objects are allowed in person project list!", exception.getMessage());
    }
}
