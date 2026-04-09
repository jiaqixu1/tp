package seedu.taskforge.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import seedu.taskforge.model.ReadOnlyTaskForge;
import seedu.taskforge.model.person.Availability;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final double PROJECT_BOX_WIDTH = 150;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label availability;
    @FXML
    private Circle availabilityIndicator;
    @FXML
    private FlowPane projects;

    /**
     * Creates a {@code PersonCard} with the given {@code Person}, index, and {@code ReadOnlyTaskForge} to display.
     */
    public PersonCard(Person person, int displayedIndex, ReadOnlyTaskForge taskForge) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);

        List<PersonProject> personProjectList = person.getProjects();
        List<PersonTask> personTaskList = person.getTasks();
        List<Project> globalProjectList = new ArrayList<>(taskForge.getProjectList());

        int workload = calculateWorkload(personTaskList, globalProjectList);
        String availabilityString = calculateAvailability(workload).toString();
        availability.setText(formatAvailabilityText(workload));
        availabilityIndicator.getStyleClass().add(availabilityString);

        populateProjectBoxes(personProjectList, personTaskList, globalProjectList);
    }

    static String formatAvailabilityText(int workload) {
        return calculateAvailability(workload) + ".  Workload:  " + workload;
    }

    private void populateProjectBoxes(List<PersonProject> personProjects, List<PersonTask> personTasks,
                                      List<Project> globalProjects) {
        List<Integer> projectIndexes = collectProjectIndexes(personProjects);
        for (int i = 0; i < projectIndexes.size(); i++) {
            projects.getChildren().add(createProjectBox(i + 1, projectIndexes.get(i), personTasks, globalProjects));
        }
    }

    private List<Integer> collectProjectIndexes(List<PersonProject> personProjects) {
        List<Integer> orderedProjectIndexes = new ArrayList<>();
        for (PersonProject personProject : personProjects) {
            orderedProjectIndexes.add(personProject.getProjectIndex());
        }
        return orderedProjectIndexes;
    }

    private TitledPane createProjectBox(int displayIndex, int projectIndex, List<PersonTask> personTasks,
                                        List<Project> globalProjects) {
        VBox projectBoxContent = new VBox(4);
        projectBoxContent.setId("tasks");
        projectBoxContent.getStyleClass().add("person-project-box");

        for (int i = 0; i < personTasks.size(); i++) {
            PersonTask personTask = personTasks.get(i);
            if (personTask.getProjectIndex() != projectIndex) {
                continue;
            }
            projectBoxContent.getChildren().add(createTaskDisplayRow(i + 1, resolveTask(personTask, globalProjects)));
        }

        TitledPane projectBox = new TitledPane(displayIndex + ". " + resolveProjectTitle(projectIndex, globalProjects),
                projectBoxContent);
        projectBox.setCollapsible(false);
        projectBox.setMinWidth(PROJECT_BOX_WIDTH);
        projectBox.getStyleClass().add("person-project-pane");
        return projectBox;
    }

    private String resolveProjectTitle(int projectIndex, List<Project> globalProjects) {
        if (projectIndex >= 0 && projectIndex < globalProjects.size()) {
            return globalProjects.get(projectIndex).title;
        }
        return "";
    }

    static Task resolveTask(PersonTask personTask, List<Project> globalProjects) {
        int projectIndex = personTask.getProjectIndex();
        int taskIndex = personTask.getTaskIndex();
        List<Task> projectTasks = globalProjects.get(projectIndex).getTasks();
        return projectTasks.get(taskIndex);
    }

    static HBox createTaskDisplayRow(int taskId, Task task) {
        Circle circle = new Circle(4);
        circle.getStyleClass().add("task-not-done");
        if (task != null && task.getStatus()) {
            circle.getStyleClass().add("task-done");
        }

        String taskDescription = task != null ? task.description : "[invalid-task-reference]";
        Label taskLabel = new Label(taskDescription + " (ID: " + taskId + ")");

        HBox taskContainer = new HBox(2);
        taskContainer.setAlignment(Pos.CENTER_LEFT);
        taskContainer.getChildren().addAll(circle, taskLabel);
        return taskContainer;
    }

    static int calculateWorkload(List<PersonTask> personTasks, List<Project> globalProjects) {
        int workload = 0;
        for (PersonTask personTask : personTasks) {
            int projectIndex = personTask.getProjectIndex();
            int taskIndex = personTask.getTaskIndex();
            if (projectIndex < 0 || projectIndex >= globalProjects.size()) {
                continue;
            }
            List<Task> projectTasks = globalProjects.get(projectIndex).getTasks();
            if (taskIndex < 0 || taskIndex >= projectTasks.size()) {
                continue;
            }
            if (!projectTasks.get(taskIndex).getStatus()) {
                workload++;
            }
        }
        return workload;
    }

    static Availability calculateAvailability(int workload) {
        return workload == Person.FREE
                ? Availability.FREE
                : workload <= Person.AVAILABLE
                ? Availability.AVAILABLE
                : workload <= Person.BUSY
                ? Availability.BUSY
                : Availability.OVERLOADED;
    }
}
