package seedu.taskforge.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

import java.util.List;
import java.util.stream.IntStream;

public class ProjectCard extends UiPart<Region> {
    private static final String FXML = "ProjectListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Project project;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label projectTitle;
    @FXML
    private FlowPane tasks;

    /**
     * Creates a {@code ProjectCode} with the given {@code Project} and index to display.
     */
    public ProjectCard(Project project, int displayedIndex) {
        super(FXML);
        this.project = project;
        id.setText(displayedIndex + ". ");
        projectTitle.setText(project.toString());
        List<Task> taskList = project.getTasks();

        for(int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            Circle circle = new Circle(4);
            circle.getStyleClass().add("task-not-done");
            if (task.getStatus()) {
                circle.getStyleClass().add("task-done");
            }

            Label taskLabel = new Label((i + 1) + ". " + task.description);

            HBox taskContainer = new HBox(2);
            taskContainer.setAlignment(Pos.CENTER_LEFT);
            taskContainer.getChildren().addAll(circle, taskLabel);

            tasks.getChildren().add(taskContainer);
        }
    }
}
