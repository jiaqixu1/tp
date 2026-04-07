package seedu.taskforge.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                new Project("alpha", List.of(new Task("task 1"), new Task("task 2"))),
                new Project("beta", List.of(new Task("task 3"))));

        List<PersonTask> tasks = new ArrayList<>();
        tasks.add(new PersonTask(0, 0));
        tasks.add(new PersonTask(0, 1));
        tasks.add(new PersonTask(1, 0));
        tasks.add(new PersonTask(99, 0));
        tasks.add(new PersonTask(0, 99));

        assertEquals(3, invokeCalculateWorkload(tasks, projects));
    }

    @Test
    public void calculateWorkload_doneTask_notCounted() {
        Task doneTask = new Task("task 1");
        doneTask.setDone();

        List<Project> projects = List.of(new Project("alpha", List.of(doneTask)));
        List<PersonTask> tasks = List.of(new PersonTask(0, 0));

        assertEquals(0, invokeCalculateWorkload(tasks, projects));
    }

    @Test
    public void calculateAvailability_thresholds_returnExpectedAvailability() {
        assertEquals(Availability.FREE, invokeCalculateAvailability(0));
        assertEquals(Availability.AVAILABLE, invokeCalculateAvailability(1));
        assertEquals(Availability.AVAILABLE, invokeCalculateAvailability(2));
        assertEquals(Availability.BUSY, invokeCalculateAvailability(3));
        assertEquals(Availability.BUSY, invokeCalculateAvailability(4));
        assertEquals(Availability.OVERLOADED, invokeCalculateAvailability(5));
    }

    @Test
    public void calculateAvailability_negativeWorkload_returnsAvailable() {
        assertEquals(Availability.AVAILABLE, invokeCalculateAvailability(-1));
    }

    @Test
    public void formatAvailabilityText_returnsExpectedString() {
        assertEquals("Available.  Workload:  2", PersonCard.formatAvailabilityText(2));
    }

    @Test
    public void resolveProjectTitle_validAndInvalidIndex() {
        List<Project> projects = List.of(new Project("alpha"));

        assertEquals("Alpha", PersonCard.resolveProjectTitle(new seedu.taskforge.model.person.PersonProject(0),
                projects));
        assertEquals("", PersonCard.resolveProjectTitle(new seedu.taskforge.model.person.PersonProject(1), projects));
    }

    @Test
    public void buildProjectDisplayLabels_formatsOrdinalAndFallbackTitle() {
        List<Project> projects = List.of(new Project("alpha"));
        List<seedu.taskforge.model.person.PersonProject> personProjects = List.of(
                new seedu.taskforge.model.person.PersonProject(0),
                new seedu.taskforge.model.person.PersonProject(1));

        assertEquals(List.of("1. Alpha", "2. "), PersonCard.buildProjectDisplayLabels(personProjects, projects));
    }

    @Test
    public void resolveTaskDisplay_doneAndInvalidTaskReference() {
        Task doneTask = new Task("task 1");
        doneTask.setDone();
        List<Project> projects = List.of(new Project("alpha", List.of(doneTask)));

        assertEquals("[X] task 1", PersonCard.resolveTaskDisplay(new PersonTask(0, 0), projects));
        assertEquals("[ ] [invalid-task-reference]", PersonCard.resolveTaskDisplay(new PersonTask(0, 2), projects));
        assertEquals("[ ] [invalid-task-reference]", PersonCard.resolveTaskDisplay(new PersonTask(2, 0), projects));
    }

    @Test
    public void buildTaskDisplayLabels_formatsOrdinalAndStatuses() {
        Task doneTask = new Task("task 1");
        doneTask.setDone();
        Task todoTask = new Task("task 2");
        List<Project> projects = List.of(new Project("alpha", List.of(doneTask, todoTask)));
        List<PersonTask> personTasks = List.of(new PersonTask(0, 0), new PersonTask(0, 1), new PersonTask(0, 9));

        assertEquals(List.of("1. [X] task 1", "2. [ ] task 2", "3. [ ] [invalid-task-reference]"),
                PersonCard.buildTaskDisplayLabels(personTasks, projects));
    }

    private static int invokeCalculateWorkload(List<PersonTask> personTasks, List<Project> projects) {
        return PersonCard.calculateWorkload(personTasks, projects);
    }

    private static Availability invokeCalculateAvailability(int workload) {
        return PersonCard.calculateAvailability(workload);
    }

}
