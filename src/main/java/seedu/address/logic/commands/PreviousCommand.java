package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.task.DueDate;
import seedu.address.model.activity.task.Priority;
import seedu.address.model.tag.UniqueTagList;
/** 
 * Carries information of previous command: Command word and task.
 */
public class PreviousCommand {

	public String COMMAND_WORD;
	public Activity updatedTask;
	public Activity oldTask;

	
	public PreviousCommand(String command, Activity task)
	{
		COMMAND_WORD = command;
		updatedTask = task;
		oldTask = null;
	}
	
	public PreviousCommand(String command, ReadOnlyActivity task)
	{
		COMMAND_WORD = command;
		updatedTask = new Activity(task);
		oldTask = null;
	}
		
	public PreviousCommand(String command, Activity originalActivity, Activity editedActivity) {
        COMMAND_WORD = command;
        updatedTask = editedActivity;
                
            oldTask = originalActivity;

	}

	public String getCommand()
	{
		return COMMAND_WORD;
	}
	
	public Activity getUpdatedTask()
	{
		return updatedTask;
	}
	  
	public Activity getOldTask()
	{
	    return oldTask;
    }
	   
	
}
