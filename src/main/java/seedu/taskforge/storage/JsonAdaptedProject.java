package seedu.taskforge.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    private final String title;
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProject} with the given title and tasks.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("title") String title,
                            @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.title = title;
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    /**
     * Converts a given {@code Project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        title = source.title;
        tasks.addAll(source.getTasks().stream()
                .map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
    }

    public String getTitle() {
        return title;
    }

    public List<JsonAdaptedTask> getTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Converts this Jackson-friendly adapted Project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Project.
     */
    public Project toModelType() throws IllegalValueException {
        if (!Project.isValidProjectTitle(title)) {
            throw new IllegalValueException(Project.MESSAGE_CONSTRAINTS);
        }

        List<Task> modelTasks = new ArrayList<>();
        for (JsonAdaptedTask task : tasks) {
            modelTasks.add(task.toModelType());
        }

        return new Project(title, modelTasks);
    }

}
