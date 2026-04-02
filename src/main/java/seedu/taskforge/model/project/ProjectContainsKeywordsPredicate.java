package seedu.taskforge.model.project;

import seedu.taskforge.commons.util.StringUtil;
import seedu.taskforge.commons.util.ToStringBuilder;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonContainsKeywordsPredicate;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ProjectContainsKeywordsPredicate implements Predicate<Project> {
    private List<String> projectKeywords;
    private List<String> taskKeywords;

    public ProjectContainsKeywordsPredicate() {

    }

    public ProjectContainsKeywordsPredicate setProjectKeywords(List<String> projectKeywords) {
        this.projectKeywords = projectKeywords;
        return this;
    }

    public ProjectContainsKeywordsPredicate setTaskKeywords(List<String> taskKeywords) {
        this.taskKeywords = taskKeywords;
        return this;
    }

    @Override
    public boolean test(Project project) {
        if (!isAnyFieldChecked()) {
            return false;
        }

        return isKeywordMatch(project, projectKeywords, p -> p.toString())
                && isKeywordMatchForCollection(project, taskKeywords, p -> p.getTasks().stream()
                .map(task -> task.description).toList());
    }

    private boolean isKeywordMatch(Project project, List<String> keywords,
                                   java.util.function.Function<Project, String> fieldMapper) {
        return !isNonEmpty(keywords) || keywords.stream()
                .anyMatch(keyword -> containsIgnoreCase(fieldMapper.apply(project), keyword));
    }

    private boolean isKeywordMatchForCollection(Project project, List<String> keywords,
                                                java.util.function.Function<Project, List<String>> collectionMapper) {
        return !isNonEmpty(keywords) || keywords.stream()
                .anyMatch(keyword -> collectionMapper.apply(project).stream()
                        .anyMatch(fieldValue -> containsIgnoreCase(fieldValue, keyword)));
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
