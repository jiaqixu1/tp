package seedu.taskforge.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PersonProjectTest {

    @Test
    public void getProjectIndex_returnsExpectedValue() {
        PersonProject personProject = new PersonProject(2);

        assertEquals(2, personProject.getProjectIndex());
    }

    @Test
    public void equalsHashCodeAndToString() {
        PersonProject projectA = new PersonProject(1);
        PersonProject projectACopy = new PersonProject(1);
        PersonProject projectB = new PersonProject(2);

        assertTrue(projectA.equals(projectACopy));
        assertEquals(projectA.hashCode(), projectACopy.hashCode());
        assertFalse(projectA.equals(projectB));
        assertFalse(projectA.equals(1));
        assertEquals(PersonProject.class.getCanonicalName() + "{projectIndex=1}", projectA.toString());
    }
}
