package seedu.address.model.tag.exceptions;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class TagNotFoundExceptionTest {

    @Test
    public void constructor_defaultMessage_isNull() {
        assertNull(new TagNotFoundException().getMessage());
    }
}
