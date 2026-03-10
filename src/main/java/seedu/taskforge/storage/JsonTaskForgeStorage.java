package seedu.taskforge.storage;

import seedu.taskforge.commons.core.LogsCenter;
import seedu.taskforge.commons.exceptions.DataLoadingException;
import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.commons.util.FileUtil;
import seedu.taskforge.commons.util.JsonUtil;
import seedu.taskforge.model.ReadOnlyTaskForge;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonTaskForgeStorage implements TaskForgeStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTaskForgeStorage.class);

    private Path filePath;

    public JsonTaskForgeStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskForge> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyTaskForge> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTaskForge> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableTaskForge.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskForge addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyTaskForge)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyTaskForge addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskForge(addressBook), filePath);
    }

}
