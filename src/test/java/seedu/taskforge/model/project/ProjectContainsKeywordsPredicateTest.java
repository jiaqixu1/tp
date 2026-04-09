package seedu.taskforge.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.taskforge.model.task.Task;

public class ProjectContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ProjectContainsKeywordsPredicate emptyPredicate = new ProjectContainsKeywordsPredicate();
        ProjectContainsKeywordsPredicate firstPredicate = new ProjectContainsKeywordsPredicate()
                .setProjectKeywords(Collections.singletonList(VALID_PROJECT_ALPHA));
        ProjectContainsKeywordsPredicate firstPredicateCopy = new ProjectContainsKeywordsPredicate()
                .setProjectKeywords(Collections.singletonList(VALID_PROJECT_ALPHA));
        ProjectContainsKeywordsPredicate differentProjectPredicate = new ProjectContainsKeywordsPredicate()
                .setProjectKeywords(Collections.singletonList("beta"));
        ProjectContainsKeywordsPredicate taskPredicate = new ProjectContainsKeywordsPredicate()
                .setTaskKeywords(Collections.singletonList(VALID_TASK_REFACTOR));
        ProjectContainsKeywordsPredicate secondPredicate = new ProjectContainsKeywordsPredicate()
                .setTaskKeywords(Collections.singletonList("Deploy"));

        assertTrue(emptyPredicate.equals(new ProjectContainsKeywordsPredicate()));
        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(firstPredicateCopy));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(differentProjectPredicate));
        assertFalse(taskPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_noKeywords_returnsFalse() {
        ProjectContainsKeywordsPredicate predicate = new ProjectContainsKeywordsPredicate();

        assertFalse(predicate.test(new Project(VALID_PROJECT_ALPHA,
                Collections.singletonList(new Task(VALID_TASK_REFACTOR)))));
    }

    @Test
    public void test_projectKeywordsMatch_returnsTrue() {
        ProjectContainsKeywordsPredicate predicate = new ProjectContainsKeywordsPredicate()
                .setProjectKeywords(Arrays.asList("alp", "beta"));

        assertTrue(predicate.test(new Project(VALID_PROJECT_ALPHA)));
    }

    @Test
    public void test_taskKeywordsMatch_returnsTrue() {
        ProjectContainsKeywordsPredicate predicate = new ProjectContainsKeywordsPredicate()
                .setTaskKeywords(Collections.singletonList("factor"));

        assertTrue(predicate.test(new Project(VALID_PROJECT_ALPHA,
                Collections.singletonList(new Task(VALID_TASK_REFACTOR)))));
    }

    @Test
    public void test_secondSetterOverridesFirstMatcher() {
        ProjectContainsKeywordsPredicate predicate = new ProjectContainsKeywordsPredicate()
                .setProjectKeywords(Collections.singletonList("alp"))
                .setTaskKeywords(Collections.singletonList("factor"));

        assertTrue(predicate.test(new Project(VALID_PROJECT_ALPHA,
                Collections.singletonList(new Task(VALID_TASK_REFACTOR)))));
    }

    @Test
    public void test_secondSetterOverridesFirstMismatch() {
        Project alpha = new Project(VALID_PROJECT_ALPHA, Collections.singletonList(new Task(VALID_TASK_REFACTOR)));

        ProjectContainsKeywordsPredicate taskModeIgnoresEarlierProjectKeywords = new ProjectContainsKeywordsPredicate()
                .setProjectKeywords(Collections.singletonList("gamma"))
                .setTaskKeywords(Collections.singletonList("factor"));
        ProjectContainsKeywordsPredicate projectModeIgnoresEarlierTaskKeywords = new ProjectContainsKeywordsPredicate()
                .setTaskKeywords(Collections.singletonList("deploy"))
                .setProjectKeywords(Collections.singletonList("alp"));

        assertTrue(taskModeIgnoresEarlierProjectKeywords.test(alpha));
        assertTrue(projectModeIgnoresEarlierTaskKeywords.test(alpha));
    }

    @Test
    public void test_selectedModeDoesNotMatch_returnsFalse() {
        ProjectContainsKeywordsPredicate projectPredicate = new ProjectContainsKeywordsPredicate()
                .setProjectKeywords(Collections.singletonList("gamma"));
        ProjectContainsKeywordsPredicate taskPredicate = new ProjectContainsKeywordsPredicate()
                .setTaskKeywords(Collections.singletonList("deploy"));

        Project alpha = new Project(VALID_PROJECT_ALPHA, Collections.singletonList(new Task(VALID_TASK_REFACTOR)));

        assertFalse(projectPredicate.test(alpha));
        assertFalse(taskPredicate.test(alpha));
    }

    @Test
    public void toStringMethod() {
        ProjectContainsKeywordsPredicate projectPredicate = new ProjectContainsKeywordsPredicate()
                .setProjectKeywords(Collections.singletonList(VALID_PROJECT_ALPHA));
        String expected = ProjectContainsKeywordsPredicate.class.getCanonicalName()
                + "{projectKeywords=[alpha], taskKeywords=null}";
        assertEquals(expected, projectPredicate.toString());
    }

    @Test
    public void isAnyFieldChecked_nullAndEmptyLists_returnsFalse() {
        assertFalse(new ProjectContainsKeywordsPredicate().isAnyFieldChecked());
        assertFalse(new ProjectContainsKeywordsPredicate()
                .setProjectKeywords(Collections.emptyList()).isAnyFieldChecked());
        assertFalse(new ProjectContainsKeywordsPredicate()
                .setTaskKeywords(Collections.emptyList()).isAnyFieldChecked());
    }
}
