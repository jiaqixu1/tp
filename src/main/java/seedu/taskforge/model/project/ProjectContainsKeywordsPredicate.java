package seedu.taskforge.model.project;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.taskforge.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields match any of the keywords given.
 */
public class ProjectContainsKeywordsPredicate implements Predicate<Project> {
    private List<String> projectKeywords;
    private List<String> taskKeywords;

    public ProjectContainsKeywordsPredicate() {

    }

    public ProjectContainsKeywordsPredicate setProjectKeywords(List<String> projectKeywords) {
        this.projectKeywords = projectKeywords;
        this.taskKeywords = null;
        return this;
    }

    public ProjectContainsKeywordsPredicate setTaskKeywords(List<String> taskKeywords) {
        this.taskKeywords = taskKeywords;
        this.projectKeywords = null;
        return this;
    }

    @Override
    public boolean test(Project project) {
        if (!isAnyFieldChecked()) {
            return false;
        }

        if (isNonEmpty(projectKeywords)) {
            return projectKeywords.stream()
                    .anyMatch(keyword -> containsIgnoreCase(project.toString(), keyword));
        }

        return taskKeywords.stream()
                .anyMatch(keyword -> project.getTasks().stream()
                        .anyMatch(task -> containsIgnoreCase(task.description, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectContainsKeywordsPredicate)) {
            return false;
        }

        ProjectContainsKeywordsPredicate otherPredicate = (ProjectContainsKeywordsPredicate) other;
        return Objects.equals(taskKeywords, otherPredicate.taskKeywords)
                && Objects.equals(projectKeywords, otherPredicate.projectKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("projectKeywords", projectKeywords)
                .add("taskKeywords", taskKeywords)
                .toString();
    }

    public boolean isAnyFieldChecked() {
        return isNonEmpty(taskKeywords) || isNonEmpty(projectKeywords);
    }

    private boolean isNonEmpty(List<String> keywords) {
        return keywords != null && !keywords.isEmpty();
    }

    private static boolean containsIgnoreCase(String string, String substring) {
        return string.toLowerCase().contains(substring.toLowerCase());
    }
}
