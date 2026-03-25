package seedu.taskforge.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an address book with undo/redo capabilities.
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
     * Commits the current address book state to history.
     * Clears forward history.
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

    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }
}
