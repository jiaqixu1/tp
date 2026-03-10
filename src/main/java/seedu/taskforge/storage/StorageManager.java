package seedu.taskforge.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.taskforge.commons.core.LogsCenter;
import seedu.taskforge.commons.exceptions.DataLoadingException;
import seedu.taskforge.model.ReadOnlyTaskForge;
import seedu.taskforge.model.ReadOnlyUserPrefs;
import seedu.taskforge.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskForgeStorage taskForgeStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(TaskForgeStorage taskForgeStorage, UserPrefsStorage userPrefsStorage) {
        this.taskForgeStorage = taskForgeStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return taskForgeStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskForge> readAddressBook() throws DataLoadingException {
        return readAddressBook(taskForgeStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskForge> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskForgeStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskForge addressBook) throws IOException {
        saveAddressBook(addressBook, taskForgeStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskForge addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskForgeStorage.saveAddressBook(addressBook, filePath);
    }

}
