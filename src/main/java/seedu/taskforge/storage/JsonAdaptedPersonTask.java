package seedu.taskforge.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.model.person.PersonTask;

/**
 * Jackson-friendly version of {@link PersonTask}.
 */
class JsonAdaptedPersonTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "PersonTask's %s field is missing!";

    private final Integer projectIndex;
    private final Integer taskIndex;

    @JsonCreator
    public JsonAdaptedPersonTask(@JsonProperty("projectIndex") Integer projectIndex,
                                 @JsonProperty("taskIndex") Integer taskIndex) {
        this.projectIndex = projectIndex;
        this.taskIndex = taskIndex;
    }

    public JsonAdaptedPersonTask(PersonTask source) {
        this.projectIndex = source.getProjectIndex();
        this.taskIndex = source.getTaskIndex();
    }

    public PersonTask toModelType() throws IllegalValueException {
        if (projectIndex == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "projectIndex"));
        }
        if (taskIndex == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "taskIndex"));
        }
        if (projectIndex < 0 || taskIndex < 0) {
            throw new IllegalValueException("projectIndex and taskIndex must be non-negative");
        }
        return new PersonTask(projectIndex, taskIndex);
    }
}
