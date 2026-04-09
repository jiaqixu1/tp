package seedu.taskforge.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_BETA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskforge.model.person.Availability;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class PersonCardTest {

    @Test
    public void calculateWorkload_validAndInvalidTaskReferences_countsOnlyUnfinishedValidTasks() {
        List<Project> projects = List.of(
                new Project(VALID_PROJECT_ALPHA,
                        List.of(new Task(VALID_TASK_REFACTOR), new Task(VALID_TASK_FIX_ERROR))),
                new Project(VALID_PROJECT_BETA,
                        List.of(new Task(VALID_TASK_IMPLEMENT_X))));

        List<PersonTask> tasks = new ArrayList<>();
        tasks.add(new PersonTask(0, 0));
        tasks.add(new PersonTask(0, 1));
        tasks.add(new PersonTask(1, 0));
        tasks.add(new PersonTask(99, 0));
        tasks.add(new PersonTask(0, 99));

        assertEquals(3, PersonCard.calculateWorkload(tasks, projects));
    }

    @Test
    public void calculateWorkload_doneTask_notCounted() {
        Task doneTask = new Task(VALID_TASK_REFACTOR);
        doneTask.setDone();

        List<Project> projects = List.of(new Project(VALID_PROJECT_ALPHA, List.of(doneTask)));
        List<PersonTask> tasks = List.of(new PersonTask(0, 0));

        assertEquals(0, PersonCard.calculateWorkload(tasks, projects));
    }

    @Test
    public void calculateAvailability_thresholds_returnExpectedAvailability() {
        assertEquals(Availability.FREE, PersonCard.calculateAvailability(0));
        assertEquals(Availability.AVAILABLE, PersonCard.calculateAvailability(1));
        assertEquals(Availability.AVAILABLE, PersonCard.calculateAvailability(2));
        assertEquals(Availability.BUSY, PersonCard.calculateAvailability(3));
        assertEquals(Availability.BUSY, PersonCard.calculateAvailability(4));
        assertEquals(Availability.OVERLOADED, PersonCard.calculateAvailability(5));
    }

    @Test
    public void formatAvailabilityText_returnsExpectedString() {
        assertEquals("Available.  Workload:  2", PersonCard.formatAvailabilityText(2));
    }

    @Test
    public void resolveTask_returnsExpectedTask() {
        Task doneTask = new Task(VALID_TASK_REFACTOR);
        doneTask.setDone();
        Task notDoneTask = new Task(VALID_TASK_FIX_ERROR);
        List<Project> projects = List.of(new Project(VALID_PROJECT_ALPHA, List.of(doneTask, notDoneTask)));

        assertEquals(doneTask, PersonCard.resolveTask(new PersonTask(0, 0), projects));
        assertEquals(notDoneTask, PersonCard.resolveTask(new PersonTask(0, 1), projects));
    }
}
