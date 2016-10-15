package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Task> filteredPersons;
    private final FilteredList<Tag> filteredTags;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(AddressBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new AddressBook(src);
        filteredPersons = new FilteredList<>(addressBook.getPersons());
        filteredTags = new FilteredList<>(addressBook.getTags());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyLifeKeeper initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredPersons = new FilteredList<>(addressBook.getPersons());
        filteredTags = new FilteredList<>(addressBook.getTags());
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
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task person) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addPerson(person);
        updateFilteredListToShowAllTasks();
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized Task editTask(ReadOnlyTask oldTask, Task newParams) throws TaskNotFoundException, DuplicateTaskException {
        Task editedTask = addressBook.editTask(oldTask, newParams, "edit");
        updateFilteredListToShowAllTasks();
        indicateAddressBookChanged();
        
        return editedTask;
    }
    
    @Override
    public synchronized Task undoEditTask(ReadOnlyTask oldTask, Task newParams) throws TaskNotFoundException, DuplicateTaskException {
        Task editedTask = addressBook.editTask(oldTask, newParams, "undo");
        updateFilteredListToShowAllTasks();
        indicateAddressBookChanged();
        
        return editedTask;
    }

    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }

    @Override
    public void updateFilteredListToShowAllTasks() {
        filteredPersons.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredListToShowAllTags() {
        filteredTags.setPredicate(null);
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredPersons.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
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
    
    private class TagQualifier implements Qualifier {
        private Set<String> tagKeyWords;
        
        TagQualifier(Set<String> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }
        
        @Override
        public String toString() {
            return "tags=" + String.join(", ", tagKeyWords);
        }
    }

}
