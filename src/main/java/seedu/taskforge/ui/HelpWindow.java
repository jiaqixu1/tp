package seedu.taskforge.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.taskforge.commons.core.LogsCenter;

/**
 * Controller for an in-app help page.
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String HELP_MESSAGE =
            "TaskForge Help\n\n"
                    + "TaskForge helps you manage people, projects, and tasks.\n\n"
                    + "Available commands:\n"
                    + "1. Add Person: add -n NAME -p PHONE_NUMBER -e EMAIL [-l PROJECT_NAME] [-d TASK_NAME]\n"
                    + "   Example: add -n James Ho -p 22224444 -e jamesho@example.com -l ProjectX -d TaskY\n\n"
                    + "2. Add Project: project add PROJECT_NAME\n"
                    + "   Example: project add WebApp\n\n"
                    + "3. Add Task: task add PROJECT_INDEX -n TASK_NAME\n"
                    + "   Example: task add 1 -n Write report\n\n"
                    + "4. Delete Task: task delete PROJECT_INDEX -i TASK_INDEX\n"
                    + "   Example: task delete 1 -i 2\n\n"
                    + "5. List Tasks by Project: task list -n PROJECT_NAME\n"
                    + "   Example: task list -n Alpha\n\n"
                    + "6. Assign Task: task assign INDEX -n TASK_NAME\n"
                    + "   Example: task assign 1 -n Draft proposal\n\n"
                    + "7. Assign Project: project assign INDEX -n PROJECT_NAME\n"
                    + "   Example: project assign 1 -n WebApp\n\n"
                    + "8. Clear: clear\n\n"
                    + "9. Delete Person: delete INDEX\n"
                    + "   Example: delete 3\n\n"
                    + "10. Delete Project: project delete INDEX\n"
                    + "    Example: project delete 1\n\n"
                    + "11. Find Project: project find KEYWORD [MORE_KEYWORDS]\n"
                    + "    Example: project find Alpha Web\n\n"
                    + "12. View Tasks: task view INDEX\n"
                    + "    Example: task view 1\n\n"
                    + "13. Edit Person: edit INDEX [-n NAME] [-p PHONE_NUMBER] [-e EMAIL] [-l PROJECT_NAME] [-d TASK_NAME]\n"
                    + "    Example: edit 1 -n James Ho -p 22224444 -e jamesho@example.com -l ProjectX -d TaskY\n\n"
                    + "14. Find Person: find KEYWORD [MORE_KEYWORDS]\n"
                    + "    Example: find James Jake\n\n"
                    + "15. List Person: list\n\n"
                    + "16. Unassign Task: task unassign INDEX -i TASK_INDEX\n"
                    + "    Example: task unassign 2 -i 1\n\n"
                    + "17. Unassign Project: project unassign INDEX -i PROJECT_INDEX\n"
                    + "    Example: project unassign 2 -i 1\n\n"
                    + "18. View Projects: project list\n\n"
                    + "19. Help: help";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Label helpMessage;

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
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
