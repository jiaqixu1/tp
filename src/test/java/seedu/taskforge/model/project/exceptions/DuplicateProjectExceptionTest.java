package seedu.taskforge.model.project.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DuplicateProjectExceptionTest {

    @Test
    public void constructor_setsMessage() {
        assertEquals("Operation would result in duplicate projects", new DuplicateProjectException().getMessage());
    }
}
