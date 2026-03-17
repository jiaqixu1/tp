package seedu.taskforge.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.taskforge.testutil.Assert.assertThrows;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.ExitCommand;
import seedu.taskforge.logic.commands.HelpCommand;
import seedu.taskforge.logic.commands.person.AddCommand;
import seedu.taskforge.logic.commands.person.ClearCommand;
import seedu.taskforge.logic.commands.person.DeleteCommand;
import seedu.taskforge.logic.commands.person.EditCommand;
import seedu.taskforge.logic.commands.person.EditCommand.EditPersonDescriptor;
import seedu.taskforge.logic.commands.person.FindCommand;
import seedu.taskforge.logic.commands.person.ListCommand;
import seedu.taskforge.logic.commands.project.AddProjectCommand;
import seedu.taskforge.logic.commands.project.AddProjectCommand.AddProjectDescriptor;
import seedu.taskforge.logic.commands.project.AddProjectToProjectListCommand;
import seedu.taskforge.logic.commands.project.DeleteProjectCommand;
import seedu.taskforge.logic.commands.project.DeleteProjectCommand.DeleteProjectDescriptor;
import seedu.taskforge.logic.commands.project.DeleteProjectFromProjectListCommand;
import seedu.taskforge.logic.commands.project.ProjectCommand;
import seedu.taskforge.logic.commands.project.ViewAllProjectCommand;
import seedu.taskforge.logic.commands.task.AddTaskCommand;
import seedu.taskforge.logic.commands.task.AddTaskCommand.AddTaskDescriptor;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand.DeleteTaskDescriptor;
import seedu.taskforge.logic.parser.exceptions.ParseException;
import seedu.taskforge.model.person.NameContainsKeywordsPredicate;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.testutil.AddProjectDescriptorBuilder;
import seedu.taskforge.testutil.AddTaskDescriptorBuilder;
import seedu.taskforge.testutil.DeleteProjectDescriptorBuilder;
import seedu.taskforge.testutil.DeleteTaskDescriptorBuilder;
import seedu.taskforge.testutil.EditPersonDescriptorBuilder;
import seedu.taskforge.testutil.PersonBuilder;
import seedu.taskforge.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addProject() throws Exception {
        Person person = new PersonBuilder().build();
        AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder(person).build();
        AddProjectCommand command = (AddProjectCommand) parser.parseCommand(AddProjectCommand.COMMAND_WORD
                + " " + AddProjectCommand.SUBCOMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PersonUtil.getAddProjectDescriptorDetails(descriptor));
        assertEquals(new AddProjectCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_deleteProject() throws Exception {
        Index index = INDEX_FIRST_PROJECT;
        DeleteProjectDescriptor descriptor = new DeleteProjectDescriptorBuilder(index).build();
        DeleteProjectCommand command = (DeleteProjectCommand) parser.parseCommand(DeleteProjectCommand.COMMAND_WORD
                + " " + DeleteProjectCommand.SUBCOMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PersonUtil.getDeleteProjectDescriptorDetails(descriptor));
        assertEquals(new DeleteProjectCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_addProjectToProjectList() throws Exception {
        AddProjectToProjectListCommand command = (AddProjectToProjectListCommand) parser.parseCommand(
                AddProjectToProjectListCommand.COMMAND_WORD + " alpha");
        assertEquals(new AddProjectToProjectListCommand(new Project("alpha")), command);
    }

    @Test
    public void parseCommand_deleteProjectFromProjectList() throws Exception {
        DeleteProjectFromProjectListCommand command = (DeleteProjectFromProjectListCommand) parser.parseCommand(
                DeleteProjectFromProjectListCommand.COMMAND_WORD + " " + INDEX_FIRST_PROJECT.getOneBased());
        assertEquals(new DeleteProjectFromProjectListCommand(INDEX_FIRST_PROJECT), command);
    }

    @Test
    public void parseCommand_viewAllProject() throws Exception {
        assertTrue(parser.parseCommand(ViewAllProjectCommand.COMMAND_WORD) instanceof ViewAllProjectCommand);
    }

    @Test
    public void parseCommand_addTask() throws Exception {
        Person person = new PersonBuilder().build();
        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder(person).build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(AddTaskCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getAddTaskDescriptorDetails(descriptor));
        assertEquals(new AddTaskCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder(INDEX_FIRST_TASK).build();
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(DeleteTaskCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PersonUtil.getDeleteTaskDescriptorDetails(descriptor));
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_projectUnrecognisedSubcommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ProjectCommand.MESSAGE_USAGE), () -> parser.parseCommand(ProjectCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_projectUnknownSubcommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ProjectCommand.MESSAGE_USAGE), () -> parser.parseCommand(
                        ProjectCommand.COMMAND_WORD + " unknownCommand")
        );
    }
}
