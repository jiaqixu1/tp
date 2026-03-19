package seedu.taskforge.model.task;

import static seedu.taskforge.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.taskforge.model.project.Project;

public class TaskTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task(null));
    }

    @Test
    public void constructor_invalidTaskDescription_throwsIllegalArgumentException() {
        String invalidTaskDescription = "";
        String invalidTaskNameTooLong = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnqrstuvwxyzabcdefghijklmno";
        assertThrows(IllegalArgumentException.class, () -> new Task(invalidTaskDescription));
        assertThrows(IllegalArgumentException.class, () -> new Project(invalidTaskNameTooLong));
    }

    @Test
    public void isValidTaskDescription() {
        // null task name
        assertThrows(NullPointerException.class, () -> Task.isValidTaskDescription(null));
    }

}
