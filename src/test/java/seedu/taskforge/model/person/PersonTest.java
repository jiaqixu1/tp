package seedu.taskforge.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_BETA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;
import static seedu.taskforge.testutil.Assert.assertThrows;
import static seedu.taskforge.testutil.TypicalPersons.ALICE;
import static seedu.taskforge.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.taskforge.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getProjects().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> person.getTasks().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withProjects(VALID_PROJECT_ALPHA)
                .withTasks(VALID_TASK_REFACTOR).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same phone, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withProjects(VALID_PROJECT_ALPHA)
                .withTasks(VALID_TASK_REFACTOR).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same email, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withProjects(VALID_PROJECT_ALPHA)
                .withTasks(VALID_TASK_REFACTOR).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, phone, email -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name and email differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB)
                .withName(VALID_NAME_BOB.toLowerCase())
                .withEmail(VALID_EMAIL_BOB.toUpperCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));


        // different projects -> returns false
        editedAlice = new PersonBuilder(ALICE).withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tasks -> returns false
        editedAlice = new PersonBuilder(ALICE).withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR).build();
        assertFalse(ALICE.equals(editedAlice));


    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", projects="
                + ALICE.getProjects() + ", tasks=" + ALICE.getTasks() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void getWorkloadTest() {
        // No task = no workload
        Person person = new PersonBuilder().build();
        assertEquals(0, person.getWorkload());

        // 2 tasks = 2 workloads
        Person personWithTask = new PersonBuilder().withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR).build();
        assertEquals(2, personWithTask.getWorkload());

        // 2 tasks with 1 done = 1 workload
        Person personDoneTask = new PersonBuilder().withTasks(VALID_TASK_REFACTOR)
                .appendDoneTasks(VALID_TASK_FIX_ERROR).build();
        assertEquals(2, personDoneTask.getWorkload());
    }

    @Test
    public void getAvailabilityTest() {
        PersonBuilder personBuilder = new PersonBuilder();
        int taskCount = 1;

        // when total tasks = 0
        Person freePerson = personBuilder.build();
        assertEquals(Availability.FREE, freePerson.getAvailability());
        assertEquals("Free", freePerson.getAvailability().toString());

        // when total tasks = Person.AVAILABLE
        while (taskCount <= Person.AVAILABLE) {
            personBuilder.appendTasks(VALID_TASK_REFACTOR + taskCount);
            taskCount++;
        }
        Person availablePerson = personBuilder.build();
        assertEquals(Availability.AVAILABLE, availablePerson.getAvailability());
        assertEquals("Available", availablePerson.getAvailability().toString());

        // when total tasks = Person.BUSY
        while (taskCount <= Person.BUSY) {
            personBuilder.appendTasks(VALID_TASK_REFACTOR + taskCount);
            taskCount++;
        }
        Person busyPerson = personBuilder.build();
        assertEquals(Availability.BUSY, busyPerson.getAvailability());
        assertEquals("Busy", busyPerson.getAvailability().toString());


        // when total tasks > Person.BUSY
        personBuilder.appendTasks(VALID_TASK_REFACTOR + taskCount + 1);
        Person overloadedPerson = personBuilder.build();
        assertEquals(Availability.OVERLOADED, overloadedPerson.getAvailability());
        assertEquals("Overloaded", overloadedPerson.getAvailability().toString());
    }
}
