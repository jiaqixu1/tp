package seedu.taskforge.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskforge.model.task.exceptions.DuplicateTaskException;
import seedu.taskforge.model.task.exceptions.TaskNotFoundException;

public class UniquePersonTaskListTest {

    private final UniquePersonTaskList uniquePersonTaskList = new UniquePersonTaskList();
    private final PersonTask taskRefA = new PersonTask(0, 0);
    private final PersonTask taskRefB = new PersonTask(0, 1);

    @Test
    public void contains_nullPersonTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonTaskList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniquePersonTaskList.contains(taskRefA));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniquePersonTaskList.add(taskRefA);
        assertTrue(uniquePersonTaskList.contains(taskRefA));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonTaskList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        uniquePersonTaskList.add(taskRefA);
        assertThrows(DuplicateTaskException.class, () -> uniquePersonTaskList.add(new PersonTask(0, 0)));
    }

    @Test
    public void setPersonTask_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonTaskList.setPersonTask(null, taskRefA));
    }

    @Test
    public void setPersonTask_nullEdited_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonTaskList.setPersonTask(taskRefA, null));
    }

    @Test
    public void setPersonTask_targetNotFound_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniquePersonTaskList.setPersonTask(taskRefA, taskRefB));
    }

    @Test
    public void setPersonTask_sameTask_success() {
        uniquePersonTaskList.add(taskRefA);
        uniquePersonTaskList.setPersonTask(taskRefA, taskRefA);

        UniquePersonTaskList expected = new UniquePersonTaskList();
        expected.add(taskRefA);
        assertEquals(expected, uniquePersonTaskList);
    }

    @Test
    public void setPersonTask_differentTask_success() {
        uniquePersonTaskList.add(taskRefA);
        uniquePersonTaskList.setPersonTask(taskRefA, taskRefB);

        UniquePersonTaskList expected = new UniquePersonTaskList();
        expected.add(taskRefB);
        assertEquals(expected, uniquePersonTaskList);
    }

    @Test
    public void setPersonTask_nonUniqueTask_throwsDuplicateTaskException() {
        uniquePersonTaskList.add(taskRefA);
        uniquePersonTaskList.add(taskRefB);
        assertThrows(DuplicateTaskException.class, () -> uniquePersonTaskList.setPersonTask(taskRefA, taskRefB));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonTaskList.remove(null));
    }

    @Test
    public void remove_taskNotFound_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniquePersonTaskList.remove(taskRefA));
    }

    @Test
    public void remove_existingTask_success() {
        uniquePersonTaskList.add(taskRefA);
        uniquePersonTaskList.remove(taskRefA);
        assertEquals(new UniquePersonTaskList(), uniquePersonTaskList);
    }

    @Test
    public void setPersonTasks_nullUniquePersonTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniquePersonTaskList.setPersonTasks((UniquePersonTaskList) null));
    }

    @Test
    public void setPersonTasks_uniquePersonTaskList_replacesOwnList() {
        uniquePersonTaskList.add(taskRefA);
        UniquePersonTaskList replacement = new UniquePersonTaskList();
        replacement.add(taskRefB);

        uniquePersonTaskList.setPersonTasks(replacement);
        assertEquals(replacement, uniquePersonTaskList);
    }

    @Test
    public void setPersonTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniquePersonTaskList.setPersonTasks((List<PersonTask>) null));
    }

    @Test
    public void setPersonTasks_list_replacesOwnList() {
        uniquePersonTaskList.add(taskRefA);
        uniquePersonTaskList.setPersonTasks(Collections.singletonList(taskRefB));

        UniquePersonTaskList expected = new UniquePersonTaskList();
        expected.add(taskRefB);
        assertEquals(expected, uniquePersonTaskList);
    }

    @Test
    public void setPersonTasks_listWithDuplicates_throwsDuplicateTaskException() {
        List<PersonTask> duplicates = Arrays.asList(taskRefA, new PersonTask(0, 0));
        assertThrows(DuplicateTaskException.class, () -> uniquePersonTaskList.setPersonTasks(duplicates));
    }

    @Test
    public void asUnmodifiableObservableList_modify_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniquePersonTaskList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equalsAndHashCode() {
        UniquePersonTaskList first = new UniquePersonTaskList();
        UniquePersonTaskList second = new UniquePersonTaskList();

        first.add(taskRefA);
        first.add(taskRefB);
        second.add(taskRefB);
        second.add(taskRefA);

        assertTrue(first.equals(second));
        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(1));
    }
}
