package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.lifekeeper.logic.commands.CommandResult;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of activities */
    ObservableList<ReadOnlyActivity> getFilteredPersonList();
    
    /** Returns the filtered list of overdue tasks */
    ObservableList<ReadOnlyActivity> getFilteredOverdueTaskList();
    
}
