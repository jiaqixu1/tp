package seedu.taskforge.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskforge.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> nameKeywords = Collections.singletonList("Alice");
        List<String> differentNameKeywords = Collections.singletonList("Bob");

        List<String> phoneKeywords = Collections.singletonList("91234567");
        List<String> differentPhoneKeywords = Collections.singletonList("88887777");

        List<String> emailKeywords = Collections.singletonList("alice@example.com");
        List<String> differentEmailKeywords = Collections.singletonList("bob@example.com");

        PersonContainsKeywordsPredicate basePredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(emailKeywords);

        // same object -> returns true
        assertTrue(basePredicate.equals(basePredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate basePredicateCopy = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(emailKeywords);
        assertTrue(basePredicate.equals(basePredicateCopy));

        // different types -> returns false
        assertFalse(basePredicate.equals(1));

        // null -> returns false
        assertFalse(basePredicate.equals((Object) null));

        // different nameKeywords -> returns false
        PersonContainsKeywordsPredicate differentNamePredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(differentNameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(emailKeywords);
        assertFalse(basePredicate.equals(differentNamePredicate));

        // different phoneKeywords -> returns false
        PersonContainsKeywordsPredicate differentPhonePredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(differentPhoneKeywords)
                .setEmailKeywords(emailKeywords);
        assertFalse(basePredicate.equals(differentPhonePredicate));

        // different emailKeywords -> returns false
        PersonContainsKeywordsPredicate differentEmailPredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(differentEmailKeywords);
        assertFalse(basePredicate.equals(differentEmailPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate().setNameKeywords(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate().setNameKeywords(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate().setNameKeywords(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setPhoneKeywords(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate()
                .setPhoneKeywords(Arrays.asList("91234567", "88887777"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setEmailKeywords(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate()
                .setEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate()
                .setEmailKeywords(Arrays.asList("aLIce@EXample.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_multipleFieldsContainsKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.singletonList("Alice"))
                .setPhoneKeywords(Collections.singletonList("91234567"))
                .setEmailKeywords(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .build()));
    }

    @Test
    public void test_noFieldChecked_returnsFalse() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate();
        assertFalse(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate().setNameKeywords(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setPhoneKeywords(Arrays.asList("12345"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("99999").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setEmailKeywords(Arrays.asList("alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("bob@email.com").build()));
    }

    @Test
    public void test_multipleFieldsDoesNotContainKeywords_returnsFalse() {
        // Match name but not phone
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.singletonList("Alice"))
                .setPhoneKeywords(Collections.singletonList("91234567"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("99999").build()));

        // Match phone but not name
        predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.singletonList("Alice"))
                .setPhoneKeywords(Collections.singletonList("91234567"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withPhone("91234567").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName() + "{nameKeywords=" + keywords
                + ", phoneKeywords=null, emailKeywords=null}";
        assertEquals(expected, predicate.toString());
    }
}
