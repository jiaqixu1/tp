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
import seedu.taskforge.logic.commands.project.AssignProjectCommand;
import seedu.taskforge.logic.commands.project.AssignProjectCommand.AssignProjectDescriptor;
import seedu.taskforge.logic.commands.project.DeleteProjectCommand;
import seedu.taskforge.logic.commands.project.ListProjectCommand;
import seedu.taskforge.logic.commands.project.ProjectCommand;
import seedu.taskforge.logic.commands.project.UnassignProjectCommand;
import seedu.taskforge.logic.commands.project.UnassignProjectCommand.UnassignProjectDescriptor;
import seedu.taskforge.logic.commands.task.AddTaskCommand;
import seedu.taskforge.logic.commands.task.AddTaskCommand.AddTaskDescriptor;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand.DeleteTaskDescriptor;
import seedu.taskforge.logic.commands.task.TaskCommand;
import seedu.taskforge.logic.parser.exceptions.ParseException;
import seedu.taskforge.model.person.NameContainsKeywordsPredicate;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.testutil.AddTaskDescriptorBuilder;
import seedu.taskforge.testutil.AssignProjectDescriptorBuilder;
import seedu.taskforge.testutil.DeleteTaskDescriptorBuilder;
import seedu.taskforge.testutil.EditPersonDescriptorBuilder;
import seedu.taskforge.testutil.PersonBuilder;
import seedu.taskforge.testutil.PersonUtil;
import seedu.taskforge.testutil.UnassignProjectDescriptorBuilder;

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
    public void parseCommand_assignProject() throws Exception {
        Person person = new PersonBuilder().build();
        AssignProjectDescriptor descriptor = new AssignProjectDescriptorBuilder(person).withProjects("alpha").build();
        AssignProjectCommand command = (AssignProjectCommand) parser.parseCommand(AssignProjectCommand.COMMAND_WORD
                + " " + AssignProjectCommand.SUBCOMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PersonUtil.getAssignProjectDescriptorDetails(descriptor));
        assertEquals(new AssignProjectCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_unassignProject() throws Exception {
        Index index = INDEX_FIRST_PROJECT;
        UnassignProjectDescriptor descriptor = new UnassignProjectDescriptorBuilder(index).build();
        UnassignProjectCommand command = (UnassignProjectCommand) parser.parseCommand(
                UnassignProjectCommand.COMMAND_WORD + " " + UnassignProjectCommand.SUBCOMMAND_WORD
                        + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PersonUtil.getUnassignProjectDescriptorDetails(descriptor));
        assertEquals(new UnassignProjectCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_addProject() throws Exception {
        AddProjectCommand command = (AddProjectCommand) parser.parseCommand(
                ProjectCommand.COMMAND_WORD + " " + AddProjectCommand.SUBCOMMAND_WORD + " alpha");
        assertEquals(new AddProjectCommand(new Project("alpha")), command);
    }

    @Test
    public void parseCommand_deleteProject() throws Exception {
        DeleteProjectCommand command = (DeleteProjectCommand) parser.parseCommand(
                ProjectCommand.COMMAND_WORD + " " + DeleteProjectCommand.SUBCOMMAND_WORD + " "
                        + INDEX_FIRST_PROJECT.getOneBased());
        assertEquals(new DeleteProjectCommand(INDEX_FIRST_PROJECT), command);
    }

    @Test
    public void parseCommand_listProject() throws Exception {
        assertTrue(parser.parseCommand(ProjectCommand.COMMAND_WORD + " " + ListProjectCommand.SUBCOMMAND_WORD)
                instanceof ListProjectCommand);
    }

    @Test
    public void parseCommand_addTask() throws Exception {
        AddTaskDescriptor descriptor = new AddTaskDescriptorBuilder().withTasks("Write report").build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(AddTaskCommand.COMMAND_WORD
                + " " + AddTaskCommand.SUBCOMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PersonUtil.getAddTaskDescriptorDetails(descriptor));
        assertEquals(new AddTaskCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder(INDEX_FIRST_TASK).build();
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(DeleteTaskCommand.COMMAND_WORD
                + " " + DeleteTaskCommand.SUBCOMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
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

    @Test
    public void parseCommand_taskUnrecognisedSubcommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TaskCommand.MESSAGE_USAGE), () -> parser.parseCommand(TaskCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_taskUnknownSubcommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TaskCommand.MESSAGE_USAGE), () -> parser.parseCommand(
                TaskCommand.COMMAND_WORD + " unknownCommand")
        );
    }
}
