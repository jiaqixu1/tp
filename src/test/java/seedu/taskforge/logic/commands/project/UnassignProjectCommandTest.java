package seedu.taskforge.logic.commands.project;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.testutil.PersonBuilder;
import seedu.taskforge.testutil.UnassignProjectDescriptorBuilder;

public class UnassignProjectCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unassignOneProjectUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects().build();

        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.formatPersonSummary(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unassignOneProjectFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects().build();

        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.formatPersonSummary(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unassignOneProjectOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(unassignProjectCommand, model,
                UnassignProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unassignOneProjectOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(unassignProjectCommand, model,
                UnassignProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unassignMultipleProjectsOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(unassignProjectCommand, model,
                UnassignProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unassignMultipleProjectsOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(unassignProjectCommand, model,
                UnassignProjectCommand.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unassignMultipleProjectsUnfilteredList_success() {
        Index indexSecondPerson = Index.fromOneBased(2);
        Person firstPerson = model.getFilteredPersonList().get(indexSecondPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects().build();

        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(indexSecondPerson, descriptor);

        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.formatPersonSummary(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unassignMultipleProjectsFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects().build();

        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1", "2").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UnassignProjectCommand.MESSAGE_UNASSIGN_PROJECT_SUCCESS,
                Messages.formatPersonSummary(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(unassignProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noProjectSpecifiedUnfilteredList_errorThrown() {
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(
                INDEX_FIRST_PERSON, new UnassignProjectCommand.UnassignProjectDescriptor()
        );
        assertCommandFailure(unassignProjectCommand, model, UnassignProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_noProjectSpecifiedFilteredList_errorThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(
                INDEX_FIRST_PERSON, new UnassignProjectCommand.UnassignProjectDescriptor()
        );
        assertCommandFailure(unassignProjectCommand, model, UnassignProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnassignProjectCommand.UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder()
                .withProjects("1").build();
        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(unassignProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Unassigns project to a person of a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnassignProjectCommand unassignProjectCommand = new UnassignProjectCommand(outOfBoundIndex,
                new UnassignProjectDescriptorBuilder().withProjects("1").build());

        assertCommandFailure(unassignProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
