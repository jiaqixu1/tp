package seedu.taskforge.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class JsonAdaptedProjectTest {

    @Test
    public void toModelType_validProjectDetails_returnsProject() throws Exception {
        List<JsonAdaptedTask> adaptedTasks = Arrays.asList(
                new JsonAdaptedTask("Refactor", null),
                new JsonAdaptedTask("Fix bug", null));
        JsonAdaptedProject adaptedProject = new JsonAdaptedProject("alpha", adaptedTasks);

        Project modelProject = adaptedProject.toModelType();

        assertEquals(new Project("alpha", Arrays.asList(new Task("Refactor"), new Task("Fix bug"))), modelProject);
        assertEquals(2, modelProject.getTasks().size());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedProject adaptedProject = new JsonAdaptedProject("#alpha", Collections.emptyList());

        assertThrows(IllegalValueException.class, Project.MESSAGE_CONSTRAINTS, adaptedProject::toModelType);
    }

    @Test
    public void constructor_nullTasks_createsEmptyTaskList() {
        JsonAdaptedProject adaptedProject = new JsonAdaptedProject("alpha", null);

        assertEquals("alpha", adaptedProject.getTitle());
        assertTrue(adaptedProject.getTasks().isEmpty());
    }

    @Test
    public void getTasks_returnsDefensiveCopy() {
        JsonAdaptedProject adaptedProject = new JsonAdaptedProject("alpha",
                Collections.singletonList(new JsonAdaptedTask("Refactor", null)));

        List<JsonAdaptedTask> tasks = adaptedProject.getTasks();
        tasks.clear();

        assertEquals(1, adaptedProject.getTasks().size());
    }
}
