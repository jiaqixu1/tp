package seedu.taskforge.testutil;

import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_BETA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_Y;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_Z;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withProjects("alpha")
            .withTasks("refactor code").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withProjects("alpha", "beta")
            .withTasks("refactor code", "fix error in tp project").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withProjects("alpha")
            .withTasks("refactor code").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withProjects(VALID_PROJECT_BETA)
            .withTasks(VALID_TASK_FIX_ERROR).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA)
            .withTasks(VALID_TASK_REFACTOR, VALID_TASK_FIX_ERROR)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        Set<Project> uniqueProjects = new LinkedHashSet<>();
        for (Person person : getTypicalPersons()) {
            uniqueProjects.addAll(person.getProjects());
        }
        // Also add projects used in command tests.
        uniqueProjects.add(new Project(VALID_PROJECT_ALPHA));
        uniqueProjects.add(new Project(VALID_PROJECT_BETA));
        uniqueProjects.add(new Project(VALID_PROJECT_X));
        uniqueProjects.add(new Project(VALID_PROJECT_Y));
        uniqueProjects.add(new Project(VALID_PROJECT_Z));
        for (Project project : uniqueProjects) {
            ab.addProject(project);
        }
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
