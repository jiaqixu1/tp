package seedu.taskforge.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.model.task.Task;

public class JsonAdaptedTaskTest {

    private static final String VALID_TASK = "Refactor";
    private static final String INVALID_TASK = "Refactor*";

    @Test
    public void toModelType_doneTask_success() throws Exception {
        JsonAdaptedTask adaptedTask = new JsonAdaptedTask(VALID_TASK, "alpha", true);

        Task modelTask = adaptedTask.toModelType();

        assertTrue(modelTask.getStatus());
    }

    @Test
    public void toModelType_missingDoneField_defaultsToNotDone() throws Exception {
        JsonAdaptedTask adaptedTask = new JsonAdaptedTask(VALID_TASK, "alpha");

        Task modelTask = adaptedTask.toModelType();

        assertFalse(modelTask.getStatus());
    }

    @Test
    public void toModelType_invalidTask_throwsIllegalValueException() {
        JsonAdaptedTask adaptedTask = new JsonAdaptedTask(INVALID_TASK, "alpha", false);
        assertThrows(IllegalValueException.class, Task.MESSAGE_CONSTRAINTS, adaptedTask::toModelType);
    }
}

