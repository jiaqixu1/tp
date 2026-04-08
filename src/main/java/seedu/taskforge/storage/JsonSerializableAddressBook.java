package seedu.taskforge.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.ReadOnlyAddressBook;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_PROJECT = "Projects list contains duplicate project(s).";
    public static final String MESSAGE_PERSON_PROJECT_NOT_IN_PROJECT_LIST =
            "Person %1$s contains project %2$s that does not exist in the project list.";
    public static final String MESSAGE_PERSON_TASK_NOT_IN_ASSIGNED_PROJECTS =
            "Person %1$s contains task %2$s that does not exist in any assigned project.";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedProject> projects = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and projects.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("projects") List<JsonAdaptedProject> projects) {
        this.persons.addAll(persons);
        if (projects != null) {
            this.projects.addAll(projects);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        projects.addAll(source.getProjectList().stream().map(JsonAdaptedProject::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedProject jsonAdaptedProject : projects) {
            Project project = jsonAdaptedProject.toModelType();
            if (addressBook.hasProject(project)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECT);
            }
            addressBook.addProject(project);
        }

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }

            for (PersonProject personProject : person.getProjects()) {
                int projectIndex = personProject.getProjectIndex();
                if (projectIndex < 0 || projectIndex >= addressBook.getProjectList().size()) {
                    throw new IllegalValueException(String.format(
                            MESSAGE_PERSON_PROJECT_NOT_IN_PROJECT_LIST,
                            person.getName(), projectIndex));
                }
            }

            for (PersonTask personTask : person.getTasks()) {
                int taskProjectIndex = personTask.getProjectIndex();
                int taskIndex = personTask.getTaskIndex();

                if (taskProjectIndex < 0 || taskProjectIndex >= addressBook.getProjectList().size()) {
                    throw new IllegalValueException(String.format(
                        MESSAGE_PERSON_TASK_NOT_IN_ASSIGNED_PROJECTS,
                        person.getName(), "[invalid-project-index]"));
                }

                Project project = addressBook.getProjectList().get(taskProjectIndex);
                if (taskIndex < 0 || taskIndex >= project.getTasks().size()) {
                    throw new IllegalValueException(String.format(
                        MESSAGE_PERSON_TASK_NOT_IN_ASSIGNED_PROJECTS,
                        person.getName(), "[invalid-task-index]"));
                }

                boolean projectAssignedToPerson = person.getProjects().stream()
                    .anyMatch(pp -> pp.getProjectIndex() == taskProjectIndex);
                if (!projectAssignedToPerson) {
                    throw new IllegalValueException(String.format(
                        MESSAGE_PERSON_TASK_NOT_IN_ASSIGNED_PROJECTS,
                        person.getName(), project.getTasks().get(taskIndex).description));
                }
            }

            addressBook.addPerson(person);
        }
        return addressBook;
    }

}
