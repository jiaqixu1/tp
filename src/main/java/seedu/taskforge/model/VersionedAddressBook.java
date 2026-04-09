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
public class VersionedAddressBook extends AddressBook {
    private final List<ReadOnlyAddressBook> addressBookStateList = new ArrayList<>();
    private final List<String> storedInputs = new ArrayList<>();
    private int currentStatePointer;
    private int currentInputPointer;

    /**
     * Constructs a VersionedAddressBook with the given initial state.
     * Initializes the history with the initial state and sets pointers accordingly.
     * @param initialState The initial address book state
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList.add(new AddressBook(initialState));

        currentStatePointer = 0;
        currentInputPointer = -1;
    }

    /**
     * Stores the current address book state in history and clears forward history.
     * @param input The command input string that produced this state
     */
    public void commit(String input) {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
        storedInputs.subList(currentInputPointer + 1, storedInputs.size()).clear();

        addressBookStateList.add(new AddressBook(this));
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
        resetData(addressBookStateList.get(currentStatePointer));

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
        resetData(addressBookStateList.get(currentStatePointer));

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
        return currentStatePointer < addressBookStateList.size() - 1;
    }
}
