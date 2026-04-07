package seedu.taskforge.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.taskforge.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.model.person.PersonTask;

public class JsonAdaptedPersonTaskTest {

    @Test
    public void toModelType_validPersonTask_returnsPersonTask() throws Exception {
        JsonAdaptedPersonTask jsonPersonTask = new JsonAdaptedPersonTask(1, 2);

        assertEquals(new PersonTask(1, 2), jsonPersonTask.toModelType());
    }

    @Test
    public void toModelType_nullProjectIndex_throwsIllegalValueException() {
        JsonAdaptedPersonTask jsonPersonTask = new JsonAdaptedPersonTask(null, 1);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedPersonTask.MISSING_FIELD_MESSAGE_FORMAT, "projectIndex"),
                jsonPersonTask::toModelType);
    }

    @Test
    public void toModelType_nullTaskIndex_throwsIllegalValueException() {
        JsonAdaptedPersonTask jsonPersonTask = new JsonAdaptedPersonTask(1, null);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedPersonTask.MISSING_FIELD_MESSAGE_FORMAT, "taskIndex"),
                jsonPersonTask::toModelType);
    }

    @Test
    public void toModelType_negativeIndices_throwsIllegalValueException() {
        JsonAdaptedPersonTask jsonPersonTask = new JsonAdaptedPersonTask(-1, 0);

        assertThrows(IllegalValueException.class, "projectIndex and taskIndex must be non-negative",
                jsonPersonTask::toModelType);
    }
}
