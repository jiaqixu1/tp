package seedu.taskforge.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.taskforge.commons.core.LogsCenter;
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

/**
 * Controller for an in-app help page.
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String HELP_MESSAGE =
            "TaskForge Help\n\n"
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
    public static final String USER_GUIDE_URL =
            "https://ay2526s2-cs2103t-w09-4.github.io/tp/UserGuide.html#quick-start";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private TextArea helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Sets focus on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Shows URL
     */
    @FXML
    private void handleOpenUserGuide() {
        try {
            Desktop.getDesktop().browse(
                    new URI(USER_GUIDE_URL)
            );
        } catch (Exception e) {
            logger.warning("Failed to open user guide: " + e.getMessage());
        }
    }
}
