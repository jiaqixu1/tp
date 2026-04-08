package seedu.taskforge.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskforge.model.project.exceptions.DuplicateProjectException;
import seedu.taskforge.model.project.exceptions.ProjectNotFoundException;

public class UniquePersonProjectListTest {

    private final UniquePersonProjectList uniquePersonProjectList = new UniquePersonProjectList();
    private final PersonProject projectRefA = new PersonProject(0);
    private final PersonProject projectRefB = new PersonProject(1);

    @Test
    public void contains_nullPersonProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonProjectList.contains(null));
    }

    @Test
    public void contains_projectNotInList_returnsFalse() {
        assertFalse(uniquePersonProjectList.contains(projectRefA));
    }

    @Test
    public void contains_projectInList_returnsTrue() {
        uniquePersonProjectList.add(projectRefA);
        assertTrue(uniquePersonProjectList.contains(projectRefA));
    }

    @Test
    public void add_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonProjectList.add(null));
    }

    @Test
    public void add_duplicateProject_throwsDuplicateProjectException() {
        uniquePersonProjectList.add(projectRefA);
        assertThrows(DuplicateProjectException.class, () -> uniquePersonProjectList.add(new PersonProject(0)));
    }

    @Test
    public void setPersonProject_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonProjectList.setPersonProject(null, projectRefA));
    }

    @Test
    public void setPersonProject_nullEdited_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonProjectList.setPersonProject(projectRefA, null));
    }

    @Test
    public void setPersonProject_targetNotFound_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () ->
                uniquePersonProjectList.setPersonProject(projectRefA, projectRefB));
    }

    @Test
    public void setPersonProject_sameProject_success() {
        uniquePersonProjectList.add(projectRefA);
        uniquePersonProjectList.setPersonProject(projectRefA, projectRefA);

        UniquePersonProjectList expected = new UniquePersonProjectList();
        expected.add(projectRefA);
        assertEquals(expected, uniquePersonProjectList);
    }

    @Test
    public void setPersonProject_differentProject_success() {
        uniquePersonProjectList.add(projectRefA);
        uniquePersonProjectList.setPersonProject(projectRefA, projectRefB);

        UniquePersonProjectList expected = new UniquePersonProjectList();
        expected.add(projectRefB);
        assertEquals(expected, uniquePersonProjectList);
    }

    @Test
    public void setPersonProject_nonUniqueProject_throwsDuplicateProjectException() {
        uniquePersonProjectList.add(projectRefA);
        uniquePersonProjectList.add(projectRefB);
        assertThrows(DuplicateProjectException.class, () ->
                uniquePersonProjectList.setPersonProject(projectRefA, projectRefB));
    }

    @Test
    public void remove_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonProjectList.remove(null));
    }

    @Test
    public void remove_projectNotFound_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () -> uniquePersonProjectList.remove(projectRefA));
    }

    @Test
    public void remove_existingProject_success() {
        uniquePersonProjectList.add(projectRefA);
        uniquePersonProjectList.remove(projectRefA);
        assertEquals(new UniquePersonProjectList(), uniquePersonProjectList);
    }

    @Test
    public void setPersonProjects_nullUniquePersonProjectList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            uniquePersonProjectList.setPersonProjects((UniquePersonProjectList) null));
    }

    @Test
    public void setPersonProjects_uniquePersonProjectList_replacesOwnList() {
        uniquePersonProjectList.add(projectRefA);
        UniquePersonProjectList replacement = new UniquePersonProjectList();
        replacement.add(projectRefB);

        uniquePersonProjectList.setPersonProjects(replacement);
        assertEquals(replacement, uniquePersonProjectList);
    }

    @Test
    public void setPersonProjects_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniquePersonProjectList.setPersonProjects((List<PersonProject>) null));
    }

    @Test
    public void setPersonProjects_list_replacesOwnList() {
        uniquePersonProjectList.add(projectRefA);
        uniquePersonProjectList.setPersonProjects(Collections.singletonList(projectRefB));

        UniquePersonProjectList expected = new UniquePersonProjectList();
        expected.add(projectRefB);
        assertEquals(expected, uniquePersonProjectList);
    }

    @Test
    public void setPersonProjects_listWithDuplicates_throwsDuplicateProjectException() {
        List<PersonProject> duplicates = Arrays.asList(projectRefA, new PersonProject(0));
        assertThrows(DuplicateProjectException.class, () ->
                uniquePersonProjectList.setPersonProjects(duplicates));
    }

    @Test
    public void asUnmodifiableObservableList_modify_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniquePersonProjectList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equalsHashCodeAndToString() {
        UniquePersonProjectList first = new UniquePersonProjectList();
        UniquePersonProjectList second = new UniquePersonProjectList();

        first.add(projectRefA);
        first.add(projectRefB);
        second.add(projectRefB);
        second.add(projectRefA);

        assertTrue(first.equals(second));
        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(1));
        assertEquals(first.asUnmodifiableObservableList().toString(), first.toString());
    }
}
