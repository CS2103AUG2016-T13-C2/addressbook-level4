package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.address.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.address.model.activity.task.Task;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyLifeKeeper newData);

    /** Returns the Lifekeeper */
    ReadOnlyLifeKeeper getLifekeeper();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyActivity target) throws UniqueActivityList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Activity person) throws UniqueActivityList.DuplicateTaskException;
    
    /** Edits the given task
     * @return The edited task
     */
    Activity editTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException;

    /** Undo edit 
     * @return The original task before edit
     */
    Activity undoEditTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<Task>} */
    UnmodifiableObservableList<Activity> getFilteredTaskListForEditing();

    /** Updates the filter of the filtered list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered list to show all tasks with the specific tag*/
    void updateFilteredByTagListToShowAll(String tag);
    
    /** Updates the filter of the filtered list to show all tasks type entries*/
    void updateFilteredTaskListToShowAll();

    /** Updates the filter of the filtered list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Marks task as completed **/
	void markTask(Activity unmarkedTask, boolean isComplete) throws TaskNotFoundException;

    /** Updates the filter of the filtered list to show all activity type entries*/
    void updateFilteredActivityListToShowAll();

    /** Updates the filter of the filtered list to show all event type entries*/
    void updateFilteredEventListToShowAll();
    
}
