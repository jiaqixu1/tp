package seedu.taskforge.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.taskforge.model.ReadOnlyAddressBook;
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

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
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
    @FXML
    private FlowPane tasks;

    /**
     * Creates a {@code PersonCard} with the given {@code Person}, index, and {@code ReadOnlyAddressBook} to display.
     */
    public PersonCard(Person person, int displayedIndex, ReadOnlyAddressBook addressBook) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);

        List<PersonProject> personProjectList = person.getProjects();
        List<PersonTask> taskList = person.getTasks();
        List<Project> globalProjectList = new ArrayList<>(addressBook.getProjectList());

        int workload = calculateWorkload(taskList, globalProjectList);
        String availabilityString = calculateAvailability(workload).toString();
        availability.setText(availabilityString + ".  Workload:  " + workload);
        availabilityIndicator.getStyleClass().add(availabilityString);

        IntStream.range(0, personProjectList.size())
                .forEach(i -> {
                    PersonProject personProject = personProjectList.get(i);
                    int projectIndex = personProject.getProjectIndex();
                    String projectTitle = "";
                    if (projectIndex >= 0 && projectIndex < globalProjectList.size()) {
                        projectTitle = globalProjectList.get(projectIndex).title;
                    }
                    projects.getChildren().add(
                            new Label((i + 1) + ". " + projectTitle)
                    );
                });
        IntStream.range(0, taskList.size())
                .forEach(i -> {
                    PersonTask personTask = taskList.get(i);
                    String taskLabel = "[invalid-task-reference]";
                    String status = "[ ] ";
                    int projectIndex = personTask.getProjectIndex();
                    int taskIndex = personTask.getTaskIndex();
                    if (projectIndex >= 0 && projectIndex < globalProjectList.size()) {
                        List<Task> projectTasks = globalProjectList.get(projectIndex).getTasks();
                        if (taskIndex >= 0 && taskIndex < projectTasks.size()) {
                            Task task = projectTasks.get(taskIndex);
                            taskLabel = task.description;
                            status = task.getStatus() ? "[X] " : "[ ] ";
                        }
                    }
                    tasks.getChildren().add(
                            new Label((i + 1) + ". " + status + taskLabel)
                    );
                });
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
