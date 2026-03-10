package seedu.taskforge.storage;

import seedu.taskforge.commons.exceptions.DataLoadingException;
import seedu.taskforge.model.ReadOnlyTaskForge;
import seedu.taskforge.model.ReadOnlyUserPrefs;
import seedu.taskforge.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends TaskForgeStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyTaskForge> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyTaskForge addressBook) throws IOException;

}
