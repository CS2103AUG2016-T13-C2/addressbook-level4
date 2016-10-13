package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Reminder;
import seedu.address.model.task.TaskName;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;


public class EditCommandTest extends AddressBookGuiTest {

    @Test
    /**
     * Assumptions: there is at least one task already in TypicalTestTasks.
     */
    public void edit() {
        TestTask[] currentList = td.getTypicalTasks();
        
    	//edit one person selected
        int targetIndex = 1;
        assertEditSuccess(targetIndex, currentList);
    	
        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
        
    	//invalid command
        commandBox.runCommand("edits Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
  
    }
    
    
    /**
     * Runs the edit command to edit the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of task (before edits).
     */
	
	private void assertEditSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        

        try {
            //i am still unsure how to initialise the tags.
            TestTask expectedResult = TestUtil.editTaskAttributes(taskToEdit, 
        	    	new TaskName("CS2103 T7A1 "), 
        	    	new Reminder("05-01-2016"), 
        	    	new Priority("1"), 
        	    	new DueDate("06-10-2016")
        	    	);
            
            commandBox.runCommand("edit " + targetIndexOneIndexed);
            commandBox.runCommand("n/CS2103 T7A1 d/06-10-2016 14:00 p/3 r/05-01-2016 14:00");

            //confirm if both tasks are exactly the same
            assertSameTask(currentList[targetIndexOneIndexed-1], expectedResult);
            
            //confirm the result message is correct
            
        } catch (IllegalValueException ive) {
        	//probably do a logging to make it clear that edit test has failed due to IllegalValueException
        }

    }
	
	
}
