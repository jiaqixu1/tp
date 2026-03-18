package seedu.taskforge.logic.commands.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_BETA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_Y;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_Z;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.project.AssignProjectCommand.AssignProjectDescriptor;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.util.SampleDataUtil;
import seedu.taskforge.testutil.AssignProjectDescriptorBuilder;
import seedu.taskforge.testutil.PersonBuilder;

public class AssignProjectCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_assignOneProjectUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA).build();

        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA).build();
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AssignProjectCommand.MESSAGE_ASSIGN_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_assignOneProjectFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA).build();

        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA).build();
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AssignProjectCommand.MESSAGE_ASSIGN_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_assignOneProjectDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_ALPHA).build();
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(assignProjectCommand, model, AssignProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_assignOneProjectDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_ALPHA).build();
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(assignProjectCommand, model, AssignProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_assignMultipleProjectsDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA, VALID_PROJECT_X, VALID_PROJECT_Y,
                VALID_PROJECT_Z, VALID_PROJECT_ALPHA).build();
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(assignProjectCommand, model, AssignProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_assignMultipleProjectsDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA, VALID_PROJECT_X, VALID_PROJECT_Y,
                VALID_PROJECT_Z, VALID_PROJECT_ALPHA).build();
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(assignProjectCommand, model, AssignProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_assignMultipleProjectsUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA,
                VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z).build();

        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA, VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z).build();
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AssignProjectCommand.MESSAGE_ASSIGN_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_assignMultipleProjectsFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA,
                VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z).build();

        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA, VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z).build();
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AssignProjectCommand.MESSAGE_ASSIGN_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(assignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noProjectSpecifiedUnfilteredList_errorThrown() {
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(
                INDEX_FIRST_PERSON, new AssignProjectDescriptor()
        );
        assertCommandFailure(assignProjectCommand, model, AssignProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_noProjectSpecifiedFilteredList_errorThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(
                INDEX_FIRST_PERSON, new AssignProjectDescriptor()
        );
        assertCommandFailure(assignProjectCommand, model, AssignProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_X).build();
        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(assignProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Assigns project to a person of a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AssignProjectCommand assignProjectCommand = new AssignProjectCommand(outOfBoundIndex,
                new AssignProjectDescriptorBuilder().withProjects(VALID_PROJECT_X).build());

        assertCommandFailure(assignProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AssignProjectDescriptor descriptor = new AssignProjectDescriptor();
        AssignProjectCommand command = new AssignProjectCommand(INDEX_FIRST_PROJECT, descriptor);

        // same values
        AssignProjectCommand commandCopy = new AssignProjectCommand(INDEX_FIRST_PROJECT, descriptor);
        assertEquals(command, commandCopy);

        // same object
        assertEquals(command, command);

        // null -> false
        assertNotEquals(command, null);

        // different type -> false
        assertNotEquals(command, 5);

        // different index -> false
        AssignProjectCommand secondProjectCommand = new AssignProjectCommand(INDEX_SECOND_PROJECT, descriptor);
        assertNotEquals(command, secondProjectCommand);

        // different descriptor -> false
        AssignProjectDescriptor otherDescriptor = new AssignProjectDescriptor();
        otherDescriptor.setProjects(SampleDataUtil.getProjectList(VALID_PROJECT_ALPHA));
        assertNotEquals(command, new AssignProjectCommand(INDEX_FIRST_PROJECT, otherDescriptor));
    }

    @Test
    public void assignProjectDescriptor_equals() {
        AssignProjectDescriptor descriptor = new AssignProjectDescriptor();
        descriptor.setProjects(SampleDataUtil.getProjectList(VALID_PROJECT_ALPHA));

        // same values
        AssignProjectDescriptor descriptorCopy = new AssignProjectDescriptor();
        descriptorCopy.setProjects(SampleDataUtil.getProjectList(VALID_PROJECT_ALPHA));
        assertEquals(descriptor, descriptorCopy);

        // same object
        assertEquals(descriptor, descriptor);

        // null -> false
        assertNotEquals(descriptor, null);

        // different type -> false
        assertNotEquals(descriptor, 5);

        // different list -> false
        AssignProjectDescriptor otherDescriptor = new AssignProjectDescriptor();
        descriptor.setProjects(SampleDataUtil.getProjectList(VALID_PROJECT_BETA));
        assertNotEquals(descriptor, otherDescriptor);
    }
}
