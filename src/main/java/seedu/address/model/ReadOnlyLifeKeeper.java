package seedu.address.model;


import seedu.address.model.activity.ReadOnlyTask;
import seedu.address.model.activity.UniqueTaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyLifeKeeper {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniquePersonList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getPersonList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
