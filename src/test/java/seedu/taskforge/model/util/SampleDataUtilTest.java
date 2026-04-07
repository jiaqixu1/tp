package seedu.taskforge.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.taskforge.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskforge.model.ReadOnlyAddressBook;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

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

    @Test
    public void getSampleProjects_returnsExpectedProjects() {
        Project[] sampleProjects = SampleDataUtil.getSampleProjects();

        assertEquals(6, sampleProjects.length);
        assertEquals(new Project("Interior", List.of(new Task("add new feature"))), sampleProjects[0]);
        assertEquals(new Project("Future Gen", List.of(new Task("read notes"))), sampleProjects[5]);
    }

    @Test
    public void getProjectList_returnsConvertedProjects() {
        List<Project> projectList = SampleDataUtil.getProjectList("alpha", "beta");

        assertEquals(List.of(new Project("alpha"), new Project("beta")), projectList);
    }

    @Test
    public void getPersonProjectList_returnsReferences() {
        List<PersonProject> personProjectList = SampleDataUtil.getPersonProjectList("Interior", "Web app");

        assertEquals(List.of(new PersonProject(0), new PersonProject(4)), personProjectList);
    }

    @Test
    public void getPersonProjectList_unknownProject_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> SampleDataUtil.getPersonProjectList("Missing Project"));
    }

    @Test
    public void getTaskList_returnsConvertedTasks() {
        List<Task> taskList = SampleDataUtil.getTaskList("task A", "task B");

        assertEquals(List.of(new Task("task A"), new Task("task B")), taskList);
    }

    @Test
    public void samplePersons_haveExpectedTaskReferences() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        assertEquals(List.of(new PersonTask(0, 0)), samplePersons[0].getTasks());
        assertEquals(List.of(new PersonTask(1, 0), new PersonTask(2, 0)), samplePersons[1].getTasks());
    }
}
