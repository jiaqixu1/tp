package seedu.taskforge.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.HelpCommand;
import seedu.taskforge.logic.commands.person.AddCommand;
import seedu.taskforge.logic.commands.person.ClearCommand;
import seedu.taskforge.logic.commands.person.DeleteCommand;
import seedu.taskforge.logic.commands.person.EditCommand;
import seedu.taskforge.logic.commands.person.FindCommand;
import seedu.taskforge.logic.commands.person.ListCommand;
import seedu.taskforge.logic.commands.project.AddProjectCommand;
import seedu.taskforge.logic.commands.project.AssignProjectCommand;
import seedu.taskforge.logic.commands.project.DeleteProjectCommand;
import seedu.taskforge.logic.commands.project.FindProjectCommand;
import seedu.taskforge.logic.commands.project.ListProjectCommand;
import seedu.taskforge.logic.commands.project.UnassignProjectCommand;
import seedu.taskforge.logic.commands.task.AddTaskCommand;
import seedu.taskforge.logic.commands.task.AssignTaskCommand;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand;
import seedu.taskforge.logic.commands.task.EditTaskCommand;
import seedu.taskforge.logic.commands.task.FindTaskCommand;
import seedu.taskforge.logic.commands.task.ListTaskCommand;
import seedu.taskforge.logic.commands.task.MarkTaskCommand;
import seedu.taskforge.logic.commands.task.UnassignTaskCommand;
import seedu.taskforge.logic.commands.task.UnmarkTaskCommand;

public class HelpWindowTest {

    @Test
    public void helpMessage_containsAllCommandUsages() {
        String expected = "TaskForge Help\n\n"
                + "TaskForge helps you manage people, projects, and tasks.\n\n"
                + "PERSON COMMANDS\n"
                + "------------------------------\n"
                + AddCommand.MESSAGE_USAGE + "\n\n"
                + EditCommand.MESSAGE_USAGE + "\n\n"
                + DeleteCommand.MESSAGE_USAGE + "\n\n"
                + FindCommand.MESSAGE_USAGE + "\n\n"
                + ListCommand.MESSAGE_USAGE + "\n\n"
                + "PROJECT COMMANDS\n"
                + "------------------------------\n"
                + AddProjectCommand.MESSAGE_USAGE + "\n\n"
                + DeleteProjectCommand.MESSAGE_USAGE + "\n\n"
                + FindProjectCommand.MESSAGE_USAGE + "\n\n"
                + ListProjectCommand.MESSAGE_USAGE + "\n\n"
                + AssignProjectCommand.MESSAGE_USAGE + "\n\n"
                + UnassignProjectCommand.MESSAGE_USAGE + "\n\n"
                + "TASK COMMANDS\n"
                + "------------------------------\n"
                + AddTaskCommand.MESSAGE_USAGE + "\n\n"
                + DeleteTaskCommand.MESSAGE_USAGE + "\n\n"
                + EditTaskCommand.MESSAGE_USAGE + "\n\n"
                + ListTaskCommand.MESSAGE_USAGE + "\n\n"
                + FindTaskCommand.MESSAGE_USAGE + "\n\n"
                + AssignTaskCommand.MESSAGE_USAGE + "\n\n"
                + UnassignTaskCommand.MESSAGE_USAGE + "\n\n"
                + MarkTaskCommand.MESSAGE_USAGE + "\n\n"
                + UnmarkTaskCommand.MESSAGE_USAGE + "\n\n"
                + "GENERAL COMMANDS\n"
                + "------------------------------\n"
                + ClearCommand.MESSAGE_USAGE + "\n\n"
                + HelpCommand.MESSAGE_USAGE;

        assertEquals(expected, HelpWindow.HELP_MESSAGE);
    }

    @Test
    public void helpMessage_containsSectionHeaders() {
        assertTrue(HelpWindow.HELP_MESSAGE.contains("PERSON COMMANDS"));
        assertTrue(HelpWindow.HELP_MESSAGE.contains("PROJECT COMMANDS"));
        assertTrue(HelpWindow.HELP_MESSAGE.contains("TASK COMMANDS"));
        assertTrue(HelpWindow.HELP_MESSAGE.contains("GENERAL COMMANDS"));
    }

    @Test
    public void userGuideUrl_correct() {
        assertEquals(
                "https://ay2526s2-cs2103t-w09-4.github.io/tp/UserGuide.html#quick-start",
                HelpWindow.USER_GUIDE_URL
        );
    }

    @Test
    public void helpMessage_containsCommandUsage() {
        assertTrue(HelpWindow.HELP_MESSAGE.contains("add"));
        assertTrue(HelpWindow.HELP_MESSAGE.contains("project"));
        assertTrue(HelpWindow.HELP_MESSAGE.contains("task"));
    }
}
