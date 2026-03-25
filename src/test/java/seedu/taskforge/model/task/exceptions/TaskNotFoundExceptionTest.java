package seedu.taskforge.model.task.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskNotFoundExceptionTest {

    @Test
    public void constructor_defaultMessage_success() {
        TaskNotFoundException exception = new TaskNotFoundException();

        assertEquals("The specified task cannot be found", exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }
}
