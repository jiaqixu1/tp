package seedu.taskforge.model.project;

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

public class UniqueProjectListTest {

    private final UniqueProjectList uniqueProjectList = new UniqueProjectList();
    private final Project alpha = new Project("alpha");
    private final Project beta = new Project("beta");

    @Test
    public void contains_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.contains(null));
    }

    @Test
    public void contains_projectNotInList_returnsFalse() {
        assertFalse(uniqueProjectList.contains(alpha));
    }

    @Test
    public void contains_projectInList_returnsTrue() {
        uniqueProjectList.add(alpha);
        assertTrue(uniqueProjectList.contains(alpha));
    }

    @Test
    public void add_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.add(null));
    }

    @Test
    public void add_duplicateProject_throwsDuplicateProjectException() {
        uniqueProjectList.add(alpha);
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.add(alpha));
    }

    @Test
    public void setProject_nullTargetProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProject(null, alpha));
    }

    @Test
    public void setProject_nullEditedProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProject(alpha, null));
    }

    @Test
    public void setProject_targetProjectNotInList_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () -> uniqueProjectList.setProject(alpha, beta));
    }

    @Test
    public void setProject_editedProjectIsSameProject_success() {
        uniqueProjectList.add(alpha);
        uniqueProjectList.setProject(alpha, alpha);

        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(alpha);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasDifferentIdentity_success() {
        uniqueProjectList.add(alpha);
        uniqueProjectList.setProject(alpha, beta);

        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(beta);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasNonUniqueIdentity_throwsDuplicateProjectException() {
        uniqueProjectList.add(alpha);
        uniqueProjectList.add(beta);

        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.setProject(alpha, beta));
    }

    @Test
    public void remove_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.remove(null));
    }

    @Test
    public void remove_projectDoesNotExist_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () -> uniqueProjectList.remove(alpha));
    }

    @Test
    public void remove_existingProject_removesProject() {
        uniqueProjectList.add(alpha);
        uniqueProjectList.remove(alpha);
        assertEquals(new UniqueProjectList(), uniqueProjectList);
    }

    @Test
    public void setProjects_nullUniqueProjectList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProjects((UniqueProjectList) null));
    }

    @Test
    public void setProjects_uniqueProjectList_replacesOwnListWithProvidedUniqueProjectList() {
        uniqueProjectList.add(alpha);

        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(beta);

        uniqueProjectList.setProjects(expectedUniqueProjectList);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProjects((List<Project>) null));
    }

    @Test
    public void setProjects_list_replacesOwnListWithProvidedList() {
        uniqueProjectList.add(alpha);
        List<Project> projectList = Collections.singletonList(beta);

        uniqueProjectList.setProjects(projectList);

        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(beta);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_listWithDuplicateProjects_throwsDuplicateProjectException() {
        List<Project> listWithDuplicateProjects = Arrays.asList(alpha, alpha);
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.setProjects(listWithDuplicateProjects));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueProjectList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equalsAndHashCode() {
        UniqueProjectList first = new UniqueProjectList();
        UniqueProjectList second = new UniqueProjectList();

        first.add(alpha);
        first.add(beta);
        second.add(beta);
        second.add(alpha);

        assertTrue(first.equals(second));
        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(null));
        assertFalse(first.equals(1));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueProjectList.asUnmodifiableObservableList().toString(), uniqueProjectList.toString());
    }
}
