package seedu.lifekeeper.logic.commands;

import seedu.lifekeeper.commons.core.EventsCenter;
import seedu.lifekeeper.commons.core.Messages;
import seedu.lifekeeper.commons.core.UnmodifiableObservableList;
import seedu.lifekeeper.commons.events.ui.JumpToListRequestEvent;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;

/**
 * Selects a activity identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the activity identified by the index number used in the last activity listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ACTIVITY_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyActivity> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_ACTIVITY_SUCCESS, targetIndex));

    }

}
