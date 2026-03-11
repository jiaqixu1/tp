package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

public class UniqueTagListTest {

    private final UniqueTagList uniqueTagList = new UniqueTagList();
    private final Tag friends = new Tag("friends");
    private final Tag colleagues = new Tag("colleagues");

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.contains(null));
    }

    @Test
    public void contains_tagNotInList_returnsFalse() {
        assertFalse(uniqueTagList.contains(friends));
    }

    @Test
    public void contains_tagInList_returnsTrue() {
        uniqueTagList.add(friends);
        assertTrue(uniqueTagList.contains(friends));
    }

    @Test
    public void add_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.add(null));
    }

    @Test
    public void add_duplicateTag_throwsDuplicateTagException() {
        uniqueTagList.add(friends);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.add(friends));
    }

    @Test
    public void setTag_nullTargetTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTag(null, friends));
    }

    @Test
    public void setTag_nullEditedTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTag(friends, null));
    }

    @Test
    public void setTag_targetTagNotInList_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.setTag(friends, colleagues));
    }

    @Test
    public void setTag_editedTagIsSameTag_success() {
        uniqueTagList.add(friends);
        uniqueTagList.setTag(friends, friends);

        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(friends);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTag_editedTagHasDifferentIdentity_success() {
        uniqueTagList.add(friends);
        uniqueTagList.setTag(friends, colleagues);

        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(colleagues);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTag_editedTagHasNonUniqueIdentity_throwsDuplicateTagException() {
        uniqueTagList.add(friends);
        uniqueTagList.add(colleagues);

        assertThrows(DuplicateTagException.class, () -> uniqueTagList.setTag(friends, colleagues));
    }

    @Test
    public void remove_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.remove(null));
    }

    @Test
    public void remove_tagDoesNotExist_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.remove(friends));
    }

    @Test
    public void remove_existingTag_removesTag() {
        uniqueTagList.add(friends);
        uniqueTagList.remove(friends);
        assertEquals(new UniqueTagList(), uniqueTagList);
    }

    @Test
    public void setTags_nullUniqueTagList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTags((UniqueTagList) null));
    }

    @Test
    public void setTags_uniqueTagList_replacesOwnListWithProvidedUniqueTagList() {
        uniqueTagList.add(friends);

        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(colleagues);

        uniqueTagList.setTags(expectedUniqueTagList);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTags((List<Tag>) null));
    }

    @Test
    public void setTags_list_replacesOwnListWithProvidedList() {
        uniqueTagList.add(friends);
        List<Tag> tagList = Collections.singletonList(colleagues);

        uniqueTagList.setTags(tagList);

        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(colleagues);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_listWithDuplicateTags_throwsDuplicateTagException() {
        List<Tag> listWithDuplicateTags = Arrays.asList(friends, friends);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.setTags(listWithDuplicateTags));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTagList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equalsAndHashCode() {
        UniqueTagList first = new UniqueTagList();
        UniqueTagList second = new UniqueTagList();

        first.add(friends);
        first.add(colleagues);
        second.add(colleagues);
        second.add(friends);

        assertTrue(first.equals(second));
        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(null));
        assertFalse(first.equals(1));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueTagList.asUnmodifiableObservableList().toString(), uniqueTagList.toString());
    }
}
