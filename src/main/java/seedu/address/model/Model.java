package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

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
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task person) throws UniqueTaskList.DuplicateTaskException;
    
    /** Edits the given task
     * @return The edited task
     */
    Task editTask(ReadOnlyTask oldTask, Task newParams) throws TaskNotFoundException, DuplicateTaskException;

    /** Undo edit 
     * @return The original task before edit
     */
    Task undoEditTask(ReadOnlyTask oldTask, Task newParams) throws TaskNotFoundException, DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAllTasks();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show all tags */
    void updateFilteredListToShowAllTags();

    
}
