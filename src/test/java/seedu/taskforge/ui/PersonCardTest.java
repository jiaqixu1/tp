package seedu.taskforge.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

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

    static {
        bootstrapJavaFx();
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

        PersonCard card = new PersonCard(person, 1, addressBook);

        assertEquals("1. ", label(card, "id").getText());
        assertEquals("Ada Lovelace", label(card, "name").getText());
        assertEquals("81234567", label(card, "phone").getText());
        assertEquals("ada@example.com", label(card, "email").getText());
        assertEquals("Available.  Workload:  2", label(card, "availability").getText());
        assertEquals(1, flowPane(card, "projects").getChildren().size());
        assertEquals(2, flowPane(card, "tasks").getChildren().size());
        assertEquals("1. Alpha", ((Label) flowPane(card, "projects").getChildren().get(0)).getText());
        assertEquals("1. [ ] task 1", ((Label) flowPane(card, "tasks").getChildren().get(0)).getText());
        assertEquals("2. [ ] task 2", ((Label) flowPane(card, "tasks").getChildren().get(1)).getText());
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

        PersonCard card = new PersonCard(person, 1, addressBook);

        assertEquals("Free.  Workload:  0", label(card, "availability").getText());
        assertEquals("1. [X] task 1", ((Label) flowPane(card, "tasks").getChildren().get(0)).getText());
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

        PersonCard card = new PersonCard(person, 1, addressBook);

        assertEquals("1. ", ((Label) flowPane(card, "projects").getChildren().get(0)).getText());
        assertEquals("1. [ ] [invalid-task-reference]", ((Label) flowPane(card, "tasks")
            .getChildren().get(0)).getText());
        assertEquals("2. [ ] [invalid-task-reference]", ((Label) flowPane(card, "tasks")
            .getChildren().get(1)).getText());
        assertEquals("Free.  Workload:  0", label(card, "availability").getText());
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
        PersonCard card = new PersonCard(person, 1, addressBook);
        return label(card, "availability").getText();
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

    private static Label label(PersonCard card, String fieldName) {
        return getField(card, fieldName, Label.class);
    }

    private static javafx.scene.layout.FlowPane flowPane(PersonCard card, String fieldName) {
        return getField(card, fieldName, javafx.scene.layout.FlowPane.class);
    }

    private static <T> T getField(PersonCard card, String fieldName, Class<T> fieldType) {
        try {
            Field field = PersonCard.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return fieldType.cast(field.get(card));
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    private static void bootstrapJavaFx() {
        try {
            Class<?> platformImplClass = Class.forName("com.sun.javafx.application.PlatformImpl");
            Method startupMethod = platformImplClass.getDeclaredMethod("startup", Runnable.class);
            startupMethod.setAccessible(true);
            startupMethod.invoke(null, (Runnable) () -> { });
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalStateException)) {
                throw new AssertionError(cause);
            }
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }
}
