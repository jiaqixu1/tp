package seedu.taskforge.testutil;

import seedu.taskforge.model.TaskForge;
import seedu.taskforge.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TaskForge taskForge;

    public AddressBookBuilder() {
        taskForge = new TaskForge();
    }

    public AddressBookBuilder(TaskForge taskForge) {
        this.taskForge = taskForge;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        taskForge.addPerson(person);
        return this;
    }

    public TaskForge build() {
        return taskForge;
    }
}
