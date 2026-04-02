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

    private static int invokeCalculateWorkload(List<PersonTask> personTasks, List<Project> projects) {
        return PersonCard.calculateWorkload(personTasks, projects);
    }

    private static Availability invokeCalculateAvailability(int workload) {
        return PersonCard.calculateAvailability(workload);
    }

}
