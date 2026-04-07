package seedu.taskforge.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.person.Phone;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final List<JsonAdaptedPersonProject> projects = new ArrayList<>();
    private final List<JsonAdaptedPersonTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("projects") List<JsonAdaptedPersonProject> projects,
                             @JsonProperty("tasks") List<JsonAdaptedPersonTask> tasks) {
        this.name = name;
        this.phone = phone;
        this.email = email;

        if (projects != null) {
            this.projects.addAll(projects);
        }
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        projects.addAll(source.getProjects().stream()
                .map(JsonAdaptedPersonProject::new)
                .collect(Collectors.toList()));
        tasks.addAll(source.getTasks().stream()
            .map(JsonAdaptedPersonTask::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<PersonProject> personProjects = new ArrayList<>();
        final List<PersonTask> personTasks = new ArrayList<>();
        for (JsonAdaptedPersonProject personProject : projects) {
            personProjects.add(personProject.toModelType());
        }
        for (JsonAdaptedPersonTask task : tasks) {
            personTasks.add(task.toModelType());
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);


        final List<PersonProject> modelPersonProjects = new ArrayList<>(personProjects);
        final List<PersonTask> modelTasks = new ArrayList<>(personTasks);
        return new Person(modelName, modelPhone, modelEmail, modelPersonProjects, modelTasks);
    }

}
