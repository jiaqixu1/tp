package seedu.address.model.tag.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DuplicateTagExceptionTest {

    @Test
    public void constructor_setsMessage() {
        assertEquals("Operation would result in duplicate tags", new DuplicateTagException().getMessage());
    }
}
