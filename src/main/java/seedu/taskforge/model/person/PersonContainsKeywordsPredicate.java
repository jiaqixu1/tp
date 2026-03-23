package seedu.taskforge.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.taskforge.commons.util.StringUtil;
import seedu.taskforge.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields match any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private List<String> nameKeywords;
    private List<String> phoneKeywords;
    private List<String> emailKeywords;
    private List<String> taskKeywords;
    private List<String> projectKeywords;

    public PersonContainsKeywordsPredicate() {

    }



    public PersonContainsKeywordsPredicate setNameKeywords(List<String> nameKeywords) {
        this.nameKeywords = nameKeywords;
        return this;
    }

    public PersonContainsKeywordsPredicate setPhoneKeywords(List<String> phoneKeywords) {
        this.phoneKeywords = phoneKeywords;
        return this;
    }

    public PersonContainsKeywordsPredicate setEmailKeywords(List<String> emailKeywords) {
        this.emailKeywords = emailKeywords;
        return this;
    }

    public PersonContainsKeywordsPredicate setTaskKeywords(List<String> taskKeywords) {
        this.taskKeywords = taskKeywords;
        return this;
    }

    public PersonContainsKeywordsPredicate setProjectKeywords(List<String> projectKeywords) {
        this.projectKeywords = projectKeywords;
        return this;
    }

    @Override
    public boolean test(Person person) {
        if (!isAnyFieldChecked()) {
            return false;
        }

        return isKeywordMatch(person, nameKeywords, p -> p.getName().fullName)
                && isKeywordMatch(person, phoneKeywords, p -> p.getPhone().value)
                && isKeywordMatch(person, emailKeywords, p -> p.getEmail().value)
                && isKeywordMatchForCollection(person, taskKeywords, p -> p.getTasks().stream()
                        .map(task -> task.description).toList())
                && isKeywordMatchForCollection(person, projectKeywords, p -> p.getProjects().stream()
                        .map(project -> project.toString()).toList());
    }

    private boolean isKeywordMatch(Person person, List<String> keywords,
                                   java.util.function.Function<Person, String> fieldMapper) {
        return !isNonEmpty(keywords) || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(fieldMapper.apply(person), keyword));
    }

    private boolean isKeywordMatchForCollection(Person person, List<String> keywords,
            java.util.function.Function<Person, List<String>> collectionMapper) {
        return !isNonEmpty(keywords) || keywords.stream()
                .anyMatch(keyword -> collectionMapper.apply(person).stream()
                        .anyMatch(fieldValue -> StringUtil.containsWordIgnoreCase(fieldValue, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return Objects.equals(nameKeywords, otherPredicate.nameKeywords)
                && Objects.equals(phoneKeywords, otherPredicate.phoneKeywords)
                && Objects.equals(emailKeywords, otherPredicate.emailKeywords)
                && Objects.equals(taskKeywords, otherPredicate.taskKeywords)
                && Objects.equals(projectKeywords, otherPredicate.projectKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("phoneKeywords", phoneKeywords)
                .add("emailKeywords", emailKeywords)
                .add("taskKeywords", taskKeywords)
                .add("projectKeywords", projectKeywords)
                .toString();
    }

    public boolean isAnyFieldChecked() {
        return isNonEmpty(nameKeywords) || isNonEmpty(phoneKeywords) || isNonEmpty(emailKeywords)
                || isNonEmpty(taskKeywords) || isNonEmpty(projectKeywords);
    }

    private boolean isNonEmpty(List<String> keywords) {
        return keywords != null && !keywords.isEmpty();
    }
}
