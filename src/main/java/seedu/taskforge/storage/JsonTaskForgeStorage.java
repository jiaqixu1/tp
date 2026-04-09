package seedu.taskforge.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.taskforge.commons.core.LogsCenter;
import seedu.taskforge.commons.exceptions.DataLoadingException;
import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.commons.util.FileUtil;
import seedu.taskforge.commons.util.JsonUtil;
import seedu.taskforge.model.ReadOnlyTaskForge;

/**
 * A class to access TaskForge data stored as a json file on the hard disk.
 */
public class JsonTaskForgeStorage implements TaskForgeStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTaskForgeStorage.class);

    private Path filePath;

    public JsonTaskForgeStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTaskForgeFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskForge> readTaskForge() throws DataLoadingException {
        return readTaskForge(filePath);
    }

    /**
     * Reads the address book data from {@code filePath}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyTaskForge> readTaskForge(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTaskForge> jsonTaskForge = JsonUtil.readJsonFile(
                filePath, JsonSerializableTaskForge.class);
        if (!jsonTaskForge.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTaskForge.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTaskForge(ReadOnlyTaskForge taskForge) throws IOException {
        saveTaskForge(taskForge, filePath);
    }

    /**
     * Saves the address book data to {@code filePath}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTaskForge(ReadOnlyTaskForge taskForge, Path filePath) throws IOException {
        requireNonNull(taskForge);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaskForge(taskForge), filePath);
    }

}
