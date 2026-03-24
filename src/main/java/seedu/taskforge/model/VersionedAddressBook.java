package seedu.taskforge.model;

import seedu.taskforge.logic.commands.CommandResult;

import java.util.ArrayList;
import java.util.List;

public class VersionedAddressBook extends AddressBook {
    private final List<ReadOnlyAddressBook> addressBookStateList = new ArrayList<>();
    private final List<String> storedInputs = new ArrayList<>();
    private int currentStatePointer;
    private int currentInputPointer;

    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList.add(new AddressBook(initialState));

        currentStatePointer = 0;
        currentInputPointer = -1;
    }

    public void commit(String input) {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
        storedInputs.subList(currentInputPointer + 1, storedInputs.size()).clear();

        addressBookStateList.add(new AddressBook(this));
        storedInputs.add(input);

        currentStatePointer++;
        currentInputPointer++;
    }

    public String undo() {
        currentStatePointer--;
        resetData(addressBookStateList.get(currentStatePointer));

        String CommandToUndo = storedInputs.get(currentInputPointer);
        currentInputPointer--;

        return CommandToUndo;
    }

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
