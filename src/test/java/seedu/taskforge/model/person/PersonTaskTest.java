package seedu.taskforge.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonTaskTest {

    @Test
    public void constructor_negativeProjectIndex_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PersonTask(-1, 0));
    }

    @Test
    public void constructor_negativeTaskIndex_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PersonTask(0, -1));
    }

    @Test
    public void getIndices_returnsExpectedValues() {
        PersonTask personTask = new PersonTask(2, 3);

        assertEquals(2, personTask.getProjectIndex());
        assertEquals(3, personTask.getTaskIndex());
    }

    @Test
    public void equalsHashCodeAndToString() {
        PersonTask taskA = new PersonTask(1, 2);
        PersonTask taskACopy = new PersonTask(1, 2);
        PersonTask taskB = new PersonTask(1, 3);

        assertTrue(taskA.equals(taskACopy));
        assertEquals(taskA.hashCode(), taskACopy.hashCode());
        assertFalse(taskA.equals(taskB));
        assertFalse(taskA.equals(1));
        assertEquals(PersonTask.class.getCanonicalName() + "{projectIndex=1, taskIndex=2}", taskA.toString());
    }
}
