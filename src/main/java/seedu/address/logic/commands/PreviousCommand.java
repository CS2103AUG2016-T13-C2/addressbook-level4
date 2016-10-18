package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyTask;
import seedu.address.model.activity.task.DueDate;
import seedu.address.model.activity.task.Priority;
import seedu.address.model.activity.task.Reminder;
import seedu.address.model.activity.task.Task;
import seedu.address.model.tag.UniqueTagList;
/** 
 * Carries information of previous command: Command word and task.
 */
public class PreviousCommand {

	public String COMMAND_WORD;
	public Task updatedTask;
	public Task oldTask;

	
	public PreviousCommand(String command, Task task)
	{
		COMMAND_WORD = command;
		updatedTask = task;
		oldTask = null;
	}
	
	public PreviousCommand(String command, ReadOnlyTask task) {
		COMMAND_WORD = command;
		oldTask = null;
		try {
		updatedTask = new Task(
                new Name(task.getName().toString()),
                new DueDate(task.getDueDate().getCalendarValue()),
                new Priority(task.getPriority().toString()),
                new Reminder(task.getReminder().getCalendarValue()),
                new UniqueTagList(task.getTags())
        );
		} catch (IllegalValueException ive) {
			assert false: "Strings have to be all valid to be added in the first place";
		}
	}
	
	public PreviousCommand(String command, ReadOnlyTask originalTask, ReadOnlyTask editedTask) {
        COMMAND_WORD = command;
        updatedTask = new Task(editedTask);
        
        try {            
            oldTask = new Task(
                new Name(originalTask.getName().toString()),
                new DueDate(originalTask.getDueDate().getCalendarValue()),
                new Priority(originalTask.getPriority().toString()),
                new Reminder(originalTask.getReminder().getCalendarValue()),
                new UniqueTagList(originalTask.getTags())
        );
        } catch (IllegalValueException ive) {
            assert false: "Strings have to be all valid to be added in the first place";
        }
	
	}

	public String getCommand()
	{
		return COMMAND_WORD;
	}
	
	public Task getUpdatedTask()
	{
		return updatedTask;
	}
	  
	public Task getOldTask()
	{
	    return oldTask;
    }
	   
	
}
