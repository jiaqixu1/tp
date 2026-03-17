package seedu.taskforge.model.project.exceptions;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ProjectNotFoundExceptionTest {

    @Test
    public void constructor_defaultMessage_isNull() {
        assertNull(new ProjectNotFoundException().getMessage());
    }
}
