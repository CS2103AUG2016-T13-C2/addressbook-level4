package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.address.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.address.model.activity.task.Task;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.SaveEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Represents the in-memory model of the address book data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Activity> filteredEntries;
    private final FilteredList<Tag> filteredTags;

    /**
     * Initializes a ModelManager with the given AddressBook AddressBook and its
     * variables should not be null
     */
    public ModelManager(AddressBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new AddressBook(src);
        filteredEntries = new FilteredList<>(addressBook.getAllEntries());
        filteredTags = new FilteredList<>(addressBook.getTag());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyLifeKeeper initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredEntries = new FilteredList<>(addressBook.getAllEntries());
        filteredTags = new FilteredList<>(addressBook.getTag());
    }

    @Override
    public void resetData(ReadOnlyLifeKeeper newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyLifeKeeper getLifekeeper() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyActivity target) throws TaskNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Activity person) throws UniqueActivityList.DuplicateTaskException {
        addressBook.addPerson(person);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void undoDelete(int index, Activity taskToAdd) throws UniqueActivityList.DuplicateTaskException {
        addressBook.addPerson(index, taskToAdd);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();

    }

    // @@author A0125680H
    @Override
    public synchronized Activity editTask(Activity oldTask, Activity newParams)
            throws TaskNotFoundException, DuplicateTaskException {
        Activity editedTask = addressBook.editTask(oldTask, newParams, "edit");
        indicateAddressBookChanged();

        return editedTask;
    }

    @Override
    public synchronized Activity undoEditTask(Activity oldTask, Activity newParams)
            throws TaskNotFoundException, DuplicateTaskException {
        Activity editedTask = addressBook.editTask(oldTask, newParams, "undo");
        indicateAddressBookChanged();

        return editedTask;
    }

    @Override
    public synchronized void markTask(Activity taskToMark, boolean isComplete) throws TaskNotFoundException {
        addressBook.markTask(taskToMark, isComplete);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();

    }

    @Subscribe
    public void indicateSaveLocChanged(SaveEvent event) {
        indicateAddressBookChanged();
    }

    // =========== Filtered Person List Accessors
    // ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList() {
        filteredEntries.setPredicate(p -> p.getCompletionStatus() == false && p.getisOver() == false);
    	return new UnmodifiableObservableList<>(filteredEntries);
    }

    @Override
    public UnmodifiableObservableList<Activity> getFilteredTaskListForEditing() {
        return new UnmodifiableObservableList<>(filteredEntries);
    }

    // @@author A0131813R
    @Override
    public void updateFilteredListToShowAll() {
        filteredEntries.setPredicate(p -> p.getCompletionStatus() == false && p.getisOver() == false);
    }

    @Override
    public void updateFilteredByTagListToShowAll(String tag) {
        filteredEntries.setPredicate(p -> p.getTags().contains1(tag));
    }

    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredEntries.setPredicate(p -> p.getClass().getSimpleName().equalsIgnoreCase("Task")&& p.getCompletionStatus()==false);
    }

    @Override
    public void updateFilteredDoneListToShowAll() {
        filteredEntries.setPredicate(p -> p.getCompletionStatus() == true || p.getisOver() == true);
    }

    @Override
    public void updateFilteredActivityListToShowAll() {
        filteredEntries.setPredicate(p -> p.getClass().getSimpleName().equalsIgnoreCase("Activity") &&  p.getCompletionStatus() == false);
    }

    @Override
    public void updateFilteredEventListToShowAll() {
        filteredEntries.setPredicate(p -> p.getClass().getSimpleName().equalsIgnoreCase("Event") && p.getisOver() == false);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredEntriesList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredEntriesList(Expression expression) {
        filteredEntries.setPredicate(expression::satisfies);
    }
    // @@author
    // ========== Inner classes/interfaces used for filtering
    // ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyActivity person);

        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyActivity person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyActivity person);

        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyActivity person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
