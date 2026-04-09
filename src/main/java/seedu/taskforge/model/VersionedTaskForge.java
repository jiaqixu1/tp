package seedu.taskforge.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an address book with undo/redo capabilities.
 *
 * <p>The undo/redo history is maintained only for the current session.
 * History is not persisted between application restarts. When the
 * application closes, all saved states are discarded.</p>
 *
 * Maintains a history of address book states and the commands that created them.
 */
public class VersionedTaskForge extends TaskForge {
    private final List<ReadOnlyTaskForge> taskForgeStateList = new ArrayList<>();
    private final List<String> storedInputs = new ArrayList<>();
    private int currentStatePointer;
    private int currentInputPointer;

    /**
     * Constructs a VersionedTaskForge with the given initial state.
     * Initializes the history with the initial state and sets pointers accordingly.
     * @param initialState The initial task forge state
     */
    public VersionedTaskForge(ReadOnlyTaskForge initialState) {
        super(initialState);

        taskForgeStateList.add(new TaskForge(initialState));

        currentStatePointer = 0;
        currentInputPointer = -1;
    }

    /**
     * Stores the current taskforge state in history and clears forward history.
     * @param input The command input string that produced this state
     */
    public void commit(String input) {
        taskForgeStateList.subList(currentStatePointer + 1, taskForgeStateList.size()).clear();
        storedInputs.subList(currentInputPointer + 1, storedInputs.size()).clear();

        taskForgeStateList.add(new TaskForge(this));
        storedInputs.add(input);

        currentStatePointer++;
        currentInputPointer++;
    }

    /**
     * Restores the previous address book state from history.
     * @return The command that is being undone
     */
    public String undo() {
        currentStatePointer--;
        resetData(taskForgeStateList.get(currentStatePointer));

        String commandToUndo = storedInputs.get(currentInputPointer);
        currentInputPointer--;

        return commandToUndo;
    }

    /**
     * Restores a previously undone address book state.
     * @return The command that is being redone
     */
    public String redo() {
        currentStatePointer++;
        resetData(taskForgeStateList.get(currentStatePointer));

        currentInputPointer++;
        return storedInputs.get(currentInputPointer);
    }

    /**
     * Checks whether it is possible to perform undo.
     * Undo is possible if there is a previous state to restore.
     * @return true if there is a previous state available, false otherwise
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Checks whether it is possible to perform redo.
     * Redo is possible if there is a next state to restore.
     * @return true if there is a next state available, false otherwise
     */
    public boolean canRedo() {
        return currentStatePointer < taskForgeStateList.size() - 1;
    }
}
