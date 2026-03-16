package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

public class SampleDataUtilTest {
    @Test
    public void sampleDataUtil_checkCorrectness() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();
        ObservableList<Person> expectedList = FXCollections.observableArrayList(samplePersons);

        assertNotNull(samplePersons);

        assertEquals(6, samplePersons.length);

        assertEquals(expectedList, addressBook.getPersonList());
    }
}
