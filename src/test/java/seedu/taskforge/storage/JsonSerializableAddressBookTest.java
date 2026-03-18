package seedu.taskforge.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.taskforge.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.commons.util.JsonUtil;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path DUPLICATE_PROJECT_FILE = TEST_DATA_FOLDER.resolve("duplicateProjectAddressBook.json");
    private static final Path PERSON_WITH_MISSING_PROJECT_FILE =
            TEST_DATA_FOLDER.resolve("personWithMissingProjectAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateProjects_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PROJECT_FILE,
            JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PROJECT,
            dataFromFile::toModelType);
    }

    @Test
    public void toModelType_personWithMissingProject_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(PERSON_WITH_MISSING_PROJECT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class,
                String.format(JsonSerializableAddressBook.MESSAGE_PERSON_PROJECT_NOT_IN_PROJECT_LIST,
                        "John Doe", "Beta"),
                dataFromFile::toModelType);
    }

    @Test
    public void constructor_nullProjects_success() throws Exception {
        JsonSerializableAddressBook serializableAddressBook = new JsonSerializableAddressBook(
            Collections.emptyList(), null);

        assertEquals(new AddressBook(), serializableAddressBook.toModelType());
    }
}
