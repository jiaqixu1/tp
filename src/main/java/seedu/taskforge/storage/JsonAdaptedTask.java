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

    /**
     * Constructs a {@code JsonAdaptedTask} with the given {@code description}.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description") String description) {
        this.description = description;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        description = source.description;
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
        return new Task(description);
    }

}
