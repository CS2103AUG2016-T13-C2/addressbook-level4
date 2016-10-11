package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Reminder;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;
/** 
 * Carries information of previous command: Command word and task.
 */
public class PreviousCommand {

	public String COMMAND_WORD;
	public Task Task;

	
	public PreviousCommand(String command, Task task)
	{
		COMMAND_WORD = command;
		Task = task;
	}
	
	public PreviousCommand(String command, ReadOnlyTask task) {
		COMMAND_WORD = command;
		try {
		Task = new Task(
                new TaskName(task.getName().toString()),
                new DueDate(task.getDueDate().toString()),
                new Priority(task.getPriority().toString()),
                new Reminder(task.getReminder().toString()),
                new UniqueTagList(task.getTags())
        );
		} catch (IllegalValueException ive) {
			assert false: "Strings have to be all valid to be added in the first place";
		}
	}

	public String getCommand()
	{
		return COMMAND_WORD;
	}
	
	public Task getTask()
	{
		return Task;
	}
}
