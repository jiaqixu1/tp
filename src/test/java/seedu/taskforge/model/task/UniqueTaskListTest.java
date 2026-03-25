package seedu.taskforge.model.task;

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

public class UniqueTaskListTest {

    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();
    private final Task taskA = new Task("Task A");
    private final Task taskB = new Task("Task B");

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(taskA));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueTaskList.add(taskA);
        assertTrue(uniqueTaskList.contains(taskA));
    }

    @Test
    public void contains_equivalentTaskInList_returnsTrue() {
        uniqueTaskList.add(taskA);
        assertTrue(uniqueTaskList.contains(new Task("Task A", "Alpha")));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        uniqueTaskList.add(taskA);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.add(new Task("Task A")));
    }

    @Test
    public void setTask_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(null, taskA));
    }

    @Test
    public void setTask_nullEdited_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(taskA, null));
    }

    @Test
    public void setTask_targetNotFound_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.setTask(taskA, taskB));
    }

    @Test
    public void setTask_sameTask_success() {
        uniqueTaskList.add(taskA);
        uniqueTaskList.setTask(taskA, taskA);

        UniqueTaskList expected = new UniqueTaskList();
        expected.add(taskA);
        assertEquals(expected, uniqueTaskList);
    }

    @Test
    public void setTask_differentTask_success() {
        uniqueTaskList.add(taskA);
        uniqueTaskList.setTask(taskA, taskB);

        UniqueTaskList expected = new UniqueTaskList();
        expected.add(taskB);
        assertEquals(expected, uniqueTaskList);
    }

    @Test
    public void setTask_nonUniqueTask_throwsDuplicateTaskException() {
        uniqueTaskList.add(taskA);
        uniqueTaskList.add(taskB);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTask(taskA, taskB));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_taskNotFound_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.remove(taskA));
    }

    @Test
    public void remove_existingTask_success() {
        uniqueTaskList.add(taskA);
        uniqueTaskList.remove(taskA);
        assertEquals(new UniqueTaskList(), uniqueTaskList);
    }

    @Test
    public void setTasks_nullUniqueTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((UniqueTaskList) null));
    }

    @Test
    public void setTasks_uniqueTaskList_replacesOwnList() {
        uniqueTaskList.add(taskA);
        UniqueTaskList replacement = new UniqueTaskList();
        replacement.add(taskB);

        uniqueTaskList.setTasks(replacement);
        assertEquals(replacement, uniqueTaskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnList() {
        uniqueTaskList.add(taskA);
        uniqueTaskList.setTasks(Collections.singletonList(taskB));

        UniqueTaskList expected = new UniqueTaskList();
        expected.add(taskB);
        assertEquals(expected, uniqueTaskList);
    }

    @Test
    public void setTasks_listWithDuplicateTasks_throwsDuplicateTaskException() {
        List<Task> duplicates = Arrays.asList(taskA, new Task("Task A"));
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTasks(duplicates));
    }

    @Test
    public void asUnmodifiableObservableList_modify_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueTaskList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equalsAndHashCode() {
        UniqueTaskList first = new UniqueTaskList();
        UniqueTaskList second = new UniqueTaskList();

        first.add(taskA);
        first.add(taskB);
        second.add(taskB);
        second.add(taskA);

        assertTrue(first.equals(second));
        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals((Object) null));
        assertFalse(first.equals(1));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueTaskList.asUnmodifiableObservableList().toString(), uniqueTaskList.toString());
    }
}
