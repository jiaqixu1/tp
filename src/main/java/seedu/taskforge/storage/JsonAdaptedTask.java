package seedu.taskforge.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    private final String description;
    private final String projectTitle;
    private final Boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given {@code description}.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description") String description,
                           @JsonProperty("projectTitle") String projectTitle,
                           @JsonProperty("isDone") Boolean isDone) {
        this.description = description;
        this.projectTitle = projectTitle;
        this.isDone = isDone;
    }

    public JsonAdaptedTask(String description, String projectTitle) {
        this(description, projectTitle, null);
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        description = source.description;
        projectTitle = source.getProjectTitle();
        isDone = source.getStatus();
    }

    public String getDescription() {
        return description;
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        if (!Task.isValidTaskDescription(description)) {
            throw new IllegalValueException(Task.MESSAGE_CONSTRAINTS);
        }
        Task task = new Task(description, projectTitle);
        if (Boolean.TRUE.equals(isDone)) {
            task.setDone();
        }
        return task;
    }

}
