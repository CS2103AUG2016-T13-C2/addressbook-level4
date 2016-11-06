package seedu.lifekeeper.model;

import javafx.collections.transformation.FilteredList;
import seedu.lifekeeper.commons.core.ComponentManager;
import seedu.lifekeeper.commons.core.LogsCenter;
import seedu.lifekeeper.commons.core.UnmodifiableObservableList;
import seedu.lifekeeper.commons.events.model.AddressBookChangedEvent;
import seedu.lifekeeper.commons.events.model.SaveEvent;
import seedu.lifekeeper.commons.util.StringUtil;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.lifekeeper.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.lifekeeper.model.activity.task.Task;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.model.tag.UniqueTagList;

import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final LifeKeeper addressBook;
    private final FilteredList<Activity> filteredPersons;
    private final FilteredList<Tag> filteredTags;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(LifeKeeper src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new LifeKeeper(src);
        filteredPersons = new FilteredList<>(addressBook.getAllEntries());
        filteredTags = new FilteredList<>(addressBook.getTag());
        updateFilteredListToShowAll();
    }

    public ModelManager() {
        this(new LifeKeeper(), new UserPrefs());
    }

    public ModelManager(ReadOnlyLifeKeeper initialData, UserPrefs userPrefs) {
        addressBook = new LifeKeeper(initialData);
        filteredPersons = new FilteredList<>(addressBook.getAllEntries());
        filteredTags = new FilteredList<>(addressBook.getTag());
        updateFilteredListToShowAll();
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

    //@@author A0125680H
    @Override
    public synchronized Activity editTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException {
        Activity editedTask = addressBook.editTask(oldTask, newParams, "edit");
        indicateAddressBookChanged();
        
        return editedTask;
    }
    
    @Override
    public synchronized Activity undoEditTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException {
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
    
    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }
    
    @Override
    public UnmodifiableObservableList<Activity> getFilteredTaskListForEditing() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }
  //@@author A0131813R
    @Override
    public void updateFilteredListToShowAll() {
        filteredPersons.setPredicate(p->
        p.getCompletionStatus() == false && p.getisOver() == false);
    }
    
    @Override
    public void updateFilteredByTagListToShowAll(String tag) {
        filteredPersons.setPredicate(p->
        p.getTags().contains1(tag));
    }
    
    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredPersons.setPredicate(p->
        p.getClass().getSimpleName().equalsIgnoreCase("Task"));
    }
    
    @Override
    public void updateFilteredDoneListToShowAll() {
        filteredPersons.setPredicate(p->
        p.getCompletionStatus() == true || p.getisOver() == true);
    }
    
    @Override
    public void updateFilteredActivityListToShowAll() {
        filteredPersons.setPredicate(p->
        p.getClass().getSimpleName().equalsIgnoreCase("Activity"));
    }
    
    @Override
    public void updateFilteredEventListToShowAll() {
        filteredPersons.setPredicate(p->
        p.getClass().getSimpleName().equalsIgnoreCase("Event"));
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredPersons.setPredicate(expression::satisfies);
    }
  //@@author
    //========== Inner classes/interfaces used for filtering ==================================================

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
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }





}
