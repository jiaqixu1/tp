package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.project.Project;
import seedu.address.model.task.Task;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PROJECT = "#alpha";
    private static final String INVALID_TASK = "#refactor code";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_PROJECT_1 = "alpha";
    private static final String VALID_PROJECT_2 = "beta";
    private static final String VALID_TASK_1 = "refactor code";
    private static final String VALID_TASK_2 = "meeting with client";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseProject_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseProject(null));
    }

    @Test
    public void parseProject_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseProject(INVALID_PROJECT));
    }

    @Test
    public void parseProject_validValueWithoutWhitespace_returnsProject() throws Exception {
        Project expectedProject = new Project(VALID_PROJECT_1);
        assertEquals(expectedProject, ParserUtil.parseProject(VALID_PROJECT_1));
    }

    @Test
    public void parseProject_validValueWithWhitespace_returnsTrimmedProject() throws Exception {
        String projectWithWhitespace = WHITESPACE + VALID_PROJECT_1 + WHITESPACE;
        Project expectedProject = new Project(VALID_PROJECT_1);
        assertEquals(expectedProject, ParserUtil.parseProject(projectWithWhitespace));
    }

    @Test
    public void parseProjects_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseProjects(null));
    }

    @Test
    public void parseProjects_collectionWithInvalidProject_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseProjects(
                Arrays.asList(VALID_PROJECT_1, INVALID_PROJECT))
        );
    }

    @Test
    public void parseProjects_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseProjects(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseProjects_collectionWithValidProjects_returnsProjectSet() throws Exception {
        List<Project> actualProjectSet = ParserUtil.parseProjects(Arrays.asList(VALID_PROJECT_1, VALID_PROJECT_2));
        List<Project> expectedProjectSet = new ArrayList<Project>(Arrays.asList(
                new Project(VALID_PROJECT_1), new Project(VALID_PROJECT_2))
        );

        assertEquals(expectedProjectSet, actualProjectSet);
    }

    @Test
    public void parseTask_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTask(null));
    }

    @Test
    public void parseTask_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_TASK));
    }

    @Test
    public void parseTask_validValueWithoutWhitespace_returnsTask() throws Exception {
        Task expectedTask = new Task(VALID_TASK_1);
        assertEquals(expectedTask, ParserUtil.parseTask(VALID_TASK_1));
    }

    @Test
    public void parseTask_validValueWithWhitespace_returnsTrimmedTask() throws Exception {
        String taskWithWhitespace = WHITESPACE + VALID_TASK_1 + WHITESPACE;
        Task expectedTask = new Task(VALID_TASK_1);
        assertEquals(expectedTask, ParserUtil.parseTask(taskWithWhitespace));
    }

    @Test
    public void parseTasks_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTasks(null));
    }

    @Test
    public void parseTasks_collectionWithInvalidTasks_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTasks(Arrays.asList(VALID_TASK_1, INVALID_TASK)));
    }

    @Test
    public void parseTasks_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTasks(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTasks_collectionWithValidTasks_returnsTaskSet() throws Exception {
        List<Task> actualTaskSet = ParserUtil.parseTasks(Arrays.asList(VALID_TASK_1, VALID_TASK_2));
        List<Task> expectedTaskSet = new ArrayList<Task>(Arrays.asList(new Task(VALID_TASK_1), new Task(VALID_TASK_2)));

        assertEquals(expectedTaskSet, actualTaskSet);
    }
}
