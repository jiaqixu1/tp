package seedu.taskforge.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.ReadOnlyAddressBook;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.testutil.PersonBuilder;

public class PersonCardTest {

    private static final String PROJECT_TITLE = "alpha";

    @BeforeAll
    public static void startJavaFx() {
        try {
            Platform.startup(() -> { });
        } catch (IllegalStateException e) {
            // JavaFX already started.
        }
    }

    @Test
    public void constructor_validPerson_rendersPersonDetails() {
        ReadOnlyAddressBook addressBook = addressBookWithProjectTasks(5);
        Person person = new PersonBuilder()
                .withName("Ada Lovelace")
                .withPhone("81234567")
                .withEmail("ada@example.com")
                .withProjects(PROJECT_TITLE)
                .withTasks("task 1", "task 2")
                .build();

        runOnFxThread(() -> {
            PersonCard card = new PersonCard(person, 1, addressBook);
            Scene scene = new Scene(card.getRoot());

            assertEquals("1. ", label(scene, "id").getText());
            assertEquals("Ada Lovelace", label(scene, "name").getText());
            assertEquals("81234567", label(scene, "phone").getText());
            assertEquals("ada@example.com", label(scene, "email").getText());
            assertEquals("Available.  Workload:  2", label(scene, "availability").getText());
            assertEquals(1, flowPane(scene, "projects").getChildren().size());
            assertEquals(2, flowPane(scene, "tasks").getChildren().size());
            assertEquals("1. Alpha", ((Label) flowPane(scene, "projects").getChildren().get(0)).getText());
            assertEquals("1. [ ] task 1", ((Label) flowPane(scene, "tasks").getChildren().get(0)).getText());
            assertEquals("2. [ ] task 2", ((Label) flowPane(scene, "tasks").getChildren().get(1)).getText());
            return null;
        });
    }

    @Test
    public void constructor_doneTask_rendersDoneStatusAndZeroWorkload() {
        AddressBook addressBook = addressBookWithProjectTasks(1);
        addressBook.getProjectList().get(0).getUniqueTaskList().iterator().next().setDone();
        Person person = new PersonBuilder()
                .withName("Done Task Person")
                .withPhone("82345678")
                .withEmail("done@example.com")
                .withProjects(PROJECT_TITLE)
                .withTasks("task 1")
                .build();

        runOnFxThread(() -> {
            PersonCard card = new PersonCard(person, 1, addressBook);
            Scene scene = new Scene(card.getRoot());

            assertEquals("Free.  Workload:  0", label(scene, "availability").getText());
            assertEquals("1. [X] task 1", ((Label) flowPane(scene, "tasks").getChildren().get(0)).getText());
            return null;
        });
    }

    @Test
    public void constructor_invalidReferences_renderFallbackLabels() {
        ReadOnlyAddressBook addressBook = addressBookWithProjectTasks(1);
        Person person = new Person(
                new seedu.taskforge.model.person.Name("Fallback Person"),
                new seedu.taskforge.model.person.Phone("83456789"),
                new seedu.taskforge.model.person.Email("fallback@example.com"),
                List.of(new PersonProject(9)),
                Arrays.asList(new PersonTask(9, 9), new PersonTask(0, 9)));

        runOnFxThread(() -> {
            PersonCard card = new PersonCard(person, 1, addressBook);
            Scene scene = new Scene(card.getRoot());

            assertEquals("1. ", ((Label) flowPane(scene, "projects").getChildren().get(0)).getText());
            assertEquals("1. [ ] [invalid-task-reference]", ((Label) flowPane(scene, "tasks")
                    .getChildren().get(0)).getText());
            assertEquals("2. [ ] [invalid-task-reference]", ((Label) flowPane(scene, "tasks")
                    .getChildren().get(1)).getText());
            assertEquals("Free.  Workload:  0", label(scene, "availability").getText());
            return null;
        });
    }

    @Test
    public void constructor_workloadThresholds_renderCorrectAvailability() {
        ReadOnlyAddressBook addressBook = addressBookWithProjectTasks(5);

        assertEquals("Free.  Workload:  0", cardAvailability(addressBook, 0));
        assertEquals("Available.  Workload:  2", cardAvailability(addressBook, 2));
        assertEquals("Busy.  Workload:  4", cardAvailability(addressBook, 4));
        assertEquals("Overloaded.  Workload:  5", cardAvailability(addressBook, 5));
    }

    private static String cardAvailability(ReadOnlyAddressBook addressBook, int taskCount) {
        PersonBuilder builder = new PersonBuilder()
                .withName("Workload Person " + taskCount)
                .withPhone("8000000" + taskCount)
                .withEmail("workload" + taskCount + "@example.com")
                .withProjects(PROJECT_TITLE);

        if (taskCount > 0) {
            String[] taskNames = new String[taskCount];
            for (int i = 0; i < taskCount; i++) {
                taskNames[i] = "task " + (i + 1);
            }
            builder.withTasks(taskNames);
        }

        Person person = builder.build();
        return runOnFxThread(() -> {
            PersonCard card = new PersonCard(person, 1, addressBook);
            Scene scene = new Scene(card.getRoot());
            return label(scene, "availability").getText();
        });
    }

    private static AddressBook addressBookWithProjectTasks(int taskCount) {
        AddressBook addressBook = new AddressBook();
        addressBook.addProject(new Project(PROJECT_TITLE, sampleTasks(taskCount)));
        return addressBook;
    }

    private static List<Task> sampleTasks(int taskCount) {
        Task[] tasks = new Task[taskCount];
        for (int i = 0; i < taskCount; i++) {
            tasks[i] = new Task("task " + (i + 1));
        }
        return Arrays.asList(tasks);
    }

    private static Label label(Scene scene, String fxId) {
        return (Label) scene.lookup("#" + fxId);
    }

    private static javafx.scene.layout.FlowPane flowPane(Scene scene, String fxId) {
        return (javafx.scene.layout.FlowPane) scene.lookup("#" + fxId);
    }

    private static <T> T runOnFxThread(java.util.function.Supplier<T> supplier) {
        AtomicReference<T> result = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            result.set(supplier.get());
            latch.countDown();
        });

        try {
            assertTrue(latch.await(5, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }

        return result.get();
    }
}
