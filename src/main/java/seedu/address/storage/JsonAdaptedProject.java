package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Project;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    private final String projectTitle;

    /**
     * Constructs a {@code JsonAdaptedProject} with the given {@code ProjectName}.
     */
    @JsonCreator
    public JsonAdaptedProject(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    /**
     * Converts a given {@code Project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        projectTitle = source.title;
    }

    @JsonValue
    public String getProjectTitle() {
        return projectTitle;
    }

    /**
     * Converts this Jackson-friendly adapted Project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Project.
     */
    public Project toModelType() throws IllegalValueException {
        if (!Project.isValidProjectName(projectTitle)) {
            throw new IllegalValueException(Project.MESSAGE_CONSTRAINTS);
        }
        return new Project(projectTitle);
    }

}
