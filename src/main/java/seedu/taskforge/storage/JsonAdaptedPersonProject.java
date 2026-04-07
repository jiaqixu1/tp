package seedu.taskforge.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.model.person.PersonProject;

/**
 * Jackson-friendly version of {@link PersonProject}.
 */
class JsonAdaptedPersonProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "PersonProject's %s field is missing!";

    private final Integer projectIndex;

    /**
     * Constructs a {@code JsonAdaptedPersonProject} with the given project index.
     */
    @JsonCreator
    public JsonAdaptedPersonProject(@JsonProperty("projectIndex") Integer projectIndex) {
        this.projectIndex = projectIndex;
    }

    /**
     * Converts a given {@code PersonProject} into this class for Jackson use.
     */
    public JsonAdaptedPersonProject(PersonProject source) {
        this.projectIndex = source.getProjectIndex();
    }

    /**
     * Converts this Jackson-friendly adapted person project object into the model's {@code PersonProject} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person project.
     */
    public PersonProject toModelType() throws IllegalValueException {
        if (projectIndex == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "projectIndex"));
        }
        if (projectIndex < 0) {
            throw new IllegalValueException("Project index must be non-negative");
        }
        return new PersonProject(projectIndex);
    }
}
