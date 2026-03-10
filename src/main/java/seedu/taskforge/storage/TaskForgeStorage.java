package seedu.taskforge.storage;

import seedu.taskforge.commons.exceptions.DataLoadingException;
import seedu.taskforge.model.ReadOnlyTaskForge;
import seedu.taskforge.model.TaskForge;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link TaskForge}.
 */
public interface TaskForgeStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTaskForge}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyTaskForge> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyTaskForge> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyTaskForge} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyTaskForge addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyTaskForge)
     */
    void saveAddressBook(ReadOnlyTaskForge addressBook, Path filePath) throws IOException;

}
